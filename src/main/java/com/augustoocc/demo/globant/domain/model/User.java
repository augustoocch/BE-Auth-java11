package com.augustoocc.demo.globant.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "glob-users")
public class User {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String country;
    private String city;
    private Set<Roles> roles;

}
