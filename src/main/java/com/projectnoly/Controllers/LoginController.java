package com.projectnoly.Controllers;

import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(LoginController.class);
    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/recoverPassword")
    public String recoverPassword(){
        return "recover-password";
    }
    @GetMapping("/addUser")
    public String addUser(){
        return "new-user";
    }
    @PostMapping("/validateLogin")
    public String validateLogin(
           @RequestParam("username") String username,
           @RequestParam("password") String password,
           HttpSession session,
           RedirectAttributes redirectAttributes
    ){
        try {
            User user = userService.login(username,password);
            if(user !=null){
                session.setAttribute("user",user);
                log.info("El usuario {} ha iniciado sesión", user.getUsername());
                return "redirect:/";
            }
            redirectAttributes.addFlashAttribute("warning","Credenciales incorrectas");
            return "redirect:/login";
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/recoverPassword")
    public String updatePassword (
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("code") String code
    ){
        try {
            userService.updatePassword(username,password,code);
            log.info("El usuario {} ha actualizado su contraseña", username);
            return "login";
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/addUser")
    public String addUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("employeeName") String nameEmployee,
            @RequestParam("employeeLastName") String lastname,
            @RequestParam("dni") String dni,
            @RequestParam("phone") String phone,
            @RequestParam("code") String code
    ){
        try {
            userService.adduser(username,password,role,nameEmployee,lastname,dni,phone,code);
            log.info("Se ha registrado un nuevo usuario con el nombre de usuario {}", username);
            return "login";
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/logOut")
    public String logOut(HttpSession httpSession){
        try {
            if(httpSession.getAttribute("user") !=null){
                httpSession.removeAttribute("user");
                return "redirect:/login";
            }
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/login";
    }

}
