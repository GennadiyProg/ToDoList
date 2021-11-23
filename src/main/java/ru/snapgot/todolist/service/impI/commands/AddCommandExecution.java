package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.CommandExecution;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

@Component
public class AddCommandExecution extends CommandBase implements CommandExecution {
    private final String NAME = "add";

    @Autowired
    public AddCommandExecution(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        String task = commandDescription.getArgs();
        if (task == null){
            errorHandler.handler("add: Не было ввода задачи");
            return;
        }
        taskManager.add(task);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
