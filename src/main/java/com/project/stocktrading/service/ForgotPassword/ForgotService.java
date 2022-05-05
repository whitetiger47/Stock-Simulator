package com.project.stocktrading.service.ForgotPassword;

import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class ForgotService implements IForgotService {

    @Autowired
    private UserRepository userRepo;


    public void updateResetPasswordToken(String token, String email) {
        User user = userRepo.findByEmail(email);

        user.setResetPasswordToken(token);


    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    @Override
    public String updateResetPasswordToken() {
        return null;
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);

    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MailSender mailSender = null;
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("thakkarsmit28@gmail.com", "Stock Simulator");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "Thank you for requesting help"
                + "<p><a href=\"" + link + "\">Change my password</a></p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send();
    }
}