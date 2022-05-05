package com.project.stocktrading.service.ForgotPassword;

import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.User.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ForgotPasswordTest {
    @Test
    public void testUpdateResetToken() {
        IUserActions userActions = Mockito.mock(UserRepository.class);
        User user = new User();
        ForgotService forgotService = new ForgotService();
        assertEquals(forgotService.updateResetPasswordToken(), user.getResetPasswordToken());
    }
}
