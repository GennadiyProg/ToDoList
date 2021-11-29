package ru.snapgot.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.service.impI.CommandDescription;
import ru.snapgot.todolist.service.impI.PrintTasks;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/todolist")
public class ToDoListController{
    protected final TaskManager taskManager;
    protected final ErrorHandler errorHandler;

    @Autowired
    public ToDoListController(TaskManager taskManager, ErrorHandler errorHandler) {
        this.taskManager = taskManager;
        this.errorHandler = errorHandler;
    }

    @PostMapping
    public void addTask(@RequestBody CommandDescription commandDescription){
        String task = commandDescription.getText();
        if (task == null){
            errorHandler.handler("add: Не было ввода задачи");
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
    public void getTasks(@PathVariable String command, @RequestBody CommandDescription commandDescription){
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
        PrintTasks printTasks = new PrintTasks();
        printTasks.accept(tasks);
    }

    @PatchMapping("/toggel/{id}")
    public void toggleTask(@PathVariable @Min(1) int id){
        taskManager.toggle(id);
    }
}
