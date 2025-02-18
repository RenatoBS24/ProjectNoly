package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Lot {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id_lot;
    @Column(name = "date_lot")
    private java.sql.Date date_lot;
    @Column(name = "date_expiration")
    private java.sql.Date date_expiration;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "id_ingredient", nullable = false)
    private Ingredient ingredient;
    @Column(name = "state")
    private String state;
}
