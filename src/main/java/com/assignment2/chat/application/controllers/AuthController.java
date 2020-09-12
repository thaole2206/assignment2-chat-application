package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.services.LogInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;


@Controller
@AllArgsConstructor
public class AuthController {

    AuthenticationManager authenticationManager;
    LogInfoService logInfoService;

    /**
     *
     * @return login page
     */
    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/signout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        LocalDateTime date = LocalDateTime.now();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User logUser = (User) auth.getPrincipal();

        new SecurityContextLogoutHandler().logout(request, response, auth);

        logInfoService.recordLogOut(logUser, date);

        return "redirect:/login";
    }

    /**
     *
     * @return login error page
     */
    @GetMapping("/signin?error")
    public String showLoginErrorPage(){
        return "err.signin";
    }

    /**
     *
     * @return admin page
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    /**
     *
     * @return 403 error page
     */
    @GetMapping("/403")
    public String accessDenied(Model model) {
        model.addAttribute("param.error", "Wrong username or password");
        return "/error/403";
    }

    /**
     *
     * @param principal
     * @return home page based on user role
     */
    @GetMapping("/home")
    public String homepage(Principal principal) {
        User logUser = (User) ((Authentication) principal).getPrincipal();
        LocalDateTime date = LocalDateTime.now();

        logInfoService.recordLogIn(logUser, date);

        return ("/user/home");
    }

}
