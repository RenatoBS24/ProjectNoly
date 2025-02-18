package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.SaleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleMenuRepo extends JpaRepository<SaleMenu,Integer> {
    @Procedure(name = "addSaleMenu")
    void addSaleMenu(
          @Param("p_id_sale") int p_id_sale,
          @Param("p_id_menu")  int p_id_menu,
          @Param("p_quantity")  int p_quantity
    );
    @Procedure(name = "getSaleMenuById")
    List<SaleMenu> getSaleMenuByid(
            @Param("p_id_sale") int p_id_sale
    );
    @Procedure(name = "getSaleMenuBy1OHours")
    List<SaleMenu> getSaleMenuBy1OHours();
    @Procedure(name = "deleteSaleMenu")
    void deleteSaleMenu(
            @Param("p_id_sale") int id_sale
    );
}
