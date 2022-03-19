package ru.snapgot.todolist.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.snapgot.todolist.model.dto.NewCustomerDto;
import ru.snapgot.todolist.model.Role;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    private final static PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Mock
    private UserRepo userRepo;
    private User user;
    private AdminController adminController;

    @BeforeEach
    public void setUp(){
        user = createUser();
        adminController = new AdminController(userRepo, encoder);
    }

    @Test
    public void createCustomer_savedCustomerAndHiddenPasswordWhenReturn_Always(){
        NewCustomerDto customerDto = new NewCustomerDto();
        customerDto.setUsername(user.getUsername());
        customerDto.setPassword(user.getPassword());

        User newUser = adminController.createCustomer(customerDto);

        verify(userRepo).save(any(User.class));
        assertEquals("*****", newUser.getPassword());
    }

    @Test
    public void getAllCustomers_calledUserRepo_Once() {
        adminController.getAllCustomers();

        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void deleteUser_calledUserRepo_Once() {
        adminController.deleteUser(user.getUsername());

        verify(userRepo, times(1)).deleteByUsername(user.getUsername());
    }

    private static User createUser() {
        User testUser = new User();
        testUser.setUsername("User");
        testUser.setPassword("password");
        testUser.setRole(Role.CUSTOMER);
        return testUser;
    }
}