package com.projectnoly.Controllers;

import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.IngredientService;
import com.projectnoly.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final UserService userService;

    @Autowired
    public IngredientController(IngredientService ingredientService,UserService userService){
        this.ingredientService = ingredientService;
        this.userService = userService;
    }
    @GetMapping("/ingredients")
    public String getIngredients(Model model, Authentication authentication){
        if(authentication != null && authentication.isAuthenticated()){
            User user = userService.getUserByUsername(authentication.getName());
            model.addAttribute("ingredients",ingredientService.getAllIngredients());
            model.addAttribute("user",user);
            model.addAttribute("employee",user.getEmployee());
            model.addAttribute("username",user.getUsername());
            return "ingredient";
        }
        return "redirect:/login";
    }
    @PostMapping("/deleteIngredient")
    public String deleteIngredient(
            @RequestParam("id_ingredient") Integer id,
            @RequestParam("code_entered") String code_entered
    ){
        if(code_entered.equals("1234")){
            ingredientService.deleteIngredient(id);
        }
        return "redirect:/ingredients";
    }
    @PostMapping("/editIngredient")
    public String editIngredient(
            @RequestParam("id_ingredient") Integer id,
            @RequestParam("name_ingredient") String name,
            @RequestParam("price") Double price,
            @RequestParam("stock") Integer stock,
            @RequestParam(value = "image", required = false) MultipartFile route_image
    ){
        ingredientService.editIngredient(id,name,price,stock,route_image);
        return "redirect:/ingredients";
    }
    @PostMapping("/add-ingredient")
    public String addIngredient(
            @RequestParam("name_ingredient") String name,
            @RequestParam("stock") Integer stock,
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile route_image

    ){
        ingredientService.addIngredient(name,price,route_image,stock);
        return "redirect:/ingredients";
    }
}
