package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedStoredProcedureQuery(
        name = "addMenuIngredient",
        procedureName = "sp_add_menu_ingredient",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name="p_id_menu", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name="p_id_ingredient", type = Integer.class)
        }
)
@NamedStoredProcedureQuery(
        name = "getMenuIngredient",
        procedureName = "sp_get_menu_ingredient",
        resultClasses = MenuIngredient.class,
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name="p_id_menu", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name="p_id_ingredient", type = Integer.class)
        }
)
@NamedStoredProcedureQuery(
        name = "deleteMenuIngredient",
        procedureName = "sp_delete_menu_ingredient",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name="p_id_menu", type = Integer.class),
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_menu", nullable = false)
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "id_ingredient", nullable = false)
    private Ingredient ingredient;
    @Column(name = "quantity")
    private int quantity;

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id + "," +
                "\"menu\": " + menu.getId_menu() + "," +
                "\"ingredient\": " + ingredient.getId_ingredient() + "," +
                "\"quantity\": " + quantity +
                "}";
    }
}
