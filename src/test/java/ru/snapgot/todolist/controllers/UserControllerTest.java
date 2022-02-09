package ru.snapgot.todolist.controllers;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    User user;
    @Mock
    UserRepo userRepo;
    @Mock
    PasswordEncoder encoder;
    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void createUser_isCalledUserRepo_Once() {
        UserController controller = new UserController(encoder, userRepo);
        user.setPassword("hello");
        controller.createUser(user);
        verify(userRepo, times(1)).save(user);
    }
}