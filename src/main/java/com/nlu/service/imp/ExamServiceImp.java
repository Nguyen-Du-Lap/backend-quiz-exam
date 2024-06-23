package com.nlu.service.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.nlu.exception.NotFoundException;
import com.nlu.model.dto.request.ExamRequest;
import com.nlu.model.dto.request.OptionRequest;
import com.nlu.model.dto.request.QuestionRequest;
import com.nlu.model.dto.response.ExamResponse;
import com.nlu.model.dto.response.PageResponse;
import com.nlu.model.entity.Category;
import com.nlu.model.entity.Exam;
import com.nlu.model.entity.ExamNumber;
import com.nlu.model.entity.Option;
import com.nlu.model.entity.Question;
import com.nlu.model.entity.TimeExam;
import com.nlu.model.entity.User;
import com.nlu.repository.CategoryRepository;
import com.nlu.repository.ExamNumberRepository;
import com.nlu.repository.ExamRepository;
import com.nlu.repository.OptionRepository;
import com.nlu.repository.QuestionRepository;
import com.nlu.repository.TimeExamRepository;
import com.nlu.repository.UserRepository;
import com.nlu.service.ExamService;
import com.nlu.utils.AuthenticationUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
/**
 * Implementation of the ExamService interface providing operations related to exams.
 * This service handles the creation, update, deletion, and retrieval of exams.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ExamServiceImp implements ExamService {

	TimeExamRepository timeExamRepository;
	ExamNumberRepository examNumberRepository;
	QuestionRepository questionRepository;
	OptionRepository optionRepository;
	ExamRepository examRepository;
	UserRepository userRepository;
	CategoryRepository categoryRepository;


	/**
	 * Creates an exam based on the provided request.
	 *
	 * @param request The request containing exam details.
	 * @return The ID of the created exam as a Long.
	 * @throws NotFoundException if the time, user, or category specified in the request is not found.
	 */

	@Transactional
	@Override
	public ExamResponse createExam(ExamRequest request) {
		
		if(ObjectUtils.isEmpty(request))
			throw new NotFoundException("data_null");
		
		TimeExam timeExam = timeExamRepository.findById(request.getTimeId())
				.orElseThrow(() -> new NotFoundException("time_not_found",request.getTimeId()));
		User user = userRepository.findById(AuthenticationUtils.extractUserId())
				.orElseThrow(() -> new NotFoundException("user_not_found",AuthenticationUtils.extractUserId()));
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new NotFoundException("category_not_found",request.getCategoryId()));
		/**
		 * sequence: save exam -> save question(shuffled) -> save option -> save exam number
		 */
		log.info("Exam request: {}", request);
		Exam exam = new Exam();
		exam.setTimeExam(timeExam);
		exam.setCategory(category);
		exam.setTitle(request.getTitle());
		exam.setShortDescription(request.getShortDescription());
		exam.setDescription(request.getDescription());
		exam.setQuantityQuestion(request.getQuantityQuestion());
		exam.setPublic(request.getIsPublic());
		exam.setTeacher(user);
		exam.setStartTime(Timestamp.valueOf(request.getStartTime()));
		exam.setEndTime(Timestamp.valueOf(request.getEndTime()));
		Exam examSaved = examRepository.save(exam);

		List<ExamNumber> listExamNumbers = request.getListExamNumberRequests().stream().map(itemExamNumber -> {
			ExamNumber examNumber = new ExamNumber();
			examNumber.setName(itemExamNumber.getName());
			examNumber.setExam(examSaved);
			// save question
			// shuffle the elements inside list question request
			List<QuestionRequest> shuffledQuestionRequests = new ArrayList<>(request.getListQuestionRequests());
			Collections.shuffle(shuffledQuestionRequests);
			List<Question> listQuestions = saveQuestion(shuffledQuestionRequests);

			examNumber.setListQuestions(listQuestions);
			return examNumber;
		}).toList();
		// save exam number
		examNumberRepository.saveAll(listExamNumbers);

		return ExamResponse.fromEntity(examSaved);
	}

	// save options with related question
	private List<Option> saveOption(Question question, List<OptionRequest> listOptionRequests) {
		List<Option> listOptions = listOptionRequests.stream().map(itemOption -> {
			Option option = new Option();
			option.setQuestion(question);
			option.setNameOption(itemOption.getContent());
			option.setCorrect(itemOption.getIsCorrect());
			return option;
		}).toList();
		return optionRepository.saveAll(listOptions);
	}

	// save question
	private List<Question> saveQuestion(List<QuestionRequest> listQuestionRequests) {
		List<Question> listQuestion = listQuestionRequests.stream().map(itemQuestion -> {
			Question question = new Question();
			question.setNameQuestion(itemQuestion.getQuestion());
			Question questionSaved = questionRepository.save(question);
			// save list option
			saveOption(questionSaved, itemQuestion.getListOptionRequests());
			return questionSaved;
		}).toList();
		return listQuestion;
	}

	/**
	 * Updates an existing exam based on the provided exam ID and request.
	 *
	 * @param examId The ID of the exam to update.
	 * @param request The request containing updated exam details.
	 * @throws NotFoundException if the exam with the specified ID is not found.
	 */
	@Transactional
	@Override
	public void updateExam(Long examId, ExamRequest request) {

		if (ObjectUtils.isEmpty(request))
			throw new NotFoundException("data_null");

		Exam exam = examRepository.findById(examId)
				.orElseThrow(() -> new NotFoundException("exam_not_found",examId));
		ExamRequest.setForEntity(exam, request);

		if (exam.getTimeExam().getId() != request.getTimeId()) {
			TimeExam timeExam = timeExamRepository.findById(request.getTimeId())
					.orElseThrow(() -> new NotFoundException("exam_number_not_found",request.getTimeId()));
			exam.setTimeExam(timeExam);
		}
		if (exam.getCategory().getId() != request.getCategoryId()) {
			Category category = categoryRepository.findById(request.getCategoryId())
					.orElseThrow(() -> new NotFoundException("category_not_found",request.getCategoryId()));
			exam.setCategory(category);
		}
		if(!exam.getTitle().equals(request.getTitle()))
			exam.setTitle(request.getTitle());
		if(!exam.getShortDescription().equals(request.getShortDescription()))
			exam.setShortDescription(request.getShortDescription());
		if(!exam.getDescription().equals(request.getShortDescription()))
			exam.setDescription(request.getShortDescription());
		if(exam.isPublic()!= request.getIsPublic())
			exam.setPublic(request.getIsPublic());
		examRepository.save(exam);
	}

	@Override
	public void deleteExam(Long id) {
		examRepository.deleteById(id);
	}

	
	  /**
     * Updates the visibility of an exam.
     * 
     * @param examId The ID of the exam to update.
     * @param request The boolean value indicating whether the exam is public or not.
     * @throws NotFoundException if the exam is not found.
     */

	@Override
	public void updatePublicExam(Long examId, boolean request) {
		Exam exam = examRepository.findById(examId)
				.orElseThrow(() -> new NotFoundException("exam_not_found",examId));
		exam.setPublic(request);
		examRepository.save(exam);
	}

	/**
	 * Retrieves all exams.
	 *
	 * @return A Set of ExamResponse objects representing all exams.
	 */
	@Transactional(readOnly = true)
	@Override
	public PageResponse<List<ExamResponse>> getAllExams(Pageable pageable) {
		Page<Exam> pageExam = examRepository.findAll(pageable);
		return PageResponse.<List<ExamResponse>>builder()
				.content(ExamResponse.fromEntities(pageExam.getContent()))
				.totalPage(pageExam.getTotalPages())
				.totalElement(pageExam.getTotalElements())
				.size(pageExam.getSize())
				.currentPage(pageExam.getPageable().getPageNumber())
				.build();
	}

	/**
	 * Retrieves an exam by its ID.
	 *
	 * @param id The ID of the exam to retrieve.
	 * @return An ExamResponse object representing the retrieved exam.
	 * @throws RuntimeException if the exam is not found.
	 */
	@Transactional(readOnly = true)
	@Override
	public ExamResponse getExamById(Long id) {
		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("exam_not_found",id));
		return ExamResponse.fromEntity(exam);
	}

	@Override
	public PageResponse<List<ExamResponse>> getExamsByCategoryAndKeyWord(String category, String keyword, Pageable pageable) {
		Page<Exam> pageExams;
			if(category == null && keyword == null) {
				pageExams = examRepository.findByIsPublic(true, pageable);
			}else if(keyword == null) {
				pageExams = examRepository.findByCategory_NameAndIsPublic(category, true, pageable);
			}else if(category == null) {
				pageExams = examRepository.findByTitleContainingAndIsPublic(keyword, true, pageable);
			}else {
				pageExams = examRepository.findByCategory_NameAndTitleContainingAndIsPublic(category, keyword, true, pageable);
			}

		return PageResponse.<List<ExamResponse>>builder()
				.content(ExamResponse.fromEntities(pageExams.getContent()))
				.totalPage(pageExams.getTotalPages())
				.totalElement(pageExams.getTotalElements())
				.size(pageExams.getSize())
				.currentPage(pageExams.getPageable().getPageNumber())
				.build();
	}
	
	@Override
	public PageResponse<List<ExamResponse>> getExamsByTeacherId(Long teacherId,Pageable pageable) {
		Page<Exam> pageExams = examRepository.findByTeacher_Id(teacherId, pageable);
		if(ObjectUtils.isEmpty(pageExams.getContent()))
			throw new NotFoundException("Not found data");
		return PageResponse.<List<ExamResponse>>builder()
				.content(ExamResponse.fromEntities(pageExams.getContent()))
				.totalPage(pageExams.getTotalPages())
				.totalElement(pageExams.getTotalElements())
				.size(pageExams.getSize())
				.currentPage(pageExams.getPageable().getPageNumber())
				.build();
	}
}