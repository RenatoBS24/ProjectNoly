package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
// ---------------------------------------------

@NamedStoredProcedureQuery(
        name = "getAllIngredients",
        procedureName = "sp_get_ingredients",
        resultClasses = Ingredient.class
)
// ------------------------------------------------
@NamedStoredProcedureQuery(
        name = "deleteIngredient",
        procedureName = "sp_delete_ingredient",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class)
        }
)
// -----------------------------------------------------------
@NamedStoredProcedureQuery(
        name = "editIngredient",
        procedureName = "sp_edit_ingredient",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_price", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_stock", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_route_image", type = String.class)
        }
)
// ------------------------------------------------------------
@NamedStoredProcedureQuery(
        name="addIngredient",
        procedureName = "sp_add_ingredient",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_price", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_route_image", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_stock", type = Integer.class)
        }
)
@NamedStoredProcedureQuery(
        name = "updateStock",
        procedureName = "sp_edit_stock",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_discount", type = Integer.class)
        }
)
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingredient")
    private int id_ingredient;
    @Column(name = "name_ingredient")
    private String name_ingredient;
    @Column(name = "price")
    private double price;
    @Column(name = "state")
    private String state;
    @Column(name = "route_image")
    private String route_image;
    @Column(name="stock")
    private int stock;
    @OneToMany(mappedBy = "ingredient" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuIngredient> menuIngredientList;
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lot> lots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id_ingredient == that.id_ingredient && Objects.equals(name_ingredient, that.name_ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_ingredient, name_ingredient);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id_ingredient=" + id_ingredient +
                ", name_ingredient='" + name_ingredient + '\'' +
                ", price=" + price +
                ", state='" + state + '\'' +
                ", route_image='" + route_image + '\'' +
                ", stock=" + stock +
                ", menuIngredientList=" + menuIngredientList +
                ", lots=" + lots +
                '}';
    }
}
