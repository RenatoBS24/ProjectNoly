package com.projectnoly.Controllers;

import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.EmployeeService;
import com.projectnoly.Services.SaleProductService;
import com.projectnoly.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SaleDetailController {
    private final SaleProductService saleProductService;
    private final EmployeeService employeeService;
    private final UserService userService;
    public SaleDetailController(SaleProductService saleProductService, EmployeeService employeeService,UserService userService) {
        this.saleProductService = saleProductService;
        this.employeeService = employeeService;
        this.userService = userService;
    }
    @GetMapping("/sale-detail")
    public String getSaleDetail(Model model, HttpSession httpSession, Authentication authentication, @RequestParam(value = "id_sale",required = false) Integer id_sale){
        if(authentication != null && authentication.isAuthenticated()){
            User user = userService.getUserByUsername(authentication.getName());
            model.addAttribute("sale", saleProductService.getSaleById(id_sale));
            model.addAttribute("employee",employeeService.getEmployeeById(saleProductService.getSaleById(id_sale).getId_employee()));
            model.addAttribute("product",saleProductService.getSaleById(id_sale).getProducts());
            model.addAttribute("username",user.getUsername());
            return "sale-detail";
        }
        return "redirect:/login";
    }

}
