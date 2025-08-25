package com.projectnoly.Controllers;

import com.projectnoly.DTO.AddToCartDto;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.CategoryService;
import com.projectnoly.Services.MenuService;
import com.projectnoly.Services.TablesService;
import com.projectnoly.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class NewSaleController {

    private final CategoryService categoryService;
    private final MenuService menuService;
    private final TablesService tablesService;
    private final UserService userService;
    @Autowired
    public NewSaleController(CategoryService categoryService, MenuService menuService, TablesService tablesService,UserService userService) {
        this.categoryService = categoryService;
        this.menuService = menuService;
        this.tablesService = tablesService;
        this.userService = userService;
    }
    @GetMapping("/newSale")
    public String getCartPage(Model model, Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated()){
            User user = userService.getUserByUsername(authentication.getName());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("menuList", menuService.getAllMenuResponse());
            model.addAttribute("username",user.getUsername());
            return "newSale";
        }
        return "redirect:/login";
    }
    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(
            @RequestParam("id_table") int id_table,
            @RequestParam("id_product") int id_product

    ) {
        tablesService.addProduct(id_table,id_product);
        return ResponseEntity.ok("product added to cart");
    }
    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto){
        tablesService.addProduct(addToCartDto.idTable().intValue(),addToCartDto.idProduct().intValue());
        return ResponseEntity.ok(Map.of("success", "Product added to cart successful"));
    }

}
