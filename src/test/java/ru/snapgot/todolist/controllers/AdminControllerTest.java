package ru.snapgot.todolist.controllers;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.snapgot.todolist.model.NewCustomerDto;
import ru.snapgot.todolist.model.Role;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    @Mock
    PasswordEncoder encoder;
    @Mock
    UserRepo userRepo;
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
    void createCustomer_isCreatedCustomer_True(){
        NewCustomerDto newCustomerDto = new NewCustomerDto();
        User user = new User(newCustomerDto.getUsername(), encoder.encode(newCustomerDto.getPassword()), Role.CUSTOMER);
        AdminController adminController = new AdminController(userRepo, encoder);

        User newUser = adminController.createCustomer(newCustomerDto);

        assertEquals(user.getUsername(), newUser.getUsername());
        assertEquals(user.getPassword(), newUser.getPassword());
    }

    @Test
    void createCustomer_isCalledUserRepo_Once() {
        NewCustomerDto newCustomerDto = new NewCustomerDto();
        User user = new User(newCustomerDto.getUsername(), newCustomerDto.getPassword(), Role.CUSTOMER);
        AdminController adminController = new AdminController(userRepo, encoder);

        adminController.createCustomer(newCustomerDto);

        verify(userRepo, times(1)).save(user);
    }

    @Test
    void getAllCustomers_isCalledUserRepo_Once() {
        AdminController adminController = new AdminController(userRepo, encoder);

        adminController.getAllCustomers();

        verify(userRepo, times(1)).findAll();
    }

    @Test
    void deleteUser_isCalledUserRepo_Once() {
        String username = "Kate";
        AdminController adminController = new AdminController(userRepo, encoder);

        adminController.deleteUser(username);

        verify(userRepo, times(1)).deleteByUsername(username);
    }
}