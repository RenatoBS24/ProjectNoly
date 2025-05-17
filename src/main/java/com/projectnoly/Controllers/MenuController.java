package com.projectnoly.Controllers;


import com.projectnoly.Model.MySql.Menu;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.CategoryService;
import com.projectnoly.Services.IngredientService;
import com.projectnoly.Services.MenuIngredientService;
import com.projectnoly.Services.MenuService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class MenuController {

    private final MenuService menuService;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;
    private final MenuIngredientService menuIngredientService;
    private final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    public MenuController(MenuService menuService,CategoryService categoryService, IngredientService ingredientService , MenuIngredientService menuIngredientService){
        this.menuService = menuService;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
        this.menuIngredientService = menuIngredientService;
    }

    @GetMapping("/menu")
    public String menu(Model model, HttpSession httpSession){
        if (httpSession.getAttribute("user") !=null) {
            List<Menu> menuList = menuService.getAllMenu();
            User user = (User)httpSession.getAttribute("user");
            model.addAttribute("menuList",menuList);
            model.addAttribute("categories",categoryService.getAllCategories());
            model.addAttribute("ingredients",ingredientService.getAllIngredients());
            model.addAttribute("user",user);
            model.addAttribute("username",user.getUsername());
            return "menu";
        }
        return "redirect:/login";
    }
    @PostMapping("/edit-menu")
    public String editMenu(
            @RequestParam("id_menu")int id,
            @RequestParam("name_item")String nameProduct,
            @RequestParam("description")String description,
            @RequestParam("price")double price,
            @RequestParam("category")int id_category,
            @RequestParam("image") MultipartFile route_image,
            @RequestParam(value = "listIngredient" ,required = false) List<String> listIngredient
    ){
        try {
            boolean result = menuService.editMenu(id,nameProduct,description,price,id_category,route_image);
            menuIngredientService.deleteMenuIngredient(id,listIngredient);
            if(result){
                log.info("Menu editado con exito");
            }
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/menu";
    }
    @PostMapping("/delete-menu")
    public String deleteMenu(
            @RequestParam("id_menu") int id,
            @RequestParam("code_entered") String code
    ){
        try {
            if (code.equals("1234")) {
                boolean result = menuService.deleteMenu(id);
                if(result){
                    log.info("El usarioMenu eliminado con exito");
                }
            }
        } catch (Exception e) {
            return  "error";
        }
        return "redirect:/menu";
    }

    @PostMapping("/add-menu")
    public String addMenu(
            @Valid @ModelAttribute Menu menu,
            @RequestParam("name_item")String nameProduct,
            @RequestParam("description")String description,
            @RequestParam("price")double price,
            @RequestParam("category")int id_category,
            @RequestParam("image") MultipartFile route_image,
            @RequestParam(value = "listIngredient" ,required = false) List<String> listIngredient
    ){
        try {
            int id = menuService.addMenu(nameProduct,description,price,route_image,id_category);
            if( id>0){
                menuIngredientService.addMenuIngredient(id,listIngredient);
                log.info("Menu agregado con exito");
            }else{
                log.warn("el id recibido es menor que 0");
            }
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/menu";

    }
}
