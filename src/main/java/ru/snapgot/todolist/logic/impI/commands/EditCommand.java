package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

public class EditCommand extends CommandBase implements Consumer<CommandDescription> {

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
