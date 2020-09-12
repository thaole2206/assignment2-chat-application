package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.models.ChatMessage;
import com.assignment2.chat.application.response.ScreenShotResponse;
import com.assignment2.chat.application.services.ChatService;
import com.assignment2.chat.application.services.ScreenShotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
public class AdminController {

    private final ScreenShotService screenShotService;
    private final ChatService chatService;

    @GetMapping("/user/{id}/images")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        List<ScreenShotResponse> screenShotResponseList = screenShotService.findByUserId(Long.valueOf(id));

        for (ScreenShotResponse screenShotResponse : screenShotResponseList) {
            if (screenShotResponse.getFile() != null) {
                byte[] byteArray = new byte[screenShotResponse.getFile().length];
                int i = 0;

                for (Byte wrappedByte : screenShotResponse.getFile()) {
                    byteArray[i++] = wrappedByte; //auto unboxing
                }

                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(byteArray);
                IOUtils.copy(is, response.getOutputStream());
            }
        }
    }

}
