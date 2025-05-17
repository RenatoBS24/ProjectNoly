package com.projectnoly.Controllers;

import com.projectnoly.Model.MySql.User;
import com.projectnoly.Services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @GetMapping("/employees")
    public String getEmployees(Model model, HttpSession httpSession){
        if (httpSession.getAttribute("user") !=null) {
            User user = (User) httpSession.getAttribute("user");
            model.addAttribute("employees",employeeService.getAllEmployees());
            model.addAttribute("user",user);
            model.addAttribute("username",user.getUsername());
            return "employee";
        }
        return "redirect:/login";
    }
    @PostMapping("/edit-employee")
    public String editEmployee(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("phone") String phone,
            @RequestParam("dni") String dni
    ){
        employeeService.updateEmployee(id,name,lastname,phone,dni);
        return "redirect:/employees";
    }
    @PostMapping("/delete-employee")
    public String deleteEmployee(
            @RequestParam("id_employee") Integer id,
            @RequestParam("code_entered") String code
    ){
        if (code.equals("1234")) {
            employeeService.deleteEmployee(id);
        }
        return "redirect:/employees";
    }

}
