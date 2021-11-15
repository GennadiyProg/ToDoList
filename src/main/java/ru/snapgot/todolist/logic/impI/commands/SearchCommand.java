package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.List;
import java.util.function.Consumer;

public class SearchCommand extends CommandBase implements Consumer<CommandDescription> {

    public SearchCommand(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

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
}
