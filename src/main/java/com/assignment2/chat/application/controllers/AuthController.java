package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.models.LogInfo;
import com.assignment2.chat.application.services.LogInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;

import static com.assignment2.chat.application.constants.CommonConstants.ROLE_ADMIN;
import static com.assignment2.chat.application.constants.CommonConstants.ROLE_MEMBER;

@Controller
@AllArgsConstructor
public class AuthController {

    AuthenticationManager authenticationManager;
    LogInfoService logInfoService;

    /**
     *
     * @return homepage
     */
    @GetMapping({"/", "/index"})
    public String index() {
        return "homepage";
    }

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
        Date date = new Date(System.currentTimeMillis());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User logUser = (User) auth.getPrincipal();

        LogInfo logout = logInfoService.findLogEntityByUser(logUser);
        logout.setLastLogoutDate(date);
        logout.setUser(logUser);

        new SecurityContextLogoutHandler().logout(request, response, auth);

        logInfoService.saveOrUpdate(logout);

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
    public String accessDenied() {
        return "403";
    }

    /**
     *
     * @param principal
     * @return home page based on user role
     */
    @GetMapping("/home")
    public String homepage( Principal principal) {
        String successUrl = "/homepage";

        User logUser = (User) ((Authentication) principal).getPrincipal();
        Date date = new Date(System.currentTimeMillis());
        LogInfo login;
        try {
            login = logInfoService.findLogEntityByUser(logUser);
        } catch (Exception ex){
            login = new LogInfo();
        }

        login.setUser(logUser);
        login.setLastLoginDate(date);

        logInfoService.saveOrUpdate(login);

        Collection<GrantedAuthority> authorityCollections = logUser.getAuthorities();

        for(GrantedAuthority authority : authorityCollections){
            System.out.println(authority.toString());
            if (ROLE_ADMIN.equalsIgnoreCase(authority.toString())){
                successUrl = "/admin/home";
                break;
            }
            if(ROLE_MEMBER.equalsIgnoreCase(authority.toString())){
                successUrl ="/home";
            }
        }
        return "redirect:" + successUrl;
    }

}
