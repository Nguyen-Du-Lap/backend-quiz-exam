package com.nlu.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nlu.model.dto.response.StudentResponse;
import com.nlu.model.entity.Exam;
import com.nlu.model.entity.Student;
import com.nlu.repository.ExamRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	@Value("${client.url.login.user}")
	private String urlLoginUser;

	@Value("${spring.mail.username}")
	private String sender;
	
	@Autowired
	private ExamRepository examRepository;

	@Async
	public CompletableFuture<Void> sendMailNotification(Student student) {
		return CompletableFuture.runAsync(() -> {
			try {
				String subject = "Exam notification from Study5";
				String senderName = "Teacher";
				Exam exam = examRepository.findByCodeGroup(student.getCodeGroup());
				Map<String, Object> model = new HashMap<>();
				model.put("user", student);
				model.put("exam", exam);
				model.put("url", urlLoginUser);
				model.put("senderName", senderName);
				sendEmailFromTemplate(student.getEmail(), "mails/notifyExam", subject, senderName, model);
			} catch (UnsupportedEncodingException | MessagingException e) {
				throw new RuntimeException("An error occurred when sending notification emails to students", e);
			}
		});
	}

	public void sendEmailFromTemplate(String to, String templateName, String subject, String personal,
			Map<String, Object> model) throws MessagingException, UnsupportedEncodingException {
		Context context = new Context(Locale.getDefault(), model);
		String content = templateEngine.process(templateName, context);
		send(personal, to, subject, content, false, true);
	}

	private void send(String personal, String to, String subject, String content, boolean isMultipart, boolean isHtml)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, isMultipart, "UTF-8");
		messageHelper.setFrom(sender, personal);
		messageHelper.setTo(to);
		messageHelper.setSubject(subject);
		messageHelper.setText(content, isHtml);
		mailSender.send(message);
	}

	@Async
	public CompletableFuture<Void> sendEmailWithAttachment(StudentResponse student, ByteArrayResource resource)
			throws MessagingException, IOException {
		return CompletableFuture.runAsync(() -> {
			try {
				if (ObjectUtils.isEmpty(resource))
					throw new RuntimeException("Failed to export file.");
				String subject = "Exam Results from Study5";
				String senderName = "Teacher";
				String fileAttachName = "students.xlsx";
				Map<String, Object> model = new HashMap<>();
				model.put("student", student);
				Context context = new Context(Locale.getDefault(), model);
				String content = templateEngine.process("mails/resultExam", context);
				send(senderName, student.getEmail(), subject, content, resource, fileAttachName);
			} catch (UnsupportedEncodingException | MessagingException e) {
				throw new RuntimeException("An error occurred when sending result emails to students", e);
			}
		});
	}

	private void send(String personal, String to, String subject, String content, ByteArrayResource resource,
			String fileAttachName) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		messageHelper.setFrom(sender, personal);
		messageHelper.setTo(to);
		messageHelper.setSubject(subject);
		messageHelper.setText(content, true);
		messageHelper.addAttachment(fileAttachName, resource);
		mailSender.send(message);
	}

}
