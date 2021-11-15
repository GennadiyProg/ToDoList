package ru.snapgot.todolist.logic.impI.commands;

import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskManager;

public abstract class CommandBase {
    protected final TaskManager taskManager;
    protected final ErrorHandler errorHandler;

    protected CommandBase(TaskManager taskManager, ErrorHandler errorHandler){
        this.taskManager = taskManager;
        this.errorHandler = errorHandler;
    }
}
