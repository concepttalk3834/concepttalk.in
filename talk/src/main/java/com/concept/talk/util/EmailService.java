package com.concept.talk.util;

import com.concept.talk.entity.Payment;
import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;
	public void sendResetTokenLink(String email, String resetToken) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		String resetPasswordLink = "http://localhost:5173/reset-password?token=" + resetToken;
		
		helper.setFrom("lalitjangir.cse29@gmail.com");
		helper.setTo(email);
		helper.setSubject("Reset your password");
		
		// Email content with reset link
		helper.setText("<p>Hello,</p>"
				+ "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to reset your password:</p>"
				+ "<p><a href=\"" + resetPasswordLink + "\">Reset Password</a></p>"
				+ "<p>This link will expire in 1 hour.</p>"
				+ "<p>If you didn't request this, please ignore this email.</p>", true);
		
		// Send the email
		mailSender.send(message);
	}
	
	public void sendVerificationEmail(String to,String subject,String body){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		message.setFrom("lalitjangir.cse29@gmail.com");
		mailSender.send(message);
	}
	
	public void sendPaymentInfo(String to, Payment payment,String subject,String body) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body);
		mailSender.send(message);
	}
}
