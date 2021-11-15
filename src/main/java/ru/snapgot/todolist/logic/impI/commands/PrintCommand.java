package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.List;
import java.util.function.Consumer;

public class PrintCommand extends CommandBase implements Consumer<CommandDescription> {

    public PrintCommand(TaskManager taskManager, ErrorHandler errorHandler){super(taskManager, errorHandler);}

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
}
