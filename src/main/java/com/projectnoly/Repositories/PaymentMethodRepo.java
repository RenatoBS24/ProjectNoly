package com.projectnoly.Repositories;


import com.projectnoly.Model.MySql.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepo extends JpaRepository<Long,PaymentMethod> {

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.name = ?1")
    PaymentMethod findByName(String MethodName);

}
