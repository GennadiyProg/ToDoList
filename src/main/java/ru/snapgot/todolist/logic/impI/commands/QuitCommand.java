package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

public class QuitCommand  implements Consumer<CommandDescription> {

    @Override
    public void accept(CommandDescription commandDescription) {
        System.exit(0);
    }
}
