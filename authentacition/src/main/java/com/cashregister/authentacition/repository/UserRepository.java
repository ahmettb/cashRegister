package com.cashregister.authentacition.repository;

import com.cashregister.authentacition.model.User;
import com.cashregister.authentacition.model.request.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User >findByUsername(String username);
   boolean existsByMail(String mail);

   Optional<User> findByUsernameAndPassword(String username, String password);
}
