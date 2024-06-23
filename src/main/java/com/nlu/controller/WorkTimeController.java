package com.nlu.controller;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.nlu.model.dto.response.WorkTimeResponse;
import com.nlu.service.UserAnswerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.response.ResponseData;
import com.nlu.model.entity.WorkTime;
import com.nlu.service.imp.WorkTimeService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/worktimes")
public class WorkTimeController {

	@Autowired
	private WorkTimeService workTimeService;
	@Autowired
	private UserAnswerService userAnswerService;

	@PostMapping("/{examId}")
	public ResponseData createWorkTime(@PathVariable(name = "examId") Long examId) {
		boolean data = workTimeService.createWorkTime(examId);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Create worktime from examId")
				.data(data)
				.build();
	}

	@PostMapping("/users/{userId}/exam-number/{examNumberId}")
	public ResponseData addWorkTimeById(
			@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "examNumberId") Integer examNumberId,
			@RequestParam(name = "timeExam") Integer timeExam) {

		boolean data = workTimeService.addWorkTimeByUser(userId, examNumberId, timeExam);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Add successfully")
				.data(data)
				.build();
	}

	@GetMapping("/users/{userId}/exam-number/{examNumberId}")
	public ResponseData getWorkTimeById(
			@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "examNumberId") Integer examNumberId) {

		WorkTime data = workTimeService.getWorkTimeByUser(userId, examNumberId);
		if(data == null) return ResponseData.builder()
					.status(HttpStatus.NOT_FOUND.value())
					.message("Data not found")
					.build();

		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Data work time by id user and id exam number")
				.data(data)
				.build();
	}

	@PutMapping("/users/{userId}/exam-number/{examNumberId}")
    public ResponseData updateWorkTimeById(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "examNumberId") Integer examNumberId,
            @RequestParam(name = "endExam") String endExam) {
        Timestamp endExamTimestamp;

        try {
            Instant instant = Instant.parse(endExam);
            endExamTimestamp = Timestamp.from(instant);
        } catch (Exception e) {
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Invalid date format")
                    .build();
        }

        boolean data = workTimeService.updateEndExamWorkTimeByUser(userId, examNumberId, endExamTimestamp);
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .message("Update successfully")
                .data(data)
                .build();
    }
	@Transactional
	@Modifying
	@DeleteMapping("/users/{userId}/exam-number/{examNumberId}")
	public ResponseData removeWorkTimeAndUserAnswer(
			@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "examNumberId") Integer examNumberId) {
		// remove work time
		boolean removeWorkTime = workTimeService.removeWorkTimeByUser(userId, examNumberId);
		// remove user answer
		boolean removeUserAnswer = userAnswerService.removeAnswerUser(examNumberId, userId);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Delete successfully!")
				.data(removeWorkTime && removeUserAnswer)
				.build();
	}
	@GetMapping("/users/{id}")
	public ResponseData getAllWorkTimeByUser(@PathVariable Long id) {
		List<WorkTimeResponse> workTimes = workTimeService.getAllWorkTimeByUser(id);
		if(workTimes.isEmpty())
				return ResponseData.builder()
						.status(HttpStatus.OK.value())
						.message("Empty data!")
						.data(workTimes)
						.build();
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("All work times by user")
				.data(workTimes)
				.build();
	}

	@PutMapping("/students/{studentId}/exam-number/{examNumberId}")
	public ResponseData updateWorkTimeStudent(
			@PathVariable(name = "studentId") Long studentId,
			@PathVariable(name = "examNumberId") Integer examNumberId,
			@RequestParam(name = "endExam") String endExam) {
		Timestamp endExamTimestamp;

		try {
			Instant instant = Instant.parse(endExam);
			endExamTimestamp = Timestamp.from(instant);
		} catch (Exception e) {
			return ResponseData.builder()
					.status(HttpStatus.BAD_REQUEST.value())
					.message("Invalid date format")
					.data(null)
					.build();
		}

		boolean data = workTimeService.updateEndExamWorkTimeStudent(studentId, examNumberId, endExamTimestamp);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Update successfully")
				.data(data)
				.build();
	}
	@GetMapping("/students/{studentId}/exam-number/{examNumberId}")
	public ResponseData getWorkTimeByIdStudent(
			@PathVariable(name = "studentId") Long studentId,
			@PathVariable(name = "examNumberId") Integer examNumberId) {

		WorkTime data = workTimeService.getWorkTimeByStudent(studentId, examNumberId);
		if(data == null) return ResponseData.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message("Data not found")
				.build();

		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Data work time by id user and id exam number")
				.data(data)
				.build();
	}
}
