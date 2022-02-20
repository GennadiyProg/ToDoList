package ru.snapgot.todolist.controllers;

import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.snapgot.todolist.model.*;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.repos.UserRepo;

import javax.validation.ConstraintViolationException;
import java.security.Principal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
class TaskControllerTestIT {

    private final TaskController controller;
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private Task task;
    private final Principal principal = new UserPrincipal("User");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TaskControllerTestIT(TaskRepo taskRepo, UserRepo userRepo, TaskController controller) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.controller = controller;
    }

    @BeforeEach
    public void setUp(){
        User user = new User("User", "password", Role.CUSTOMER);
        userRepo.save(user);
        task = new Task("description", false, user);
    }

    @AfterEach
    public void tearDown(){
        taskRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
        assertThat(taskRepo).isNotNull();
        assertThat(userRepo).isNotNull();
    }

    @Test
    public void addTask_isAuthorizedAndValidationAndSavedTaskInRepo_Always() throws Exception {
        CommandDescriptionDto commandDescriptionDto = new CommandDescriptionDto();
        assertThrows(ConstraintViolationException.class, () -> controller.addTask(commandDescriptionDto, principal));
        commandDescriptionDto.setText("description");

        controller.addTask(commandDescriptionDto, principal);

        assertEquals(1, taskRepo.count());
        mockMvc.perform(post("/")).andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteTask_isAuthorizedAndDeleteTaskInRepo_Always() throws Exception {
        taskRepo.save(task);
        assertEquals(1, taskRepo.count());

        controller.deleteTask(3, principal);

        assertEquals(0, taskRepo.count());
        mockMvc.perform(delete("/1")).andExpect(status().isUnauthorized());
    }

    @Test
    public void editTask_isAuthorizedAndEditTaskInRepo_Always() throws Exception {
        String newDescription = "superTask";
        taskRepo.save(task);

        controller.editTask(newDescription, 1, principal);

        assertEquals(newDescription, taskRepo.findById(1L).orElse(task).getDescription());
        mockMvc.perform(patch("/1/modification")).andExpect(status().isUnauthorized());
    }
}