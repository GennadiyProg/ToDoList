package ru.snapgot.todolist.logic;

import ru.snapgot.todolist.parser.CommandDescription;

public interface CommandHandler {
    void callingCommand(CommandDescription commandDescription);
}
