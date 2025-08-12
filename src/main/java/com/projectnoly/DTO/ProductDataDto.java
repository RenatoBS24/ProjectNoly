package com.projectnoly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class ProductDataDto {
    private Long id;
    private String nameProduct;
    private double price;
    private String route;
}
