package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.CommandExecution;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.List;

@Component
public class PrintCommandExecution extends CommandBase implements CommandExecution {
    private final String NAME = "print";

    @Autowired
    public PrintCommandExecution(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        List<Task> tasks;
        String arg = commandDescription.getArgs();
        if (arg != null && arg.equals("all")){
            tasks = taskManager.getAllTasks();
        } else if (arg == null){
            tasks = taskManager.getUncompletedTasks();
        } else {
            errorHandler.handler("print: Неверные агрументы команды");
            return;
        }
        if (tasks.isEmpty()){
            System.out.println("Задачи отсутствуют");
        } else {
            PrintTasks printTasks = new PrintTasks();
            printTasks.accept(tasks);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
