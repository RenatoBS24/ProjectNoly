package com.projectnoly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class TableDTO {
    private int id_table;
    private double total;
    private boolean state;
}
