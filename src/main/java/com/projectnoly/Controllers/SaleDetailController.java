package com.projectnoly.Controllers;

import com.projectnoly.Services.EmployeeService;
import com.projectnoly.Services.MenuService;
import com.projectnoly.Services.SaleProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SaleDetailController {
    private final SaleProductService saleProductService;
    private final EmployeeService employeeService;;
    public SaleDetailController(SaleProductService saleProductService, EmployeeService employeeService) {
        this.saleProductService = saleProductService;
        this.employeeService = employeeService;
    }
    @GetMapping("/sale-detail")
    public String getSaleDetail(Model model, HttpSession httpSession, @RequestParam(value = "id_sale",required = false) Integer id_sale){
        if (httpSession.getAttribute("user") != null) {
            model.addAttribute("sale", saleProductService.getSaleById(id_sale));
            model.addAttribute("employee",employeeService.getEmployeeById(saleProductService.getSaleById(id_sale).getId_employee()));
            model.addAttribute("product",saleProductService.getSaleById(id_sale).getProducts());
            return "sale-detail";
        }
        return "redirect:/login";
    }

}
