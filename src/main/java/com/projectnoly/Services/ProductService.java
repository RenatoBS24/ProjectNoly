package com.projectnoly.Services;


import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MySql.Menu;
import com.projectnoly.Repositories.ProductRepoMDB;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepoMDB productRepoMDB;
    private final MenuService menuService;

    public ProductService(ProductRepoMDB productRepoMDB, MenuService menuService) {
        this.productRepoMDB = productRepoMDB;
        this.menuService = menuService;
    }

    public List<Product> getAllProducts(){
        return productRepoMDB.findAll();
    }
    public double getTotal(){
        double total = 0;
        List<Product> products = productRepoMDB.findAll();
        for(Product product:products){
            total += product.getSubtotal();
        }
        return total;
    }
    public void addProduct(Product product){
        productRepoMDB.save(product);
    }

    public Product increment(int id_product){
        Product product = productRepoMDB.findById(id_product).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setQuantity(product.getQuantity() + 1);
        product.setSubtotal(product.getQuantity() * product.getPrice());
        productRepoMDB.save(product);
        return product;
    }
    public Product decrement(int id_product){
        Product product = productRepoMDB.findById(id_product).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if(product.getQuantity() > 1){
            product.setQuantity(product.getQuantity() - 1);
            product.setSubtotal(product.getQuantity() * product.getPrice());
            productRepoMDB.save(product);
        }
        return product;
    }
    public void deleteProduct(int id_product){
        productRepoMDB.deleteById(id_product);
    }
    public List<Product> deleteAllProduct(){
        List<Product> productList = productRepoMDB.findAll();
        productRepoMDB.deleteAll();
        return productList;
    }

    public HashMap<Menu,Integer> getMenuByIngredient(){
        List<Product> productList = productRepoMDB.findAll();
        HashMap<Menu,Integer> menuHashMap = new HashMap<>();
        for(Product product:productList){
            menuHashMap.put(menuService.getMenuById(product.getId_product()),product.getQuantity());
        }
        return menuHashMap;
    }
}
