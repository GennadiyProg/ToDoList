package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.CommandExecution;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

@Component
public class EditCommandExecution extends CommandBase implements CommandExecution {
    private final String NAME = "edit";

    @Autowired
    public EditCommandExecution(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

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

    @Override
    public String getName() {
        return NAME;
    }
}
