package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.CommandExecution;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

@Component
public class ToggleCommandExecution extends CommandBase implements CommandExecution {
    private final String NAME = "toggle";

    @Autowired
    public ToggleCommandExecution(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        if (commandDescription.getTaskId() == 0) {
            errorHandler.handler("toggle: Неверно указан id команды");
            return;
        }
        taskManager.toggle(commandDescription.getTaskId());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
