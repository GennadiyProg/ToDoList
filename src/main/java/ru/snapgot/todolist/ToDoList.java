package ru.snapgot.todolist;

import java.io.IOException;

public class ToDoList {

    public static void main(String[] args) throws IOException {
        TaskHandler taskHandler = new TaskDao();
        InputHandler inputHandler = new ProcessingInput(taskHandler);
        inputHandler.processingInput();
    }
}
