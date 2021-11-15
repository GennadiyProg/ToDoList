package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

public class ToggleCommand extends CommandBase implements Consumer<CommandDescription> {

    public ToggleCommand(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        if (commandDescription.getTaskId() == 0) {
            errorHandler.handler("toggle: Неверно указан id команды");
            return;
        }
        taskManager.toggle(commandDescription.getTaskId());
    }
}
