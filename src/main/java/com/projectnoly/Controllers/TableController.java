package com.projectnoly.Controllers;

import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {
    private final UserService userService;
    public TableController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/tables")
    public String tables(Model model,Authentication authentication){
        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            model.addAttribute("user",user);
            model.addAttribute("username",username);
            return "tables";
        }
        return "redirect:/login";
    }
}
