package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.Sale;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<Sale,Integer> {

    @Procedure(name = "getAllSales")
    List<Sale> getAllSales();
    @Procedure(name = "getAllSalesLimit")
    List<Sale> getAllSalesPage(
            @Param("p_page") int page,
            @Param("p_size") int size
    );

    @Query("SELECT s.id_sale FROM Sale s WHERE s.state = 'active' ORDER BY s.date_sale DESC")
    List<Long> salesIdList(Pageable pageable);


    @Query("""
    SELECT DISTINCT s FROM Sale s
    JOIN FETCH s.employee e
    LEFT JOIN FETCH s.salePaymentMethodList spm
    LEFT JOIN FETCH spm.paymentMethod pm
    WHERE s.id_sale IN :salesIdList
    ORDER BY s.date_sale DESC
    """
    )
    @NotNull
    List<Sale> findAll(List<Long> salesIdList);
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
