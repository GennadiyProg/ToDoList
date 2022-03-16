package ru.snapgot.todolist.controllers;

import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.snapgot.todolist.model.dto.CommandDescriptionDto;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.repos.UserRepo;
import ru.snapgot.todolist.service.CompositeTaskService;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Mock
    private TaskRepo taskRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private CompositeTaskService taskService;

    TaskController taskController;
    private Principal principal = new UserPrincipal("User");

    @BeforeEach
    public void setUp(){
        taskController = new TaskController(userRepo, taskService);
        when(userRepo.findByUsername(principal.getName())).thenReturn(null);
    }

    @Test
    public void addTask_createTaskAndSavedTask_Always() {
        CommandDescriptionDto dto = new CommandDescriptionDto();
        Task task = new Task(null, false, null);

        Task newTask = taskController.addTask(dto, principal);

        verify(taskRepo, times(1)).save(task);
        assertNull(newTask.getDescription());
        assertFalse(newTask.isCompleted());
        assertNull(newTask.getUser());
    }

    @Test
    public void deleteTask_calledTaskRepo_Once() {
        String id = "A1";

        taskController.deleteTask(id, principal);

        verify(taskRepo).deleteTask(1, null);
        verifyNoMoreInteractions(taskRepo);
    }
}