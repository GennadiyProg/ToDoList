package ru.snapgot.todolist;

import ru.snapgot.todolist.logic.CommandHandler;
import ru.snapgot.todolist.logic.ErrorHandler;
import ru.snapgot.todolist.logic.TaskDao;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.logic.impI.CommandHandlerImpI;
import ru.snapgot.todolist.logic.impI.ErrorHandlerImpI;
import ru.snapgot.todolist.logic.impI.InMemoryTaskDao;
import ru.snapgot.todolist.logic.impI.TaskManagerImpI;
import ru.snapgot.todolist.logic.impI.commands.CommandFactory;
import ru.snapgot.todolist.parser.CommandDescription;
import ru.snapgot.todolist.parser.ProcessingInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ToDoList {

    public static void main(String[] args) throws IOException {
        TaskDao taskDao = new InMemoryTaskDao();
        TaskManager taskManager = new TaskManagerImpI(taskDao);
        ErrorHandler errorHandler = new ErrorHandlerImpI();
        CommandFactory commandFactory = new CommandFactory(taskManager, errorHandler);
        CommandHandler commandHandler = new CommandHandlerImpI(commandFactory, errorHandler);
        ProcessingInput processingInput = new ProcessingInput();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Ввод команды: ");
            CommandDescription commandDescription = processingInput.processingInput(console.readLine());
            if (commandDescription != null){
                commandHandler.callingCommand(commandDescription);
            }
        }
    }
}
