package com.assignment2.chat.application.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "Role_Tbl", schema = "Assignment2")
public class RoleEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<UserEntity> users = new HashSet<>();

}
