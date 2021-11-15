package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

public class AddCommand extends CommandBase implements Consumer<CommandDescription> {

    public AddCommand(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        String task = commandDescription.getArgs();
        if (task == null){
            errorHandler.handler("add: Не было ввода задачи");
            return;
        }
        taskManager.add(task);
    }
}
