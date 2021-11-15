package ru.snapgot.todolist.logic.impI;

import lombok.extern.slf4j.Slf4j;
import ru.snapgot.todolist.logic.CommandHandler;
import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.impI.commands.*;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

@Slf4j
public class CommandHandlerImpI implements CommandHandler {
    private final CommandFactory commandFactory;
    private final ErrorHandler errorHandler;

    public CommandHandlerImpI(CommandFactory commandFactory, ErrorHandler errorHandler){
        this.commandFactory = commandFactory;
        this.errorHandler = errorHandler;
    }

    @Override
    public void callingCommand(CommandDescription commandDescription) {
        Consumer<CommandDescription> consumer = commandFactory.getCommands().get(commandDescription.getName());
        if (consumer != null){
            consumer.accept(commandDescription);
        } else {
            errorHandler.handler("Введена неизвестная команда");
        }
    }
}
