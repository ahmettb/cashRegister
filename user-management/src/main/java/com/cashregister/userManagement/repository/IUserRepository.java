package com.cashregister.userManagement.repository;

import com.cashregister.userManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * from users where users.username=  ?1",nativeQuery = true)
    Optional<User> findByUsername(String username);

    Optional<User>getUserByIdAndDeletedFalse(Long id);
    List<User>getAllByDeletedFalse();

    Optional<User> findByUsernameAndDeletedFalse(String username);
    Optional<User> findUserByIdAndDeletedFalse(long id);



}
