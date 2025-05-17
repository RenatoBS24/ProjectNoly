package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
//
@NamedStoredProcedureQuery(
        name = "LoginUser",
        procedureName = "sp_valid_user",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class)
        },
        resultClasses = User.class
)
@NamedStoredProcedureQuery(
        name = "updatePassword",
        procedureName = "sp_edit_password",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class)
        }
)
@NamedStoredProcedureQuery(
        name ="addUser",
        procedureName = "sp_add_user",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_role", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_id", type = Integer.class),
        }
)
@NamedStoredProcedureQuery(
        name = "updateDataUser",
        procedureName = "sp_update_username",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN,name = "p_username",type = String.class)
        }
)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id_user;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "state")
    private String state;
    @Column(name = "date_registered")
    private LocalDateTime date_registered;
    @Column(name = "role")
    private String role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;
}
