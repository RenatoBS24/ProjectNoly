package com.projectnoly.Controllers;

import com.projectnoly.DTO.UserDTO;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/user-profile")
    public String getUserProfile(Model model, Authentication authentication, HttpSession httpSession){
        if(authentication != null && authentication.isAuthenticated()){
            User user = userService.getUserByUsername(authentication.getName());
            model.addAttribute("employee",user.getEmployee());
            model.addAttribute("user",user);
            model.addAttribute("username",user.getUsername());
            return "profile-user";
        }
        return "redirect:/login";
    }
    @PostMapping("/validateOldPassword")
    public ResponseEntity<?> validateRecoverPassword(
            @RequestParam("userID") Integer username,
            @RequestParam("password") String password
    ){
        try{
            return ResponseEntity.ok(userService.validateOldPassword(username,password));
        }catch (Exception e){
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(
            @RequestParam("userID") Integer userId,
            @RequestParam("password") String password
    ){
        try{
            userService.updatePassword(userId,password);
            return ResponseEntity.ok("Contraseña actualizada");
        }catch (Exception e){
            log.info("Error: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update-data")
    public ResponseEntity<?> updateData(
            @Valid @RequestBody UserDTO userDTO,
            HttpSession httpSession
    ){
        if(userDTO !=null){
            userService.updateDataUser(userDTO);
            log.info("Se actualizaron los datos del usuario {}", userDTO.getUsername());
            httpSession.removeAttribute("user");
            User user = userService.getUserById(userDTO.getId_user());
            httpSession.setAttribute("user",user);
            return ResponseEntity.ok("Se actualizaron los datos del usuario");
        }else{
            return ResponseEntity.badRequest().body("Error: " + "No se pudo actualizar los datos del usuario");
        }
    }
}
