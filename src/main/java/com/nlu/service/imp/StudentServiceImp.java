package com.nlu.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nlu.model.dto.request.LoginStudentRequest;
import com.nlu.model.dto.response.ExamResultResponse;
import com.nlu.model.dto.response.ExamStudentResponse;
import com.nlu.model.dto.response.StudentResponse;
import com.nlu.repository.ExamNumberRepository;
import com.nlu.repository.ExamRepository;
import com.nlu.service.ExamNumberService;
import com.nlu.service.MailService;
import com.nlu.utils.ExcelStudentPointUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.exception.NotFoundException;
import com.nlu.model.entity.Exam;
import com.nlu.model.entity.Student;
import com.nlu.model.entity.User;
import com.nlu.model.entity.WorkTime;
import com.nlu.repository.StudentRepository;
import com.nlu.repository.UserRepository;
import com.nlu.repository.WorkTimeRepository;
import com.nlu.service.StudentService;
import com.nlu.utils.AuthenticationUtils;
import com.nlu.utils.ExcelUtils;
@Service
public class StudentServiceImp implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ExamNumberService examNumberService;
	@Autowired
	private MailService mailService;
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private WorkTimeRepository workTimeRepository;
	
	
	@Override
	public void save(MultipartFile file,String codeGroup) {
		try {
			System.out.println("name: "+file.getOriginalFilename());
			System.out.println("size: "+(double)file.getSize()/1024.0);
			
			User teacher = userRepository.findById(AuthenticationUtils.extractUserId())
					.orElseThrow(() -> new NotFoundException("teacher not found!"));
			
			List<Student> listStudents = ExcelUtils.toListStudents(file.getInputStream());
			
			createAndSaveListStudentOfTeacher( teacher,  listStudents, codeGroup);
			listStudents.forEach(mailService::sendMailNotification);
			
		} catch (IOException ex) {
			 throw new RuntimeException("Excel data is failed to store: " + ex.getMessage());
		}
		
	}

	@Override
	public List<Student> findAll() {
		 return studentRepository.findAll();
	}

	private void createAndSaveListStudentOfTeacher(User teacher, List<Student> listStudents, String codeGroup) {
		listStudents.forEach(student -> {
			student.setCodeGroup(codeGroup);
			student.setTeacher(teacher);
			student.setUsername(student.getCode());
			student.setPassword(passwordEncoder.encode(student.getCode()));
			student.setActive(true);
			studentRepository.save(student);
		});
	}

	@Override
	public ResponseEntity<ByteArrayResource> downloadExcelFileByCodeGroup(String codeGroup) {
		List<StudentResponse> listStudents = getListStudent(codeGroup);
		// Tạo file Excel và chuyển đổi thành ByteArrayResource
		ExcelStudentPointUtils excelUtils = new ExcelStudentPointUtils(listStudents, "students.xlsx");
		ByteArrayResource resource;
		try {
			resource = excelUtils.generateExcelFile();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		// Chuẩn bị phản hồi
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "students.xlsx");
		headers.setContentLength(resource.contentLength());

		// Xóa file Excel sau khi đã trả về cho người dùng
		File file = new File("students.xlsx");
		file.delete();
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	// function get ListStudent. then convert to ListStudentResponse have add point
	public List<StudentResponse> getListStudent(String codeGroup) {
		// Lấy danh sách sinh viên từ repository
		List<Student> students = studentRepository.findByCodeGroup(codeGroup);
		List<StudentResponse> listStudents = new ArrayList<>();
		// put into listStudents
		for(Student student : students) {
			Long idExamNumber =
					studentRepository.findIdExamByIdStudent(student.getId());
			ExamResultResponse examResultRes =
					examNumberService.getExamResultStudent(idExamNumber, student.getId());
			int totalCorrect = examResultRes.getTotalCorrect();
			int totalQuestion = examResultRes.getTotalQuestion();
			//student point
			double point = (totalCorrect*1.0/totalQuestion)*10;

			StudentResponse studentResponse =
					StudentResponse.builder()
							.id(student.getId())
							.code(student.getCode())
							.fullname(student.getFullname())
							.email(student.getEmail())
							.point(point)
							.build();
			listStudents.add(studentResponse);
		}
		return listStudents;
	}
	
	@Override
	public ExamStudentResponse loginByStudent(LoginStudentRequest request) {
		Student student = studentRepository.findByEmailAndCodeGroupAndActiveTrue(request.getEmail(), request.getCodeGroup())
				.orElseThrow(() -> new NotFoundException("student_or_code_not_found", request.getEmail()));
		boolean authenticated = passwordEncoder.matches(request.getPassword(), student.getPassword());

		if (!authenticated)
			throw new BadCredentialsException("login_student_authenticated");
		
		Exam exam = Optional.ofNullable(examRepository.findByCodeGroup(request.getCodeGroup()))
				.orElseThrow(() -> new NotFoundException("code_group_not_found", request.getCodeGroup()));
		WorkTime workTime = Optional.ofNullable(workTimeRepository.findByStudent_Id(student.getId()))
				.orElseThrow(() -> new NotFoundException("worktime_not_found_by_st", student.getId()));
		
		return ExamStudentResponse.builder()
				.studentId(student.getId())
				.examNumberId(workTime.getExamNumber().getId())
				.title(exam.getTitle())
				.fullname(student.getFullname())
				.code(student.getCode())
				.timeExam(exam.getTimeExam().getId())
				.dateExam(exam.getStartTime())
				.build();
	}
	@Override
	public void revokeStudentsByCodeGroup(String codeGroup) {
		studentRepository.revokeStudentsByCodeGroup(codeGroup);
	}

	
}
