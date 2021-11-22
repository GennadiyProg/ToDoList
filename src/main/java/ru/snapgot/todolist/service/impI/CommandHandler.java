package ru.snapgot.todolist.service.impI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;
import ru.snapgot.todolist.service.impI.commands.*;
import ru.snapgot.todolist.parser.CommandDescription;

import java.util.HashMap;
import java.util.function.Consumer;

@Component
public class CommandHandler implements Consumer<CommandDescription> {
    private final HashMap<String, Consumer<CommandDescription>> commands = new HashMap<>();
    private final AddCommand addCommand;
    private final PrintCommand printCommand;
    private final SearchCommand searchCommand;
    private final ToggleCommand toggleCommand;
    private final DeleteCommand deleteCommand;
    private final EditCommand editCommand;
    private final QuitCommand quitCommand;
    private final ErrorHandler errorHandler;

    @Autowired
    public CommandHandler(AddCommand addCommand,
                          PrintCommand printCommand,
                          SearchCommand searchCommand,
                          ToggleCommand toggleCommand,
                          DeleteCommand deleteCommand,
                          EditCommand editCommand,
                          QuitCommand quitCommand,
                          ErrorHandler errorHandler) {
        this.addCommand = addCommand;
        this.printCommand = printCommand;
        this.searchCommand = searchCommand;
        this.toggleCommand = toggleCommand;
        this.deleteCommand = deleteCommand;
        this.editCommand = editCommand;
        this.quitCommand = quitCommand;
        this.errorHandler = errorHandler;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        commands.put("add", addCommand);
        commands.put("print", printCommand);
        commands.put("search", searchCommand);
        commands.put("toggle", toggleCommand);
        commands.put("delete", deleteCommand);
        commands.put("edit", editCommand);
        commands.put("quit", quitCommand);

        Consumer<CommandDescription> command = null;
        if (commandDescription != null) command = commands.get(commandDescription.getName());
        if (command != null) command.accept(commandDescription);
        else errorHandler.handler("Введена неверная команда");
    }
}
