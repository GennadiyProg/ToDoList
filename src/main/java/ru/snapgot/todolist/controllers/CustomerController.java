package ru.snapgot.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.CommandDescriptionDto;
import ru.snapgot.todolist.model.TaskDto;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.repos.UserRepo;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/customer/tasks/")
public class CustomerController {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Autowired
    public CustomerController(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public void addTask(@RequestBody @Valid CommandDescriptionDto commandDescriptionDto, Principal principal){
        taskRepo.save(new Task(commandDescriptionDto.getText(), false, userRepo.findByUsername(principal.getName())));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable() @Min(1) long id,
                           Principal principal){
        taskRepo.deleteTask(id, userRepo.findByUsername(principal.getName()));
    }

    @PatchMapping("{id}/modification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTask(@RequestParam(value = "newDescription") String newDescription,
                                           @PathVariable @Min(1) long id,
                         Principal principal){
        taskRepo.editTask(id, newDescription, userRepo.findByUsername(principal.getName()));
    }

    @GetMapping
    public List<TaskDto> getTasks(@RequestParam(name = "isAll") boolean isAll,
                                  @RequestParam(name= "search", required = false, defaultValue = "") String search,
                                  Principal principal){
        return taskRepo.getFilteredTask(isAll, search, userRepo.findByUsername(principal.getName()));
    }

    @PatchMapping("{id}/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleTask(@PathVariable @Min(1) long id,
                           Principal principal){
        taskRepo.toggleTask(id, userRepo.findByUsername(principal.getName()));
    }
}
