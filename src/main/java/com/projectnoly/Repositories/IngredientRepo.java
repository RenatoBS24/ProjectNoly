package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient,Integer> {

    @Query(value = "SELECT stock FROM Ingredient where id_ingredient = ?1", nativeQuery = true)
    Ingredient getIngredientById(int id);
    @Procedure(name = "getAllIngredients")
    List<Ingredient> getAllIngredients();
    @Procedure(name = "deleteIngredient")
    void deleteIngredient (
            @Param("p_id") int id
    );

    @Procedure(name = "editIngredient")
    void editIngredient(
            @Param("p_id") int id,
            @Param("p_name") String name,
            @Param("p_price") double price,
            @Param("p_stock") int stock,
            @Param("p_route_image") String route_image
    );
    @Procedure(name = "addIngredient")
    void addIngredient(
            @Param("p_name") String name,
            @Param("p_price") double price,
            @Param("p_route_image") String route_image,
            @Param("p_stock") int stock
    );
    @Procedure(name ="updateStock")
    void updateStock(
            @Param("p_id") int id,
            @Param("p_discount") int discount
    );
}
