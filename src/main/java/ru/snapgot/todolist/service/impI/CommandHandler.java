package ru.snapgot.todolist.service.impI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.CommandExecution;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.List;
import java.util.function.Consumer;

@Component
public class CommandHandler implements Consumer<CommandDescription> {
    private final List<CommandExecution> commands;
    private final ErrorHandler errorHandler;

    @Autowired
    public CommandHandler(List<CommandExecution> commands, ErrorHandler errorHandler) {
        this.commands = commands;
        this.errorHandler = errorHandler;
    }

    @Override
    public void accept(CommandDescription commandDescription) {

        CommandExecution command = null;
        if (commandDescription != null) command = commands.stream()
                .filter(command1 -> command1.getName().equals(commandDescription.getName()))
                .findFirst()
                .orElse(null);
        if (command != null) command.accept(commandDescription);
        else errorHandler.handler("Введена неверная команда");
    }
}
