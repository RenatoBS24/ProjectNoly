package com.projectnoly.Controllers;
import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.TablesService;
import com.projectnoly.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    private final TablesService tablesService;
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(CartController.class);
    public CartController(TablesService tablesService,UserService userService) {
        this.tablesService = tablesService;
        this.userService = userService;
    }
    @GetMapping("/cart/{id_table}")
    public String getCart(Model model, Authentication authentication, @PathVariable int id_table) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByUsername(authentication.getName());
            model.addAttribute("table1", tablesService.getAllProducts(1));
            model.addAttribute("table2", tablesService.getAllProducts(2));
            model.addAttribute("table3", tablesService.getAllProducts(3));
            model.addAttribute("table4", tablesService.getAllProducts(4));
            model.addAttribute("table5", tablesService.getAllProducts(5));
            model.addAttribute("table6", tablesService.getAllProducts(6));
            model.addAttribute("total", tablesService.getTotal(id_table));
            model.addAttribute("employee", user.getEmployee());
            model.addAttribute("username", user.getUsername());
            return "cart";
        }
        return "redirect:/login";
    }
    @GetMapping("/updateTotal")
    public ResponseEntity<?> updateTotal(
            @RequestParam("id_table") int id_table
    ){
        return ResponseEntity.ok(tablesService.getTotal(id_table));
    }
    @PostMapping("/removeProduct")
    public ResponseEntity<?> removeProduct(
            @RequestBody Product product,
            @RequestParam("id_table") int id_table
    ){
        if(product != null){
            tablesService.deleteProduct(id_table,product.getId_product());
            return ResponseEntity.ok("removed");
        }
        return ResponseEntity.badRequest().body("id_product is required");
    }
    @PostMapping("/increment")
    public ResponseEntity<?> increment(
            @RequestBody Product product,
            @RequestParam("id_table") int id_table
    ){

        if(product != null){
            Product newproduct = tablesService.increment(id_table,product.getId_product());
            return ResponseEntity.ok(newproduct);
        }
        return ResponseEntity.badRequest().body("id_product is required");
    }
    @PostMapping("/decrement")
    public ResponseEntity<?> decrement(
            @RequestBody Product product,
            @RequestParam("id_table") int id_table
    ){

        if(product != null){
            Product newproduct = tablesService.decrement(id_table,product.getId_product());
            return ResponseEntity.ok(newproduct);
        }
        return ResponseEntity.badRequest().body("id_product is required");

    }
    @GetMapping("/getAllTables")
    public ResponseEntity<?> getAllTables(){
        return ResponseEntity.ok(tablesService.getAllTables());
    }
}
