package ru.snapgot.todolist.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserRepo userRepo;
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void createUser_calledUserRepo_Once() {
        UserController controller = new UserController(encoder, userRepo);
        User user = new User();
        user.setPassword("password");

        controller.createUser(user);

        verify(userRepo, times(1)).save(user);
    }
}