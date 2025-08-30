package com.projectnoly.Controllers;
import com.projectnoly.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/recover-password")
    public String recoverPassword(){
        return "recover-password";
    }
    @GetMapping("/add-user")
    public String addUser(){
        return "new-user";
    }



    @PostMapping("/recoverPassword")
    public String updatePassword (
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("code") String code
    ){
        log.info("El usuario {} ha solicitado un cambio de contraseña", username);
        userService.updatePassword(username,password,code);
        log.info("El usuario {} ha actualizado su contraseña", username);
        return "login";
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
        userService.adduser(username,password,role,nameEmployee,lastname,dni,phone,code);
        log.info("Se ha registrado un nuevo usuario con el nombre de usuario {}", username);
        return "login";
    }
    @PostMapping("/logOut")
    public String logOut(HttpSession httpSession){
        if(httpSession.getAttribute("user") !=null){
            httpSession.removeAttribute("user");
            return "redirect:/login";
        }
        return "redirect:/login";
    }

}
