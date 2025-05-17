package com.projectnoly.Controllers;

import com.projectnoly.Model.MySql.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {
    @GetMapping("/tables")
    public String tables(Model model, HttpSession HttpSession){
        if(HttpSession.getAttribute("user") != null){
            if("superadmin".equals(((User) HttpSession.getAttribute("user")).getRole())){
                User user = (User) HttpSession.getAttribute("user");
                model.addAttribute("user", user);
                model.addAttribute("username", user.getUsername());
                return "tables";
            }else{
                return "construction";
            }
        }else{
            return "redirect:/login";
        }
    }
}
