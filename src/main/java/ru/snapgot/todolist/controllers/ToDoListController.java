package ru.snapgot.todolist.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.dao.TaskDao;
import ru.snapgot.todolist.model.CommandDescription;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/tasks/")
public class ToDoListController{
    private final TaskDao taskDao;

    @Autowired
    public ToDoListController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @PostMapping
    public void addTask(@RequestBody @Valid CommandDescription commandDescription){
        taskDao.add(commandDescription.getText());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable() @Min(1) int id){
        taskDao.delete(id);
    }

    @PatchMapping("{id}/modification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTask(@RequestParam(value = "newDescription") String newDescription,
                                           @PathVariable @Min(1) int id){
        taskDao.edit(id, newDescription);
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(name = "isAll") boolean isAll,
                               @RequestParam(name= "search", required = false, defaultValue = "") String search){
        return taskDao.getTasks(isAll, search);
    }

    @PatchMapping("{id}/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleTask(@PathVariable @Min(1) int id){
        taskDao.toggle(id);
    }
}
