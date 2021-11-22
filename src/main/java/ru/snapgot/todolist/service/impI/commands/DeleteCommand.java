package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

@Component
public class DeleteCommand extends CommandBase implements Consumer<CommandDescription> {

    @Autowired
    public DeleteCommand(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        if (commandDescription.getTaskId() == 0){
            errorHandler.handler("delete: Неверно указан id команды");
            return;
        }
        taskManager.delete(commandDescription.getTaskId());
    }
}
