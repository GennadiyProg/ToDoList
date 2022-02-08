package ru.snapgot.todolist.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.snapgot.todolist.model.NewCustomerDto;
import ru.snapgot.todolist.model.Role;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

@SpringBootTest
class AdminControllerTest {
    @MockBean
    PasswordEncoder encoder;
    @MockBean
    UserRepo userRepo;

    @Test
    void createCustomer_isCreatedCustomer_True(){
        NewCustomerDto newCustomerDto = new NewCustomerDto();
        User user = new User(newCustomerDto.getUsername(), encoder.encode(newCustomerDto.getPassword()), Role.CUSTOMER);
        AdminController adminController = new AdminController(userRepo, encoder);

        User newUser = adminController.createCustomer(newCustomerDto);

        Assertions.assertEquals(user, newUser);
    }

    @Test
    void createCustomer_isCalledUserRepo_Once() {
        NewCustomerDto newCustomerDto = new NewCustomerDto();
        User user = new User(newCustomerDto.getUsername(), newCustomerDto.getPassword(), Role.CUSTOMER);
        AdminController adminController = new AdminController(userRepo, encoder);

        adminController.createCustomer(newCustomerDto);

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void getAllCustomers_isCalledUserRepo_Once() {
        AdminController adminController = new AdminController(userRepo, encoder);

        adminController.getAllCustomers();

        Mockito.verify(userRepo, Mockito.times(1)).findAll();
    }

    @Test
    void deleteUser_isCalledUserRepo_Once() {
        String username = "Kate";
        AdminController adminController = new AdminController(userRepo, encoder);

        adminController.deleteUser(username);

        Mockito.verify(userRepo, Mockito.times(1)).deleteByUsername(username);
    }
}