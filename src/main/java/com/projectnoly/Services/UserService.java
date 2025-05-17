package com.projectnoly.Services;

import com.projectnoly.DTO.UserDTO;
import com.projectnoly.Model.MySql.User;
import com.projectnoly.Repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepo userRepo,EmployeeService employeeService) {
        this.userRepo = userRepo;
        this.employeeService = employeeService;
    }
    @Transactional
    public User login(String username,String password){
        if(username.matches("^[a-zA-Z0-9]*$") && password.matches("^[a-zA-Z0-9]*$")){
            log.info("El usuario {} ha iniciado sesión", username);
            return userRepo.login(username,sha256(password));
        }else{
            return null;
        }
    }
    public User getUserById(int id_user){
        return userRepo.findById(id_user).orElse(null);
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
            userRepo.updatePassword(user.getUsername(),sha256(password));
            log.info("El usuario {} ha cambiado su contraseña", user.getUsername());
        }
    }
    public void updatePassword(String username,String password,String code){
        if(code.equals("1234") && username.matches("^[a-zA-Z0-9]*$") && password.matches("^[a-zA-Z0-9]*$")){
            log.info("El usuario {} ha cambiado su contraseña", username);
           userRepo.updatePassword(username,sha256(password));
        }
    }
    public void adduser(String username,String password,String role,String nameEmployee,String lastname,String dni,String phone,String code){
        if(code.equals("1234") && username.matches("^[a-zA-Z0-9]*$") && password.matches("^[a-zA-Z0-9]*$")){
            int id = userRepo.addUser(username,sha256(password),role);
            log.info("El usuario {} ha sido creado", username);
            employeeService.addEmployee(nameEmployee,lastname,phone,dni,id);
        }

    }
    public boolean validateOldPassword(Integer userId,String password){
       User user = userRepo.findById(userId).orElse(null);
       if(user !=null){
           return user.getPassword().equals(sha256(password));
       }else{
              return false;
       }
    }
    private String sha256(String password){
        HashFunction hashFunction = Hashing.sha256();
        @SuppressWarnings("UnstableApiUsage") HashCode hashCode = hashFunction.newHasher().putString(password, StandardCharsets.UTF_8).hash();
        return hashCode.toString();
    }

}
