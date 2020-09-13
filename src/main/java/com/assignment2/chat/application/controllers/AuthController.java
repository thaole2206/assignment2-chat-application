package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.services.LogInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;


@Controller
@AllArgsConstructor
public class AuthController {

    private final LogInfoService logInfoService;

    /**
     *
     * @return login page
     */
    @GetMapping("/login")
    public ModelAndView showLoginPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/signout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User logUser = (User) auth.getPrincipal();

        new SecurityContextLogoutHandler().logout(request, response, auth);

        logInfoService.recordLogOut(logUser);

        return "redirect:/login";
    }

    @GetMapping("/home")
    public String processRecordLogIn(Principal principal){
        User logUser = (User) ((Authentication) principal).getPrincipal();

        logInfoService.recordLogIn(logUser);

        return "redirect:/user/home";
    }

    /**
     *
     * @return 403 error page
     */
    @GetMapping("/403")
    public String accessDenied() {
        return "/error/403";
    }

}
