package com.projectnoly.Controllers;

import com.projectnoly.Services.IngredientService;
import com.projectnoly.Services.SaleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class indexController {
    private final IngredientService ingredientService;
    private final SaleService saleService;
    public indexController(IngredientService ingredientService, SaleService saleService) {
        this.ingredientService = ingredientService;
        this.saleService = saleService;
    }
    @GetMapping("/")
    public String getIndex(Model model, HttpSession httpSession){
        if (httpSession.getAttribute("user") !=null) {
            model.addAttribute("ingredients",ingredientService.getIngredientsByTenHours());
            model.addAttribute("sale",saleService.getTotalSales());
            model.addAttribute("efectivo",saleService.getTotalByMethod("Efectivo"));
            model.addAttribute("tarjeta",saleService.getTotalByMethod("Tarjeta"));
            model.addAttribute("yape",saleService.getTotalByMethod("yape"));
            model.addAttribute("plin",saleService.getTotalByMethod("plin"));
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
