package ru.snapgot.todolist.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.service.impI.CommandDescription;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/todolist")
public class ToDoListController{
    private final TaskManager taskManager;

    @Autowired
    public ToDoListController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @PostMapping
    public void addTask(@RequestBody CommandDescription commandDescription){
        String task = commandDescription.getText();
        if (task == null){
            log.error("add: Не было ввода задачи");
            return;
        }
        taskManager.add(task);
    }

    @PutMapping("/delete/{id}")
    public void deleteTask(@PathVariable() @Min(1) int id){
        taskManager.delete(id);
    }

    @PatchMapping("/edit/{id}")
    public void editTask(@RequestBody CommandDescription commandDescription, @PathVariable @Min(1) int id){
        taskManager.edit(id, commandDescription.getText());
    }

    @GetMapping(value = {"/{command}"})
    public List<Task> getTasks(@PathVariable String command, @RequestBody CommandDescription commandDescription){
        List<Task> tasks;
        String arg = commandDescription.getText();
        if (command.equals("print")){
            if (arg.equals("all")){
                tasks = taskManager.getAllTasks();
            } else {
                tasks = taskManager.getUncompletedTasks();
            }
        } else {
            tasks = taskManager.getFilteredTasks(arg);
        }
        return tasks;
    }

    @PatchMapping("/toggle/{id}")
    public void toggleTask(@PathVariable @Min(1) int id){
        taskManager.toggle(id);
    }
}
