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
        name = "getAllEmployees",
        procedureName = "sp_get_employees",
        resultClasses = Employee.class
)
// -------------------------------------------------
@NamedStoredProcedureQuery(
        name = "editEmployee",
        procedureName = "sp_edit_employee",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_lastname", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phone", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_dni", type = String.class)
        }
)
@NamedStoredProcedureQuery(
        name = "deleteEmployee",
        procedureName = "sp_delete_employee",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN,name="p_id",type = Integer.class)
        }
)
@NamedStoredProcedureQuery(
        name = "addEmployee",
        procedureName = "sp_add_employee",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_lastname", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phone", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_dni", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_user", type = Integer.class)
        }
)
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private int id_employee;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "dni")
    private String dni;
    @Column(name = "phone")
    private String phone;
    @Column(name = "date_registered")
    private LocalDateTime date_registered;
    @Column(name = "state")
    private String state;
    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;

}
