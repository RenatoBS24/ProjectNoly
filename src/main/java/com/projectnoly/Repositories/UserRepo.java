package com.projectnoly.Repositories;

import com.projectnoly.Model.MySql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Procedure(name = "LoginUser")
    User login(
          @Param("p_user") String p_user,
           @Param("p_password") String p_password
    );
    @Procedure(name = "updatePassword")
    void updatePassword(
            @Param("p_user") String p_user,
            @Param("p_password") String p_password
    );
    @Procedure(name = "addUser")
    Integer addUser(
            @Param("p_user") String p_user,
            @Param("p_password") String p_password,
            @Param("p_role") String p_role
    );
    @Procedure(name ="updateDataUser")
    void updateUsername(
            @Param("p_id") Integer id_user,
            @Param("p_username") String username
    );
    @Procedure(name ="getUserByUsername")
    Optional<User> getUserByUsername(@Param("p_username") String username);
}
