package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.CommandExecution;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

@Component
public class DeleteCommandExecution extends CommandBase implements CommandExecution {
    private final String NAME = "delete";

    @Autowired
    public DeleteCommandExecution(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        if (commandDescription.getTaskId() == 0){
            errorHandler.handler("delete: Неверно указан id команды");
            return;
        }
        taskManager.delete(commandDescription.getTaskId());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
