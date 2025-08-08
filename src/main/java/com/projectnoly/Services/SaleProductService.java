package com.projectnoly.Services;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MongoDB.SaleProduct;
import com.projectnoly.Repositories.SaleProductMDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleProductService {

    private final SaleProductMDB saleProductMDB;
    private final Logger log = LoggerFactory.getLogger(SaleProductService.class);
    @Autowired
    public SaleProductService(SaleProductMDB saleProductMDB) {
        this.saleProductMDB = saleProductMDB;
    }
    public List<SaleProduct> getAllSales(){
        return saleProductMDB.findAll();
    }
    public SaleProduct getSaleById(int id_sale){
        SaleProduct saleProduct = saleProductMDB.findById(id_sale).orElse(null);
        if(saleProduct != null){
            for(Product product :saleProduct.getProducts()){
                if(product.getRoute() == null){
                    product.setRoute("hamburguesa.jpg");
                }
            }
            return saleProduct;
        }
        return  new SaleProduct(0,0, LocalDateTime.now(),0, null,null);
    }

    public void addSaleProduct(int id_sale, int id_employee, double total,String pay_method, List<Product> productList){
        if (pay_method.equalsIgnoreCase("Tarjeta")) {
            total = total * 1.05;
        }
        Map<String,Double> paymentMethods = new HashMap<>();
        paymentMethods.put(pay_method,total);
        SaleProduct saleProduct = new SaleProduct(id_sale,id_employee, LocalDateTime.now(),total,paymentMethods,productList);
        saleProductMDB.save(saleProduct);
    }

    public void addSaleProduct(int id_sale, int id_employee, double total,Map<String,Double> paymentMethods, List<Product> productList){
        if(paymentMethods.containsKey("tarjeta")){
            double amount = paymentMethods.remove("tarjeta");
            amount = amount*1.05;
            paymentMethods.put("tarjeta",amount);
        }
        SaleProduct saleProduct = new SaleProduct(id_sale,id_employee, LocalDateTime.now(),total,paymentMethods,productList);
        saleProductMDB.save(saleProduct);
    }

    public void deleteSale(int id_sale){
        saleProductMDB.deleteById(id_sale);
    }
}
