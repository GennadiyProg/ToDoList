package ru.snapgot.todolist;

import java.io.IOException;

public class ToDoList {

    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new ProcessingInput();
        inputHandler.processingInput();
    }
}
