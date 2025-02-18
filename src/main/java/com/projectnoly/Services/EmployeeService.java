package com.projectnoly.Services;


import com.projectnoly.Model.MySql.Employee;
import com.projectnoly.Repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepo.getAllEmployees();
    }

    public Employee getEmployeeById(int id_employee) {
        return employeeRepo.findById(id_employee).orElse(null);
    }
    public void updateEmployee(Integer id, String name, String lastname, String phone, String dni) {
        if (id <= 0) {
            throw new IllegalStateException("Invalid id");
        }
        employeeRepo.updateEmployee(id, name, lastname, phone, dni);
    }

    public void deleteEmployee(Integer id){
        if(id > 0){
           employeeRepo.deleteEmployee(id);
        }
    }
    public void addEmployee(String name,String lastname,String phone,String dni,Integer id_user){
        employeeRepo.addEmployee(name,lastname,phone,dni,id_user);
    }
}
