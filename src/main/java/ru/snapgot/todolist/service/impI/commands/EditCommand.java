package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

@Component
public class EditCommand extends CommandBase implements Consumer<CommandDescription> {

    @Autowired
    public EditCommand(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        int id = commandDescription.getTaskId();
        String description = commandDescription.getText();
        if (id == 0 || description == null){
            errorHandler.handler("edit: Неверные агрументы команды");
            return;
        }
        taskManager.edit(id, description);
    }
}
