package com.projectnoly.Controllers;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SaleController {

    private final SaleService saleService;
    private final EmployeeService employeeService;
    private final SaleProductService saleProductService;
    private final IngredientService ingredientService;
    private final SaleMenuService saleMenuService;
    private final TablesService tablesService;

    @Autowired
    public SaleController(SaleService saleService, EmployeeService employeeService, SaleProductService saleProductService,IngredientService ingredientService,SaleMenuService saleMenuService,TablesService tablesService) {
        this.saleService = saleService;
        this.employeeService = employeeService;
        this.saleProductService = saleProductService;
        this.ingredientService = ingredientService;
        this.saleMenuService = saleMenuService;
        this.tablesService = tablesService;
    }

    @GetMapping("/sales")
    public String getAllSales(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") != null) {
            User user = (User) httpSession.getAttribute("user");
            model.addAttribute("sales", saleService.getAllSales());
            model.addAttribute("date", LocalDateTime.now());
            model.addAttribute("employees", employeeService.getAllEmployees());
            model.addAttribute("user",user);
            return "sale";
        }
        return "redirect:/login";
    }

    @PostMapping("/addSale")
    public String addSale(
            @RequestParam("pay_method") String pay_method,
            @RequestParam(value = "valid-sale", required = false,defaultValue = "false") String valid_sale,
            @RequestParam("id_employee") int id_employee,
            @RequestParam("id_table") int id_table
    ){
        try {

            double total = tablesService.getTotal(id_table);
            if (valid_sale.equals("true")) {
                total = 0;
            }
            int id_sale = saleService.addSale(pay_method,id_employee,valid_sale,id_table);
            List<Product> productList = tablesService.deleteAllProducts(id_table);
            saleMenuService.createSaleMenu(productList,id_sale);
            saleProductService.addSaleProduct(id_sale,id_employee,total,pay_method,productList);
            ingredientService.editStockByAdd(productList);
        } catch (Exception e) {
            return "error";
        }

        return "redirect:/sales";
    }
    @PostMapping("/delete-sale")
    public String deleteSale(
            @RequestParam("id_sale") int id_sale,
            @RequestParam("code_entered") String code,
            HttpSession httpSession
    ){
        try {
            User user = (User)httpSession.getAttribute("user");
            ingredientService.editStockByDelete(code,saleMenuService.getSaleMenuByid(id_sale));
            saleService.deleteSale(user,code,id_sale);
            saleMenuService.deleteSaleMenu(code,id_sale);

        } catch (Exception e) {
            return  "error";
        }
        return "redirect:/sales";
    }

}
