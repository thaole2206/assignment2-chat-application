package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.models.ChatMessage;
import com.assignment2.chat.application.services.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class MemberController {

    private final ChatService chatService;

    @GetMapping("/user/home")
    public ModelAndView getIndexPage(){
        List<ChatMessage> messagesList = chatService.loadAllChatMessage();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/homepage");
        mv.getModel().put("allMessages", messagesList);

        return mv;
    }
}
