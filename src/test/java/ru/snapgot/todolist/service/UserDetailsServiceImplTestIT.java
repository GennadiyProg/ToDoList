package ru.snapgot.todolist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.snapgot.todolist.model.Role;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;
import ru.snapgot.todolist.service.impl.UserDetailsServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
class UserDetailsServiceImplTestIT {
    @Autowired
    private UserRepo userRepo;
    private final String username = "User";

    @BeforeEach
    void setUp() {
        User user = new User(username, "password", Role.CUSTOMER);
        userRepo.save(user);
    }

    @Test
    void loadUserByUsername_isReturnedNecessaryTypeData_Always() {
        UserDetailsServiceImpl service = new UserDetailsServiceImpl(userRepo);

        Object returnedClass = service.loadUserByUsername(username);

        assertInstanceOf(org.springframework.security.core.userdetails.User.class, returnedClass);
    }
}