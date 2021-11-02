package ru.snapgot.todolist;

import java.util.ArrayList;
import java.util.List;

public interface TaskDao {
    void addTask(Task task);
    ArrayList<Task> getAllTasks();
    List<Task> getCompletedTasks();
    List<Task> getUncompletedTasks();
    List<Task> getFilteredTasks(String description);
    Task getTaskById(int id);
    void deleteTask(int id);
}
