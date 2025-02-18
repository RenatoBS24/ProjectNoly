package com.projectnoly.Services;

import com.projectnoly.Model.MongoDB.Product;
import com.projectnoly.Model.MongoDB.Tables;
import com.projectnoly.Repositories.TablesMDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TablesService {

    private final TablesMDB tablesMDB;
    @Autowired
    public TablesService(TablesMDB tablesMDB) {
        this.tablesMDB = tablesMDB;
    }

    public List<Product> getAllProducts(int id) {
        if (id>0 && id<=6) {
            return tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada")).getProductList();
        }
        return  new LinkedList<>();
    }
    public double getTotal(int id){
        double total = 0;
        List<Product> products = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada")).getProductList();
        if (products != null) {
            for(Product product:products){
                total += product.getSubtotal();
            }
        }
        return total;
    }
    public void addProduct(int id, Product product) {
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        List<Product> products = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada")).getProductList();
        if (products != null) {
            if(products.contains(product)){
                int pos = products.indexOf(product);
                Product product1 = products.remove(pos);
                product1.setQuantity(product1.getQuantity() + 1);
                product1.setSubtotal(product1.getQuantity() * product1.getPrice());
                products.add(product1);
                table.setProductList(products);
            }else{
                products.add(product);
                table.setProductList(products);
            }

        }else{
            LinkedList<Product> newProducts = new LinkedList<>();
            newProducts.add(product);
            table.setProductList(newProducts);
        }
        tablesMDB.save(table);
    }
    public Product increment(int id, int id_product){
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        Product product = table.getProductList().stream().filter(Product -> Product.getId_product() == id_product).findFirst().orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setQuantity(product.getQuantity() + 1);
        product.setSubtotal(product.getQuantity() * product.getPrice());
        tablesMDB.save(table);
        return product;
    }
    public Product decrement (int id, int id_product){
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        Product product = table.getProductList().stream().filter(Product -> Product.getId_product() == id_product).findFirst().orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if(product.getQuantity() > 1){
            product.setQuantity(product.getQuantity() - 1);
            product.setSubtotal(product.getQuantity() * product.getPrice());
            tablesMDB.save(table);
        }
        return product;
    }
    public void deleteProduct(int id, int id_product){
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        List<Product> products = table.getProductList();
        products.removeIf(product -> product.getId_product() == id_product);
        table.setProductList(products);
        tablesMDB.save(table);
    }
    public List<Product> deleteAllProducts(int id) {
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        List<Product> productList = table.getProductList();
        table.setProductList(new LinkedList<>());
        tablesMDB.save(table);
        return productList;
    }

}
