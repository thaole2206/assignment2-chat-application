package com.assignment2.chat.application.requests;

import com.assignment2.chat.application.entities.UserEntity;
import lombok.Data;

@Data
public class ScreenShotRequest {

    private UserEntity user;
    private byte[] file;
}
