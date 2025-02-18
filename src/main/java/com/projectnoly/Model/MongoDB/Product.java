package com.projectnoly.Model.MongoDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public class Product {
    private int id_product;
    private String name;
    private double price;
    private double subtotal;
    private int quantity;
    private String route;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id_product == product.id_product;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_product);
    }
}
