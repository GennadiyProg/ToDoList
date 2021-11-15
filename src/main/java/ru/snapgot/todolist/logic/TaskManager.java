package ru.snapgot.todolist.logic;

import ru.snapgot.todolist.model.Task;

import java.util.List;

public interface TaskManager {
    void add(String task);
    List<Task> getAllTasks();
    List<Task> getUncompletedTasks();
    List<Task> getFilteredTasks(String description);
    void toggle(int id);
    void delete(int id);
    void edit(int id, String newTask);
}
