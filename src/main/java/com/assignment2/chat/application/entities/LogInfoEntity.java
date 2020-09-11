package com.assignment2.chat.application.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Setter@Getter
@Entity
@Table(name="LogInfo", schema = "Assignment2")
public class LogInfoEntity extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private Date lastLoginDate;
    private Date lastLogoutDate;

}
