package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
// ----------------------------------------
@NamedStoredProcedureQuery(
        name = "addSaleMenu",
        procedureName = "sp_add_sale_menu",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_sale", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_menu", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_quantity", type = Integer.class)
        }
)
// -------------------------------
@NamedStoredProcedureQuery(
        name = "getSaleMenuById",
        procedureName = "sp_get_sale_menu_by_id",
        resultClasses = SaleMenu.class,
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_sale", type = Integer.class)
        }
)
// -------------------------------
@NamedStoredProcedureQuery(
        name = "getSaleMenuBy1OHours",
        procedureName = "sp_get_sale_menu_end_10_hours",
        resultClasses = SaleMenu.class
)
// -------------------------------
@NamedStoredProcedureQuery(
        name = "deleteSaleMenu",
        procedureName = "sp_delete_sale_menu",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_sale", type = Integer.class)
        }
)
@Table(name = "sale_menu")
public class SaleMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale_menu")
    private int id_sale_menu;
    @ManyToOne
    @JoinColumn(name = "id_sale", nullable = false)
    private Sale sale;
    @ManyToOne
    @JoinColumn(name = "id_menu", nullable = false)
    private Menu menu;
    @Column(name = "quantity")
    private int quantity;
}
