package com.projectnoly.Controllers;

import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.IngredientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }
    @GetMapping("/ingredients")
    public String getIngredients(Model model, HttpSession httpSession){
        if (httpSession.getAttribute("user") !=null) {
            User user = (User) httpSession.getAttribute("user");
            model.addAttribute("ingredients",ingredientService.getAllIngredients());
            model.addAttribute("user",user);
            return "ingredient";
        }
        return "redirect:/login";
    }
    @PostMapping("/deleteIngredient")
    public String deleteIngredient(
            @RequestParam("id_ingredient") Integer id,
            @RequestParam("code_entered") String code_entered
    ){
        try {
            if(code_entered.equals("1234")){
                ingredientService.deleteIngredient(id);
            }
        } catch (Exception e) {
            return "error";
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
        try {
            ingredientService.editIngredient(id,name,price,stock,route_image);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/ingredients";
    }
    @PostMapping("/add-ingredient")
    public String addIngredient(
            @RequestParam("name_ingredient") String name,
            @RequestParam("stock") Integer stock,
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile route_image

    ){
        try {
            ingredientService.addIngredient(name,price,route_image,stock);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/ingredients";
    }
}
