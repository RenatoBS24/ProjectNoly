package com.projectnoly.Controllers;


import com.projectnoly.DTO.MenuResponseDto;
import com.projectnoly.DTO.ProductDataDto;
import com.projectnoly.Model.MySql.Menu;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    public MenuController(MenuService menuService,CategoryService categoryService, IngredientService ingredientService , MenuIngredientService menuIngredientService,UserService userService){
        this.menuService = menuService;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
        this.menuIngredientService = menuIngredientService;
        this.userService = userService;
    }

    @GetMapping("/menu")
    public String menu(Model model, HttpSession httpSession, Authentication authentication){
        if(authentication != null && authentication.isAuthenticated()){
            User user = userService.getUserByUsername(authentication.getName());
            List<MenuResponseDto> menuList = menuService.getAllMenuResponse();
            model.addAttribute("menuList",menuList);
            model.addAttribute("categories",categoryService.getAllCategories());
            model.addAttribute("ingredients",ingredientService.getAllIngredients());
            model.addAttribute("user",user);
            model.addAttribute("username",user.getUsername());
            return "menu";
        }
        return "redirect:/login";
    }
    @GetMapping("/get-menu-by-id")
    public ResponseEntity<?> getMenuById(@RequestParam("id") int id){
        ProductDataDto productDataById = menuService.getProductDataById(id);
        if(productDataById != null){
            log.info("Menu encontrado con id: {}", id);
            return ResponseEntity.ok(productDataById);
        } else {
            log.warn("Menu no encontrado con id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/get-data-menu-by-id")
    public ResponseEntity<?> getDataMenuById(@RequestParam("id") int id){
        return ResponseEntity.ok(menuService.getDataMenuById(id));
    }
    @GetMapping("/get-menu-to-cart")
    public ResponseEntity<?> getMenuToCart(){
        return ResponseEntity.ok(menuService.getAllMenuToCart());
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
        boolean result = menuService.editMenu(id,nameProduct,description,price,id_category,route_image);
        menuIngredientService.deleteMenuIngredient(id,listIngredient);
        if(result){
            log.info("Menu editado con exito");
        }
        return "redirect:/menu";
    }
    @PostMapping("/delete-menu")
    public String deleteMenu(
            @RequestParam("id_menu") int id,
            @RequestParam("code_entered") String code
    ){
        if (code.equals("1234")) {
            boolean result = menuService.deleteMenu(id);
            if(result){
                log.info("El usarioMenu eliminado con exito");
            }
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
        int id = menuService.addMenu(nameProduct,description,price,route_image,id_category);
        if( id>0){
            menuIngredientService.addMenuIngredient(id,listIngredient);
            log.info("Menu agregado con exito");
        }else{
            log.warn("el id recibido es menor que 0");
        }
        return "redirect:/menu";

    }
}
