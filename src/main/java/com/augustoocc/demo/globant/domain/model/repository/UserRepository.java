package com.augustoocc.demo.globant.domain.model.repository;

import com.augustoocc.demo.globant.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
