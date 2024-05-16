package com.augustoocc.demo.globant.domain.model.repository;

import com.augustoocc.demo.globant.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
