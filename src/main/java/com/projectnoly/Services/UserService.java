package com.projectnoly.Services;

import com.projectnoly.DTO.UserDTO;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService  implements UserDetailsService {
    private final UserRepo userRepo;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepo userRepo,EmployeeService employeeService,PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }
    public User getUserById(int id_user){
        return userRepo.findById(id_user).orElse(null);
    }
    @Transactional(readOnly = true)
    public User getUserByUsername(String username){
        return userRepo.getUserByUsername(username).orElse(null);
    }
    public void updateDataUser(UserDTO user){
        if(user.getId_user() >0 && user.getUsername().length()<=20){
            userRepo.updateUsername(user.getId_user(), user.getUsername());
            employeeService.updateEmployee(user.getId_user(),user.getDni(), user.getPhone());
        }
    }
    public void updatePassword(Integer userID,String password){
        User user = userRepo.findById(userID).orElse(null);
        if(user !=null){
            userRepo.updatePassword(user.getUsername(),passwordEncoder.encode(password));
            log.info("El usuario {} ha cambiado su contraseña", user.getUsername());
        }
    }
    public void updatePassword(String username,String password,String code){
        if(code.equals("1234") && username.matches("^[a-zA-Z0-9]*$") && password.matches("^[a-zA-Z0-9]*$")){
            log.info("El usuario {} ha cambiado su contraseña", username);
           userRepo.updatePassword(username,passwordEncoder.encode(password));
        }
    }
    public void adduser(String username,String password,String role,String nameEmployee,String lastname,String dni,String phone,String code){
        if(code.equals("1234") && username.matches("^[a-zA-Z0-9]*$") && password.matches("^[a-zA-Z0-9]*$")){
            int id = userRepo.addUser(username,passwordEncoder.encode(password),role);
            log.info("El usuario {} ha sido creado", username);
            employeeService.addEmployee(nameEmployee,lastname,phone,dni,id);
        }

    }
    public boolean validateOldPassword(Integer userId,String password){
       User user = userRepo.findById(userId).orElse(null);
       if(user !=null){
           return passwordEncoder.matches(password,user.getPassword());
       }else{
              return false;
       }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_"+user.getRole().toUpperCase())
                .build();
    }

}
