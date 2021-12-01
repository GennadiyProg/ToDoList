package ru.snapgot.todolist.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.service.impI.CommandDescription;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/tasks/")
public class ToDoListController{
    private final TaskManager taskManager;

    @Autowired
    public ToDoListController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @PostMapping
    public void addTask(@RequestBody @Valid CommandDescription commandDescription){
        taskManager.add(commandDescription.getText());
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable() @Min(1) int id){
        taskManager.delete(id);
    }

    @PatchMapping("{id}/modification")
    public void editTask(@RequestBody @Valid CommandDescription commandDescription, @PathVariable @Min(1) int id){
        taskManager.edit(id, commandDescription.getText());
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(name = "isAll") boolean isAll,
                               @RequestBody CommandDescription commandDescription){
        return taskManager.getTasks(isAll, commandDescription.getText());
    }

    @PatchMapping("{id}/completed")
    public void toggleTask(@PathVariable @Min(1) int id){
        taskManager.toggle(id);
    }
}
