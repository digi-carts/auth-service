package com.digicart.auth.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import com.digicart.auth.exception.GlobalExceptionHandler;
import com.digicart.auth.controller.HealthController;
import com.digicart.auth.controller.UserController;
import com.digicart.auth.service.UserService;

@CucumberContextConfiguration
@WebMvcTest(controllers = { HealthController.class, UserController.class })
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
public class CucumberSpringConfiguration {
    @MockBean
    UserService userService;

}
