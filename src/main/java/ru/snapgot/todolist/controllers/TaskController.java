package ru.snapgot.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.controllers.client.ClientRequests;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.clientdto.enumeration.TaskStatus;
import ru.snapgot.todolist.model.dto.CommandDescriptionDto;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.repos.UserRepo;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/customer/tasks/")
public class TaskController {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final ClientRequests clientRequests;

    @PostMapping
    public Task addTask(@RequestBody @Valid CommandDescriptionDto commandDescriptionDto, Principal principal){
        Task task = new Task(commandDescriptionDto.getText(), false, userRepo.findByUsername(principal.getName()));
        taskRepo.save(task);
        return task;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable() String id, Principal principal){
        switch (id.charAt(0)){
            case 'A':
                taskRepo.deleteTask(Long.parseLong(id.substring(1)), userRepo.findByUsername(principal.getName()));
                break;
            case 'B':
                clientRequests.deleteTask(Long.parseLong(id.substring(1)));
                break;
        }
    }

    @PatchMapping("{id}/modification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTask(@RequestParam(value = "newDescription") String newDescription,
                         @PathVariable String id,
                         Principal principal){
        switch (id.charAt(0)){
            case 'A':
                taskRepo.editTask(Long.parseLong(id.substring(1)), newDescription, userRepo.findByUsername(principal.getName()));
                break;
            case 'B':
                clientRequests.editTask(Long.parseLong(id.substring(1)), newDescription);
                break;
        }

    }

    @GetMapping
    public List<DisplayTaskDto> getTasks(@RequestParam(name = "isAll") boolean isAll,
                                         @RequestParam(name= "search", required = false, defaultValue = "") String search,
                                         Principal principal){
        StringBuilder sb = new StringBuilder();
        List<DisplayTaskDto> listTasks = new ArrayList<>();
        taskRepo.getFilteredTask(isAll, search, userRepo.findByUsername(principal.getName()))
                .forEach(task -> listTasks.add(
                            new DisplayTaskDto(
                                    sb.delete(0, sb.length()).append("A").append(task.getId()).toString(),
                                    task.getDescription(),
                                    task.getCompleted())
                    )
                );
        if (search.equals("")){
            if (isAll){
                clientRequests.getList("ALL")
                        .forEach(task -> listTasks.add(
                                new DisplayTaskDto(
                                        sb.delete(0, sb.length()).append("B").append(task.getId()).toString(),
                                        task.getDescription(),
                                        task.getTaskStatus() == TaskStatus.COMPLETED)
                                )
                        );
            } else {
                clientRequests.getList("CREATED")
                        .forEach(task -> listTasks.add(
                                new DisplayTaskDto(
                                        sb.delete(0, sb.length()).append("B").append(task.getId()).toString(),
                                        task.getDescription(),
                                        false)
                                )
                        );
            }
        }
        return listTasks;
    }

    @PatchMapping("{id}/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleTask(@PathVariable String id,
                           Principal principal){
        switch (id.charAt(0)){
            case 'A':
                taskRepo.toggleTask(Long.parseLong(id.substring(1)), userRepo.findByUsername(principal.getName()));
                break;
            case 'B':
                clientRequests.toggleTask(Long.parseLong(id.substring(1)));
                break;
        }

    }
}
