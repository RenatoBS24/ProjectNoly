package com.projectnoly;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import com.projectnoly.Services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Repositories.UserRepo;
import com.projectnoly.Services.UserService;
public class UserServiceTest {

    private UserRepo userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        userService = new UserService(userRepo, mock(EmployeeService.class));
    }

    @Test
    void testValidateOldPassword_CorrectPassword() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        Method userMethod = userService.getClass().getDeclaredMethod("sha256", String.class);
        userMethod.setAccessible(true);
        String hashPassword = userMethod.invoke(userService,"123456").toString();
        user.setPassword(hashPassword);
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        boolean result = userService.validateOldPassword(1, "123456");
        assertTrue(result);
    }

    @Test
    void testValidateOldPassword_WrongPassword() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        Method userMethod = userService.getClass().getDeclaredMethod("sha256", String.class);
        userMethod.setAccessible(true);
        String hashPassword = userMethod.invoke(userService,"123456").toString();
        user.setPassword(hashPassword);
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        boolean result = userService.validateOldPassword(1, "wrongpass");
        assertFalse(result);
    }

    @Test
    void testValidateOldPassword_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());
        boolean result = userService.validateOldPassword(1, "password123");
        assertFalse(result);
    }
}
