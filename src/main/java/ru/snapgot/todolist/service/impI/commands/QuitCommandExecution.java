package ru.snapgot.todolist.service.impI.commands;

import org.springframework.stereotype.Component;
import ru.snapgot.todolist.parser.CommandDescription;
import ru.snapgot.todolist.service.CommandExecution;

@Component
public class QuitCommandExecution implements CommandExecution {
    private final String NAME = "quit";

    @Override
    public void accept(CommandDescription commandDescription) {
        System.exit(0);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
