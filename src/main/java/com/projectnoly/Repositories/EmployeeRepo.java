package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    @Procedure(name = "getAllEmployees")
    List<Employee> getAllEmployees();
    @Procedure(name = "editEmployee")
    void updateEmployee(
           @Param("p_id") Integer id,
           @Param("p_name") String name,
           @Param("p_lastname") String lastname,
           @Param("p_phone") String phone,
           @Param("p_dni") String dni
    );
    @Procedure(name = "deleteEmployee")
    void deleteEmployee(
            @Param("p_id") Integer id
    );
    @Procedure(name = "addEmployee")
    void addEmployee(
            @Param("p_name") String name,
            @Param("p_lastname") String lastname,
            @Param("p_phone") String phone,
            @Param("p_dni") String dni,
            @Param("p_id_user") Integer id_user
    );
    @Procedure(name = "updateEmployee")
    void updateEmployee(
            @Param("p_id") Integer id_employee,
            @Param("p_dni") String dni,
            @Param("p_phone") String phone
    );
}
