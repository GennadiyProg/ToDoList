package ru.snapgot.todolist;

import java.util.ArrayList;

public interface TaskDao {
    ArrayList<Task> getTasks();
    void addTask(int id, String description);
    void toggleTask(int id);
    void deleteTask(int id);
    void editTask(int id, String newTask);
}
