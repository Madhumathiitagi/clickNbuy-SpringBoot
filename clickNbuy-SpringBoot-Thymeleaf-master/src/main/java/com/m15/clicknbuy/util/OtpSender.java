package com.m15.clicknbuy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.mail.internet.MimeMessage;

@Service
public class OtpSender {

	@Value("${twilio.sid}")
	String TWILIO_ACCOUNT_SID;

	@Value("${twilio.auth.token}")
	String TWILIO_AUTH_TOKEN;

	@Value("${twilio.mobile}")
	String TWILIO_MOBILE;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	TemplateEngine templateEngine;

	@Async
	public void sendOtpThruEmail(String email, int otp, String name) {

		// CHECK ENVIRONMENT
		String profile = System.getenv("SPRING_PROFILE");

		// =========================================
		// PRODUCTION / RENDER -> SEND REAL EMAIL
		// =========================================
		if ("prod".equals(profile)) {

			MimeMessage message = mailSender.createMimeMessage();

			try {

				MimeMessageHelper helper = new MimeMessageHelper(message);

				helper.setFrom("noreply@clicknbuy.com", "Clicknbuy");

				helper.setTo(email);

				helper.setSubject("Otp for Creating account with ClickNBuy");

				Context context = new Context();

				context.setVariable("name", name);
				context.setVariable("otp", otp);

				String emailMessage = templateEngine.process("email-template.html", context);

				helper.setText(emailMessage, true);

				mailSender.send(message);

			} catch (Exception e) {

				System.err.println("EMAIL FAILED");
				e.printStackTrace();

			}

		}

		// =========================================
		// LOCAL DEVELOPMENT -> PRINT OTP IN CONSOLE
		// =========================================
		else {

			System.out.println("\n=================================");
			System.out.println("OTP FOR EMAIL: " + email);
			System.out.println("OTP: " + otp);
			System.out.println("=================================\n");

		}
	}

	@Async
	public void sendOtpThruMobile(Long mobile, int otp, String name) {

		try {

			Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

			Message.creator(
					new PhoneNumber("+91" + mobile),
					new PhoneNumber(TWILIO_MOBILE),
					"Hello " + name + " Thanks for creating account your OTP is " + otp)
					.create();

		} catch (Exception e) {

			System.err.println("The OTP is : " + otp);

		}
	}
}