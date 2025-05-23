package com.projectnoly.Controllers;

import com.projectnoly.DTO.UserDTO;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    public String getUserProfile(Model model, Authentication authentication){
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
            log.info(password);
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
            HttpSession httpSession,
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(userDTO !=null){
            User currentUser = userService.getUserByUsername(authentication.getName());
            userService.updateDataUser(userDTO);
            log.info("Se actualizaron los datos del usuario {}", userDTO.getUsername());
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request,response,authentication);
            return ResponseEntity.ok("Se actualizaron los datos del usuario");
        }else{
            return ResponseEntity.badRequest().body("Error: " + "No se pudo actualizar los datos del usuario");
        }
    }
}
