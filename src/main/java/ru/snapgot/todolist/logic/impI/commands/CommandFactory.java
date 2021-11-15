package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.HashMap;
import java.util.function.Consumer;

public class CommandFactory {
    private final HashMap<String, Consumer<CommandDescription>> commands = new HashMap<>();
    private final TaskManager taskManager;
    private final ErrorHandler errorHandler;

    public CommandFactory(TaskManager taskManager, ErrorHandler errorHandler){
        this.taskManager = taskManager;
        this.errorHandler = errorHandler;
    }

    public HashMap<String, Consumer<CommandDescription>> getCommands(){
        commands.put("add", new AddCommand(taskManager, errorHandler));
        commands.put("print", new PrintCommand(taskManager, errorHandler));
        commands.put("search", new SearchCommand(taskManager, errorHandler));
        commands.put("toggle", new ToggleCommand(taskManager, errorHandler));
        commands.put("delete", new DeleteCommand(taskManager, errorHandler));
        commands.put("edit", new EditCommand(taskManager, errorHandler));
        commands.put("quit", new QuitCommand());

        return commands;
    }
}
