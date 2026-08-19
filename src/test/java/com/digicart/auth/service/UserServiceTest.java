package com.digicart.auth.service;

import com.digicart.auth.dto.CreateUserRequest;
import com.digicart.auth.dto.UpdateUserRequest;
import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createSetsEmailAndRole() {
        CreateUserRequest req = new CreateUserRequest();
        req.setEmail("a@b.com");
        req.setRole(Role.merchant);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User user = userService.create(req);
        assertThat(user.getEmail()).isEqualTo("a@b.com");
        assertThat(user.getRole()).isEqualTo(Role.merchant);
    }

    @Test
    void updateBlocksUser() {
        User existing = new User();
        existing.setId("u1");
        existing.setEmail("a@b.com");
        when(userRepository.findById("u1")).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        UpdateUserRequest req = new UpdateUserRequest();
        req.setBlocked(true);
        assertThat(userService.update("u1", req).getBlocked()).isTrue();
    }

    @Test
    void deleteMissingThrows() {
        when(userRepository.existsById("nope")).thenReturn(false);
        assertThatThrownBy(() -> userService.delete("nope")).isInstanceOf(EntityNotFoundException.class);
    }
}
