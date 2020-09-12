package com.assignment2.chat.application.requests;

import com.assignment2.chat.application.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor@AllArgsConstructor
public class ScreenShotRequest {

    private UserEntity user;
    private byte[] file;
}
