package com.assignment2.chat.application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User_Tbl", schema = "Assignment2")
@Setter
@Getter
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name="Users_Roles_Tbl", schema = "Assignment2",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")}
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<ScreenShotEntity> screenShots = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private LogInfoEntity logInfo;
}
