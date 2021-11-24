package ru.snapgot.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import ru.snapgot.todolist.service.impI.CommandHandler;
import ru.snapgot.todolist.parser.CommandDescription;
import ru.snapgot.todolist.parser.ProcessingInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

@Controller
public class ToDoListController implements CommandLineRunner {
    Consumer<CommandDescription> consumer;
    ProcessingInput processingInput;

    @Autowired
    public ToDoListController(CommandHandler commandHandler, ProcessingInput processingInput) {
        this.consumer = commandHandler;
        this.processingInput = processingInput;
    }

    @Override
    public void run(String... args) {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        console.lines().map(processingInput).forEach(consumer);
    }
}
