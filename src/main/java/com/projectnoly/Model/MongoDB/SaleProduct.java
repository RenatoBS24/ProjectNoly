package com.projectnoly.Model.MongoDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Document(collection = "sales")
public class SaleProduct {
    @Id
    private int id_sale;
    private int id_employee;
    private LocalDateTime date_sale;
    private double total;
    private String pay_method;
    private List<Product> products;

    @Override
    public String toString() {
        return "SaleProduct{" +
                "id_sale=" + id_sale +
                ", id_employee=" + id_employee +
                ", date_sale=" + date_sale +
                ", total=" + total +
                ", pay_method='" + pay_method + '\'' +
                ", products=" + products +
                '}';
    }
}
