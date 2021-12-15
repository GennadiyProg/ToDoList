package ru.snapgot.todolist.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public UserController(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @PostMapping("login")
    public void createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @DeleteMapping("")
    public void deleteUser(Principal principal){
        userRepo.deleteByUsername(principal.getName());
    }
}
