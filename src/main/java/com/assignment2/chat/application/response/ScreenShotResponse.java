package com.assignment2.chat.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class ScreenShotResponse {

    private String username;
    private byte[] file;
}
