package com.mm.beauty.api.repository;

import com.mm.beauty.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserById(Long id);

    List<User> findAll();
    Optional<User> findUserByPhone(String phone);

}
