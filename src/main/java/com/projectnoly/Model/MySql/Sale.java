package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
// --------------------------------------------------
@NamedStoredProcedureQuery(
        name = "getAllSales",
        procedureName = "sp_get_sales",
        resultClasses = Sale.class
)
// --------------------------------------------------
@NamedStoredProcedureQuery(
        name = "getAllSalesLimit",
        procedureName = "sp_get_sale_limit",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_page", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_size", type = Integer.class
                )
        },
        resultClasses = Sale.class
)
// --------------------------------------------------
@NamedStoredProcedureQuery(
        name="addSale",
        procedureName = "sp_add_sale",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_total", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_pay_method", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_employee", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_id_sale", type = Integer.class)
        }
)
@NamedStoredProcedureQuery(
        name = "getTotalSales",
        procedureName = "sp_getTotalSales",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_total", type = Double.class)
        }
)
@NamedStoredProcedureQuery(
        name = "getSalesNow",
        procedureName = "sp_getSales",
        resultClasses = Sale.class
)
@NamedStoredProcedureQuery(
        name = "deleteSale",
        procedureName = "sp_delete_sale",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_sale", type = Integer.class)
        }
)
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_sale;
    @Column(name = "date_sale")
    private LocalDateTime date_sale;
    @Column(name = "total")
    private double total;
    @Column(name = "state")
    private String state;
    @Column(name = "pay_method")
    private String pay_method;
    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false)
    private Employee employee;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleMenu> saleMenus;
}
