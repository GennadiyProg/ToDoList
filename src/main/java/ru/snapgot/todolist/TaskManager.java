package ru.snapgot.todolist;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void add(String task);
    List<Task> print(boolean argAll);
    List<Task> search(String description);
    void toggle(int id);
    void delete(int id);
    void edit(int id, String newTask);
}
