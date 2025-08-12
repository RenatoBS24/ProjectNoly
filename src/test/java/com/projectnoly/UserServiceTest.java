package com.projectnoly;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import com.projectnoly.DTO.UserDTO;
import com.projectnoly.Services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Repositories.UserRepo;
import com.projectnoly.Services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    private UserRepo userRepo;
    private UserService userService;
    private EmployeeService employeeService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        employeeService = mock(EmployeeService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepo, employeeService, passwordEncoder);

    }

    @Test
    void testValidateOldPassword_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());
        boolean result = userService.validateOldPassword(2, "password123");
        assertFalse(result);
    }
    @Test
    void testAddUser_ValidInput() {
        String username = "usuario123";
        String password = "pass123";
        String role = "ADMIN";
        String nameEmployee = "Juan";
        String lastname = "Pérez";
        String dni = "12345678";
        String phone = "987654321";
        String code = "1234";

        when(passwordEncoder.encode(password)).thenReturn("encodedPass123");
        when(userRepo.addUser(eq(username), anyString(), eq(role))).thenReturn(1);

        userService.adduser(username, password, role, nameEmployee, lastname, dni, phone, code);

        verify(userRepo).addUser(eq(username), eq("encodedPass123"), eq(role));
        verify(employeeService).addEmployee(nameEmployee, lastname, phone, dni, 1);
    }

    @Test
    void testAddUser_InvalidCode() {
        userService.adduser("usuario123", "pass123", "ADMIN", "Juan", "Pérez", "12345678", "987654321", "0000");
        verify(userRepo, never()).addUser(anyString(), anyString(), anyString());
        verify(employeeService, never()).addEmployee(anyString(), anyString(), anyString(), anyString(), anyInt());
    }
    @Test
    void testUpdateDataUser_ValidInput() {
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getId_user()).thenReturn(1);
        when(userDTO.getUsername()).thenReturn("usuario123");
        when(userDTO.getDni()).thenReturn("12345678");
        when(userDTO.getPhone()).thenReturn("987654321");

        userService.updateDataUser(userDTO);

        verify(userRepo).updateUsername(1, "usuario123");
        verify(employeeService).updateEmployee(1, "12345678", "987654321");
    }

    @Test
    void testUpdateDataUser_InvalidInput() {
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getId_user()).thenReturn(0);
        when(userDTO.getUsername()).thenReturn("usuario12345678901234567890");

        userService.updateDataUser(userDTO);

        verify(userRepo, never()).updateUsername(anyInt(), anyString());
        verify(employeeService, never()).updateEmployee(anyInt(), anyString(), anyString());
    }
    @Test
    void testUpdatePassword_ById_ValidUser() {
        User user = new User();
        user.setUsername("usuario123");
        user.setPassword("oldPass");
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass123");

        userService.updatePassword(1, "pass123");

        verify(userRepo).updatePassword(eq("usuario123"), eq("encodedPass123"));
    }

    @Test
    void testUpdatePassword_ById_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        userService.updatePassword(1, "pass123");

        verify(userRepo, never()).updatePassword(anyString(), anyString());
    }
    @Test
    void testUpdatePassword_ByUsername_ValidInput() {
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass123");
        userService.updatePassword("usuario123", "pass123", "1234");
        verify(userRepo).updatePassword(eq("usuario123"), eq("encodedPass123"));
    }

    @Test
    void testUpdatePassword_ByUsername_InvalidCode() {
        userService.updatePassword("usuario123", "pass123", "0000");
        verify(userRepo, never()).updatePassword(anyString(), anyString());
    }

    @Test
    void testUpdatePassword_ByUsername_InvalidUsernameOrPassword() {
        userService.updatePassword("usuario@123", "pass123", "1234");
        userService.updatePassword("usuario123", "pass@123", "1234");
        verify(userRepo, never()).updatePassword(anyString(), anyString());
    }
}
