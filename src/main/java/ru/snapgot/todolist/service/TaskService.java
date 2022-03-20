package ru.snapgot.todolist.service;

import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface TaskService {
    Task save(String description, User user);
    void delete(String id, User user);
    void edit(String newDescription, String id, User user);
    void toggle(String id, User user);
    Future<List<DisplayTaskDto>> get(boolean isAll, String search, User user) throws ExecutionException, InterruptedException;
}
