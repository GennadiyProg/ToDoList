package ru.snapgot.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.NewCustomerDto;
import ru.snapgot.todolist.model.Role;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import java.util.List;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public User createCustomer(@RequestBody NewCustomerDto newCustomerDto){
        User user = new User(newCustomerDto.getUsername(), passwordEncoder.encode(newCustomerDto.getPassword()), Role.CUSTOMER);
        userRepo.save(user);
        return user;
    }

    @GetMapping
    public List<User> getAllCustomers(){
        return userRepo.findAll();
    }

    @DeleteMapping("{username}")
    public void deleteUser(@PathVariable String username){
        userRepo.deleteByUsername(username);
    }
}
