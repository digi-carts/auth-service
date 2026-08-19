package com.digicart.auth.cucumber;

import com.digicart.auth.entity.User;
import com.digicart.auth.service.UserService;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;

public class UserStepDefinitions {
    @Autowired
    UserService userService;

    @Before
    public void stubs() {
        when(userService.findAll()).thenReturn(List.of(new User()));
    }
}
