package com.project.stocktrading.service.ForgotPassword;

import com.project.stocktrading.models.User.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IForgotService {

    void updateResetPasswordToken(String token, String email);

    void updatePassword(User user, String newPassword);

    void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;

    User getByResetPasswordToken(String token);

    String updateResetPasswordToken();
}
