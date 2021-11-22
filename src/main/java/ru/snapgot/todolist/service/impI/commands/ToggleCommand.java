package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

@Component
public class ToggleCommand extends CommandBase implements Consumer<CommandDescription> {

    @Autowired
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
