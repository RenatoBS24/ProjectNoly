package com.projectnoly.Controllers;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.TablesService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private final TablesService tablesService;
    private final Logger log = LoggerFactory.getLogger(CartController.class);
    public CartController(TablesService tablesService) {
        this.tablesService = tablesService;
    }
    @GetMapping("/cart")
    public String getCart(Model model, HttpSession httpSession) {
        try {
            if (httpSession.getAttribute("user") != null) {
                User user =(User) httpSession.getAttribute("user");
                model.addAttribute("table1",tablesService.getAllProducts(1));
                model.addAttribute("table2",tablesService.getAllProducts(2));
                model.addAttribute("table3",tablesService.getAllProducts(3));
                model.addAttribute("table4",tablesService.getAllProducts(4));
                model.addAttribute("table5",tablesService.getAllProducts(5));
                model.addAttribute("table6",tablesService.getAllProducts(6));
                model.addAttribute("total", tablesService.getTotal(1));
                model.addAttribute("employee",user.getEmployee());
                return "cart";
            }
            return "redirect:/login";
        } catch (Exception e) {
            log.warn(e.getMessage());
            return "error";
        }
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
        try {
            if(product != null){
                tablesService.deleteProduct(id_table,product.getId_product());
                return ResponseEntity.ok("removed");
            }
            return ResponseEntity.badRequest().body("id_product is required");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body("error");
        }
    }
    @PostMapping("/increment")
    public ResponseEntity<?> increment(
            @RequestBody Product product,
            @RequestParam("id_table") int id_table
    ){
        try {
            if(product != null){
                Product newproduct = tablesService.increment(id_table,product.getId_product());
                return ResponseEntity.ok(newproduct);
            }
            return ResponseEntity.badRequest().body("id_product is required");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body("error");
        }
    }
    @PostMapping("/decrement")
    public ResponseEntity<?> decrement(
            @RequestBody Product product,
            @RequestParam("id_table") int id_table
    ){
        try {
            if(product != null){
                Product newproduct = tablesService.decrement(id_table,product.getId_product());
                return ResponseEntity.ok(newproduct);
            }
            return ResponseEntity.badRequest().body("id_product is required");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body("error");
        }
    }
    @GetMapping("/getAllTables")
    public ResponseEntity<?> getAllTables(){
        try{
            return ResponseEntity.ok(tablesService.getAllTables());
        }catch (Exception e){
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body("error");
        }
    }
}
