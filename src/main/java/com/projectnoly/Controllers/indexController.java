package com.projectnoly.Controllers;

import com.projectnoly.Services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class indexController {
    private final SaleService saleService;
    public indexController(SaleService saleService) {
        this.saleService = saleService;
    }
    @GetMapping("/")
    public String getIndex(Model model,Authentication authentication){
        if(authentication !=null && authentication.isAuthenticated()){
            model.addAttribute("sale",saleService.getTotalSales());
            model.addAttribute("efectivo",saleService.getTotalByMethod("Efectivo"));
            model.addAttribute("tarjeta",saleService.getTotalByMethod("Tarjeta"));
            model.addAttribute("yape",saleService.getTotalByMethod("yape"));
            model.addAttribute("plin",saleService.getTotalByMethod("plin"));
            model.addAttribute("username",authentication.getName());
            return "index";
        }
        return "redirect:/login";
    }
    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getData(){
        double total = saleService.getTotalSales();
        double efectivo = saleService.getTotalByMethod("Efectivo");
        double tarjeta = saleService.getTotalByMethod("Tarjeta");
        double yape = saleService.getTotalByMethod("yape");
        double plin = saleService.getTotalByMethod("plin");
        HashMap<String,Object> data = new HashMap<>();
        data.put("total",total);
        data.put("percentages", Arrays.asList(efectivo,yape,plin,tarjeta));
        return ResponseEntity.ok(data);
    }
}
