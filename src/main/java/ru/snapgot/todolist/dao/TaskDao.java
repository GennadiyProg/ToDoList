package ru.snapgot.todolist.dao;

import ru.snapgot.todolist.model.Task;

import java.util.List;

public interface TaskDao {
    void add(String task);
    List<Task> getTasks(boolean isAll, String searchFilter);
    void toggle(int id);
    void delete(int id);
    void edit(int id, String newTask);
}
