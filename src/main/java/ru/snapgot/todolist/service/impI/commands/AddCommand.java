package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.function.Consumer;

@Component
public class AddCommand extends CommandBase implements Consumer<CommandDescription> {

    @Autowired
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
