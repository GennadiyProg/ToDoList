package ru.snapgot.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.dto.CommandDescriptionDto;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.repos.UserRepo;
import ru.snapgot.todolist.service.CompositeTaskService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/customer/tasks/")
public class TaskController {
    private final UserRepo userRepo;
    private final CompositeTaskService taskService;

    @PostMapping
    public Task addTask(@RequestBody @Valid CommandDescriptionDto commandDescriptionDto, Principal principal){
        return taskService.save(commandDescriptionDto.getText(), userRepo.findByUsername(principal.getName()));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable() String id, Principal principal){
        taskService.delete(id, userRepo.findByUsername(principal.getName()));
    }

    @PatchMapping("{id}/modification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTask(@RequestParam(value = "newDescription") String newDescription,
                         @PathVariable String id,
                         Principal principal){
        taskService.edit(newDescription, id, userRepo.findByUsername(principal.getName()));
    }

    @GetMapping
    public List<DisplayTaskDto> getTasks(@RequestParam(name = "isAll") boolean isAll,
                                         @RequestParam(name= "search", required = false, defaultValue = "") String search,
                                         Principal principal){
        return taskService.get(isAll, search, userRepo.findByUsername(principal.getName()));
    }

    @PatchMapping("{id}/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleTask(@PathVariable String id,
                           Principal principal){
        taskService.toggle(id, userRepo.findByUsername(principal.getName()));
    }
}
