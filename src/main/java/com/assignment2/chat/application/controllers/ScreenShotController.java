package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.requests.ScreenShotRequest;
import com.assignment2.chat.application.response.ScreenShotResponse;
import com.assignment2.chat.application.services.ScreenShotService;
import com.assignment2.chat.application.services.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Base64;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/screen")
public class ScreenShotController {

    private final ScreenShotService screenShotService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/capture")
    @ResponseBody
    public String captureScreen(@RequestBody String base64Img, Principal principal){
        User logUser = (User) ((Authentication) principal).getPrincipal();
        UserEntity entity = userDetailsService.findUserByUsername(logUser.getUsername());

        byte[] decodedBytes = Base64.getDecoder().decode(base64Img.getBytes());

        ScreenShotRequest request = new ScreenShotRequest();
        request.setUser(entity);
        request.setFile(decodedBytes);

        ScreenShotResponse response = screenShotService.save(request);
        log.info("Screen captured for: " + response.getUsername());
        return null;
    }
}
