package ru.snapgot.todolist.service.impI.commands;

import org.springframework.stereotype.Component;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

@Component
public class QuitCommand  implements Consumer<CommandDescription> {

    @Override
    public void accept(CommandDescription commandDescription) {
        System.exit(0);
    }
}
