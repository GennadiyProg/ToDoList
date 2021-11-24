package ru.snapgot.todolist.service.impI.commands;

import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.TaskManager;

public abstract class CommandBase {
    protected final TaskManager taskManager;
    protected final ErrorHandler errorHandler;

    protected CommandBase(TaskManager taskManager, ErrorHandler errorHandler){
        this.taskManager = taskManager;
        this.errorHandler = errorHandler;
    }
}
