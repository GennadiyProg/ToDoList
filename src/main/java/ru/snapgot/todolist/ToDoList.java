package ru.snapgot.todolist;

import java.io.IOException;

public class ToDoList {

    public static void main(String[] args) throws IOException {
        TaskDao taskDao = new InMemoryTaskDao();
        TaskManager taskManager = new TaskManagerImlI(taskDao);
        ConsoleHandler consoleHandler = new ConsoleHandlerImlI(taskManager);
        consoleHandler.processingInput();
    }
}
