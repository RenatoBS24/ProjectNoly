package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<Sale,Integer> {

    @Procedure(name = "getAllSales")
    List<Sale> getAllSales();
    @Procedure(name = "addSale")
    int addSale(
            @Param("p_total") double total,
            @Param("p_pay_method") String pay_method,
            @Param("p_id_employee") int id_employee
    );
    @Procedure(name = "getTotalSales")
    double getTotalSales();
    @Procedure(name = "getSalesNow")
    List<Sale> getSalesNow();
    @Procedure(name = "deleteSale")
    void deleteSale(
            @Param("p_id_sale") int id_sale
     );
}
