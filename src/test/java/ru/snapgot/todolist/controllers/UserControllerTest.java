package ru.snapgot.todolist.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

class UserControllerTest {
    @Mock
    User user;
    @Mock
    UserRepo userRepo;
    @Mock
    PasswordEncoder encoder;

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_isCalledUserRepo_Once() {
        UserController controller = new UserController(encoder, userRepo);
        user.setPassword("hello");
        controller.createUser(user);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }
}