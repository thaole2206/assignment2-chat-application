package com.assignment2.chat.application.response;

import lombok.Data;

@Data
public class ScreenShotResponse {

    private String username;
    private byte[] file;
}
