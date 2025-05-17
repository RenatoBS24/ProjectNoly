package com.projectnoly.Controllers;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Services.CategoryService;
import com.projectnoly.Services.MenuService;
import com.projectnoly.Services.TablesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class NewSaleController {

    private final CategoryService categoryService;
    private final MenuService menuService;
    private final TablesService tablesService;
    @Autowired
    public NewSaleController(CategoryService categoryService, MenuService menuService, TablesService tablesService) {
        this.categoryService = categoryService;
        this.menuService = menuService;
        this.tablesService = tablesService;
    }
    @GetMapping("/newSale")
    public String getCartPage(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") != null) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("menuList", menuService.getAllMenu());
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

}
