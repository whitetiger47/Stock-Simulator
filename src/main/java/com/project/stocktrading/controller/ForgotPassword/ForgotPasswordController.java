package com.project.stocktrading.controller.ForgotPassword;

import com.project.stocktrading.models.User.User;
import com.project.stocktrading.service.ForgotPassword.ForgotService;
import com.project.stocktrading.service.ForgotPassword.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ForgotService forgotService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);


        forgotService.updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
        forgotService.sendEmail(email, resetPasswordLink);
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");


        return "forgot_password_form";
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = forgotService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = forgotService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            forgotService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }
}