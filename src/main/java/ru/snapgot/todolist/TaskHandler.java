package ru.snapgot.todolist;

public interface TaskHandler {
    void addTask(String task);
    void printTask(boolean argAll);
    void searchTask(String subString);
    void toggleTask(int id);
    void deleteTask(int id);
    void editTask(int id, String newTask);
}
