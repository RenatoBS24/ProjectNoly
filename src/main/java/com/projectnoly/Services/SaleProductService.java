package com.projectnoly.Services;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MongoDB.SaleProduct;
import com.projectnoly.Repositories.SaleProductMDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        return saleProductMDB.findById(id_sale).orElse(null);
    }
    public void addSaleProduct(int id_sale, int id_employee, double total,String pay_method, List<Product> productList){
        if (pay_method.equalsIgnoreCase("Tarjeta")) {
            total = total * 1.05;
        }
        SaleProduct saleProduct = new SaleProduct(id_sale,id_employee, LocalDateTime.now(),total,pay_method,productList);
        saleProductMDB.save(saleProduct);
    }

    public void deleteSale(int id_sale){
        saleProductMDB.deleteById(id_sale);
    }
}
