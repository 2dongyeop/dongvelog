package com.dongvelog.domain.user.repository;

import com.dongvelog.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
