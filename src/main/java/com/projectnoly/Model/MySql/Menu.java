package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
// --------------------------------------------
@Entity
@NamedStoredProcedureQuery(
        name = "getAllMenu",
        procedureName = "sp_get_menu_items",
        resultClasses = Menu.class
)
// --------------------------------------------
@NamedStoredProcedureQuery(
        name = "addMenu",
        procedureName = "sp_add_menu_item",
        resultClasses = Integer.class,
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name",type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_description",type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_price",type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_image",type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_category_id",type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_id",type = Integer.class)
        }
)
// --------------------------------------------
@NamedStoredProcedureQuery(
        name = "updateMenu",
        procedureName = "sp_update_menu_item",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name",type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_description",type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_price",type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_image",type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_category_id",type = Integer.class),
        }
)
// --------------------------------------------
@NamedStoredProcedureQuery(
        name = "deleteMenu",
        procedureName = "delete_menu_item",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class)
        }
)

@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private int id_menu;
    @Column(name = "name_item")
    @NotEmpty(message = "El nombre del item es requerido")
    @NotBlank(message = "El nombre del item es requerido")
    @Pattern(regexp = "[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ .,!?\\s[^<>|&-]]+", message = "El nombre solo debe contener letras y números")
    private String name_item;
    @Column(name = "description")
    @NotBlank(message = "La descripción es requerida")
    @NotEmpty(message = "La descripción es requerida")
    @Pattern(regexp = "[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ .,!?\\s[^<>|&-]]+", message = "La descripción solo debe contener letras y números")
    private String description;
    @Column(name = "price")
    @DecimalMin(value = "0.1", message = "El precio debe ser mayor a 0")
    private double price;
    @Column(name = "route_image")
    private String route_image;
    @Column(name = "state")
    private String state;
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notification;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuIngredient> menu_ingredients;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleMenu> sale_menu;

    @Override
    public String toString() {
        return "Menu{" +
                "id_menu=" + id_menu +
                ", name_item='" + name_item + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", route_image='" + route_image + '\'' +
                ", state='" + state + '\'' +
                ", category=" + category +
                ", notification=" + notification +
                ", menu_ingredients=" + menu_ingredients +
                '}';
    }
}
