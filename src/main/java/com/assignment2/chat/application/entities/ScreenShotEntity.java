package com.assignment2.chat.application.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "ScreenShot", schema = "Assignment2")
public class ScreenShotEntity extends BaseEntity{

    @Lob
    private byte[] screenshot;

    private Date createdAt;

    @ManyToOne
    private UserEntity user;


}
