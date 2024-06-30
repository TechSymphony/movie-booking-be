package com.tech_symfony.movie_booking.api.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
public class EmailService {

	private final ApplicationContext applicationContext;

	private final MailConfig mailConfig;

	@Value("${mail.username:default}")
	private String fromEmail;

	@Async
	public void sendEmail(String to, String subject, String body) {
		JavaMailSender javaMailSender = applicationContext.getBean(JavaMailSender.class);
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
		try {
			mailConfig.accessToken(mailSender);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(fromEmail);
		simpleMailMessage.setTo(to);
		simpleMailMessage.setText(body);
		simpleMailMessage.setSubject(subject);
		javaMailSender.send(simpleMailMessage);
	}

//	@Async
//	public void sendEmailHtml(String to, String subject, String text) throws MessagingException, UnsupportedEncodingException {
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//		helper.setPriority(1);
//		helper.setFrom(fromEmail, "The Cinema");
//		helper.setTo(to);
//		helper.setSubject(subject);
//		helper.setText(text, true);
//		javaMailSender.send(message);
//	}

}
