package ru.snapgot.todolist.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {
    @MockBean
    User user;
    @Mock
    UserRepo userRepo;
    @MockBean
    PasswordEncoder encoder;

    @Test
    public void createUser_isCalledUserRepo_Once() {
        UserController controller = new UserController(encoder, userRepo);
        user.setPassword("hello");
        controller.createUser(user);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }
}