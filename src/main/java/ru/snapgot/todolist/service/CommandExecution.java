package ru.snapgot.todolist.service;

import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

public interface CommandExecution extends Consumer<CommandDescription> {
    String getName();
}
