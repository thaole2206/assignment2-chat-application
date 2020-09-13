package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.configs.WebSecurityTestConfig;
import com.assignment2.chat.application.response.ScreenShotResponse;
import com.assignment2.chat.application.services.ScreenShotService;
import com.assignment2.chat.application.services.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@ContextConfiguration(
        classes = WebSecurityTestConfig.class
)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private ScreenShotService screenShotService;

    @Test
    @WithUserDetails("admin@gmail.com")
    void renderImageFromDB() throws Exception {
        byte[] byteArray = {0,1,1,1,0};
        ScreenShotResponse response = ScreenShotResponse.builder().username("admin@gmail.com").file(byteArray).build();
        List<ScreenShotResponse> screenShotResponseList = new ArrayList<>(Arrays.asList(response, response));
        given(screenShotService.findByUserId(1L)).willReturn(screenShotResponseList);

        mockMvc.perform(get("/admin/user/1/images"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("images", hasSize(2)));
        verify(screenShotService, times(1)).findByUserId(1L);
        verifyNoMoreInteractions(screenShotService);
    }
}