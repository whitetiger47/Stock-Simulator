package com.project.stocktrading.service.User;

import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.models.User.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserAuthServiceTest {

    private static final String username = "luci@dal.ca";
    private static final String unknownUsername = "jar@dal.ca";
    private static final String residentialId = "LUCI123";
    private static final String password = "12345";
    private static final String userPassword = "Luci@12345";
    private static final String encryptedPassword = "Nwek@34567";
    private static final String adminUsername = "admin@dal.ca";
    private static final String adminPassword = "admin123";
    private static final BigDecimal userFunds = BigDecimal.valueOf(10000);
    private static final ArrayList<User> users = new ArrayList<>();
    private static final IUser user = getUserFromDatabase(1, "Lucifer", "MorningStar", "LUCI123", username, "Luci@12141998", new Date(12 / 3 / 2011), new BigDecimal("1000.00"));
    private static IUserServiceActions iUserServiceActions;
    private static IUserAuthServiceActions iUserAuthServiceActions;


    private static IUser getUserFromDatabase(Integer id, String firstName, String lastName, String residentialId, String email,
                                             String password, Date dateOfBirth, BigDecimal funds) {
        IUser user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setResidentialId(residentialId);
        user.setEmail(email);
        user.setPassword(password);
        user.setFunds(funds);
        user.setDateOfBirth(dateOfBirth);
        return user;
    }

    @BeforeAll
    public static void init() throws Exception {
        IUserActions iUserActions = Mockito.mock(UserRepository.class);
        Mockito.when(iUserActions.getUserFromDatabase(username)).thenReturn(user);
        Mockito.when(iUserActions.updateFunds(user)).thenReturn(1);
        Mockito.when(iUserActions.setUserLoggedIn(username)).thenReturn(1);
        Mockito.when(iUserActions.getUserPasswordFromDb(username)).thenReturn(encryptedPassword);
        Mockito.when(iUserActions.checkUserAlreadyExists(username)).thenReturn(true);
        Mockito.when(iUserActions.checkResidentialIdAlreadyExists(residentialId)).thenReturn(true);
        Mockito.when(iUserActions.getAdminPasswordFromDb(adminUsername)).thenReturn(adminPassword);
        Mockito.when(iUserActions.getUserLoggedIn()).thenReturn(user);
        Mockito.when(iUserActions.userLogout()).thenReturn(true);
        iUserAuthServiceActions = new UserAuthService(iUserActions, iUserServiceActions);
        iUserServiceActions = new UserService(iUserActions);
    }

    @Test
    public void getUserTest() {
        assertEquals(iUserServiceActions.getUserInfoFromEmail(username), user);
    }

    @Test
    public void userAlreadyExistTest() {
        assertTrue(iUserAuthServiceActions.userIsAlreadyExists(username));
    }

    @Test
    public void userNotExistTest() {
        assertFalse(iUserAuthServiceActions.userIsAlreadyExists(unknownUsername));
    }

    @Test
    public void residentialIdAlreadyExistTest() {
        assertTrue(iUserAuthServiceActions.residentialIdAlreadyExists(residentialId));
    }

    @Test
    public void passwordIsNotValidTest() {
        assertTrue(iUserAuthServiceActions.isPasswordNotValid(password));
    }

    @Test
    public void passwordEncryptionTest() {
        String cipherPassword = iUserServiceActions.encryptedPassword(userPassword);
        assertEquals(encryptedPassword, cipherPassword);
    }

    @Test
    public void passwordDecryptionTest() {
        String normalPassword = iUserServiceActions.decryptedPassword(encryptedPassword);
        assertEquals(userPassword, normalPassword);
    }

    @Test
    public void checkAdminCredentialsTest() {
        assertTrue(iUserAuthServiceActions.isAdminCredentialValid(adminUsername, adminPassword));
    }

    @Test
    public void updateUserFundsTest() {
        assertEquals(1, iUserServiceActions.updateUserFunds(username, userFunds));
    }

    @Test
    public void setUserLoggedInTest() {
        assertEquals(1, iUserServiceActions.updateUserLogedIn(username));
    }

    @Test
    public void getCurrentUserTest() {
        assertEquals(user, iUserServiceActions.getCurrentUser());
    }

    @Test
    public void userLoggedOutTest() {
        assertTrue(iUserServiceActions.userLoggedOut());
    }
}
