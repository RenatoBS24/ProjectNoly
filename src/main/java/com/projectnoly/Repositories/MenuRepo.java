package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepo extends JpaRepository<Menu,Integer> {
    @Procedure(name = "getAllMenu")
    List<Menu> getAllMenu();

    @Procedure(name = "addMenu")
    int addMenuBy(
            @Param("p_name") String name,
            @Param("p_description") String description,
            @Param("p_price") double price,
            @Param("p_image") String image,
            @Param("p_category_id") int category_id
    );

    @Procedure(name = "updateMenu")
     void updateMenuBy(
             @Param("p_id") int id,
             @Param("p_name") String name,
             @Param("p_description") String description,
             @Param("p_price") double price,
             @Param("p_image") String image,
             @Param("p_category_id") int category_id
    );
    @Procedure(name="deleteMenu")
    void deleteMenuBy(
            @Param("p_id") int id
    );
}
