package com.projectnoly.Services;

import com.projectnoly.DTO.TableDTO;
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
    /**
     * Retrieves all products associated with a specific table.
     *
     * @param id The identifier of the table from which to retrieve products.
     * @return A list of products associated with the table, or an empty list if the table ID is out of range.
     * @throws RuntimeException If the table with the specified ID exists but is not found.
     */
    public List<Product> getAllProducts(int id) {
        if (id>0 && id<=6) {
            return tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada")).getProductList();
        }
        return  new LinkedList<>();
    }
    public List<TableDTO> getAllTables(){
       List<Tables> tablesList = tablesMDB.findAll();
       List<TableDTO> tableDTOS = new LinkedList<>();
       for(Tables tables: tablesList){
           if(tables.getProductList().isEmpty()){
               tableDTOS.add(new TableDTO(tables.getId(),0,false));

           }else{
               double total = 0;
               for(Product product : tables.getProductList()){
                   total+=product.getSubtotal();
               }
               tableDTOS.add(new TableDTO(tables.getId(),total,true));
           }
       }
       return tableDTOS;
    }
    /**
     * Retrieves the total price of all products associated with a specific table.
     *
     * @param id The identifier of the table for which to calculate the total price.
     * @return The total price of all products associated with the table.
     * @throws RuntimeException If the table with the specified ID exists but is not found.
     */
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
    /**
     * Adds a product to a specific table. If the product already exists in the table, its quantity is incremented.
     *
     * @param id The identifier of the table to which the product will be added.
     * @param product The product to be added to the table.
     * @throws RuntimeException If the table with the specified ID exists but is not found.
     */
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
    /**
     * Increments the quantity of a specific product in a table by 1.
     *
     * @param id The identifier of the table containing the product.
     * @param id_product The identifier of the product to be incremented.
     * @return The updated product with the new quantity and subtotal.
     * @throws RuntimeException If the table or product with the specified ID is not found.
     */
    public Product increment(int id, int id_product){
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        Product product = table.getProductList().stream().filter(Product -> Product.getId_product() == id_product).findFirst().orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setQuantity(product.getQuantity() + 1);
        product.setSubtotal(product.getQuantity() * product.getPrice());
        tablesMDB.save(table);
        return product;
    }
    /**
     * Decrements the quantity of a specific product in a table by 1, if the quantity is greater than 1.
     *
     * @param id The identifier of the table containing the product.
     * @param id_product The identifier of the product to be decremented.
     * @return The updated product with the new quantity and subtotal.
     * @throws RuntimeException If the table or product with the specified ID is not found.
     */
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
    /**
     * Removes a specific product from a table.
     *
     * @param id The identifier of the table from which the product will be removed.
     * @param id_product The identifier of the product to be removed.
     * @throws RuntimeException If the table with the specified ID is not found.
     */
    public void deleteProduct(int id, int id_product){
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        List<Product> products = table.getProductList();
        products.removeIf(product -> product.getId_product() == id_product);
        table.setProductList(products);
        tablesMDB.save(table);
    }
    /**
     * Removes all products from a specific table and returns the list of removed products.
     *
     * @param id The identifier of the table from which all products will be removed.
     * @return A list of products that were removed from the table.
     * @throws RuntimeException If no table is found with the provided identifier.
     */
    public List<Product> deleteAllProducts(int id) {
        Tables table = tablesMDB.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        List<Product> productList = table.getProductList();
        table.setProductList(new LinkedList<>());
        tablesMDB.save(table);
        return productList;
    }

}
