package ru.snapgot.todolist.service.impI.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.CommandExecution;
import ru.snapgot.todolist.service.TaskManager;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.List;

@Component
public class SearchCommandExecution extends CommandBase implements CommandExecution {
    private final String NAME = "search";

    @Autowired
    public SearchCommandExecution(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

    @Override
    public void accept(CommandDescription commandDescription) {
        String subString = commandDescription.getArgs();
        if (subString == null){
            errorHandler.handler("search: Не было ввода подстроки");
            return;
        }
        List<Task> tasks = taskManager.getFilteredTasks(subString);
        if (tasks.isEmpty()){
            System.out.println("Задач с такое подстрокой нет");
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
