package com.augustoocc.demo.globant.model.repository;

import com.augustoocc.demo.globant.model.repository.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer>{ }
