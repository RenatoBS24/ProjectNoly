package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.MenuIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuIngredientRepo extends JpaRepository<MenuIngredient,Integer> {
    @Procedure(name = "addMenuIngredient")
    void addMenuIngredient(
            @Param("p_id_menu") int id_menu,
            @Param("p_id_ingredient") int id_ingredient
    );
    @Procedure(name = "getMenuIngredient")
    MenuIngredient getMenuIngredient(
            @Param("p_id_menu") int id_menu,
            @Param("p_id_ingredient") int id_ingredient
    );
    @Procedure(name = "deleteMenuIngredient")
    void deleteMenuIngredient(
            @Param("p_id_menu") int id_menu
    );
}
