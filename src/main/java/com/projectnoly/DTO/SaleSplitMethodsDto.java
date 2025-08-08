package com.projectnoly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter @AllArgsConstructor @NoArgsConstructor
public class SaleSplitMethodsDto {
    private Long idTable;
    private Long idEmployee;
    private Map<String,Double> paymentMethods;
}
