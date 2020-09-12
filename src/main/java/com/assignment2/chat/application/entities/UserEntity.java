package com.assignment2.chat.application.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User_Tbl", schema = "Assignment2")
@Setter
@Getter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    @Builder
    public UserEntity(Long id, String username, String password, Set<RoleEntity> roles, Set<ScreenShotEntity> screenShots, LogInfoEntity logInfo, Set<ChatEntity> chatMessages) {
        super(id);
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.screenShots = screenShots;
        this.logInfo = logInfo;
        this.chatMessages = chatMessages;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private Set<ChatEntity> chatMessages = new HashSet<>();
}
