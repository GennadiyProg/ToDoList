package ru.snapgot.todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ConsoleHandlerImlI implements ConsoleHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(ConsoleHandlerImlI.class);
    private HashMap<String, Consumer<String[]>> commands = new HashMap<>();
    private TaskManager taskManager;

    ConsoleHandlerImlI(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    @Override
    public void processingInput() throws IOException {
        while (true){
            System.out.print("Ввод команды: ");
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String[] command = console.readLine().trim().split(" ");
            if (command[0].equals("")){
                System.out.println("Отсутствует команда");
                continue;
            }
            commands.put("add", this::addCommand);
            commands.put("print", this::printCommand);
            commands.put("search", this::searchCommand);
            commands.put("toggle", this::toggleCommand);
            commands.put("delete", this::deleteCommand);
            commands.put("edit", this::editCommand);
            try{
                commands.get(command[0]).accept(command);
            } catch (NullPointerException e){
                if (command[0].equals("quit")){
                    LOGGER.debug("Была введена команда 'quit'");
                    return;
                }
                System.out.println("Введена неизвестная команда");
                LOGGER.error("Введена неизвестная команда", e);
            }
        }
    }

    private void addCommand(String[] command){
        String args = createString(command, 1);
        LOGGER.debug("Была введена команда 'add' с аргументами: " + args);
        if (command.length == 1){
            System.out.println("Не было ввода задачи");
            return;
        }
        taskManager.add(args);
    }

    private void printCommand(String[] command){
        String args = createString(command, 1);
        LOGGER.debug("Была введена команда 'add' с аргументами: " + args);
        List<Task> tasks;
        if (command.length == 2 && command[1].equals("all")){
            tasks = taskManager.getAllTasks();
        } else if (command.length == 1){
            tasks = taskManager.getUncompletedTasks();
        } else {
            System.out.println("Неверные агрументы команды 'print'");
            return;
        }
        if (tasks.isEmpty()){
            System.out.println("Задачи отсутствуют");
        } else {
            tasks.forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isCompleted() ?  "X" : " ", task.getDescription()));
            tasks.forEach(task -> LOGGER.debug("{}. [{}] {}", task.getId(), (task.isCompleted() ?  "X" : " "), task.getDescription()));
        }
    }

    private void searchCommand(String[] command){
        String args = createString(command, 1);
        LOGGER.debug("Была введена команда 'search' с аргументами: " + args);
        if (command.length == 1){
            System.out.println("Не было ввода подстроки");
            return;
        }
        StringBuilder sbTask = new StringBuilder();
        List<Task> tasks;
        for (int i = 1; i < command.length; i++){
            sbTask.append(command[i]).append(" ");
        }
        tasks = taskManager.getFilteredTasks(sbTask.toString().trim());
        if (tasks.isEmpty()){
            System.out.println("Задач с такое подстрокой нет");
        } else {
            tasks.forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isCompleted() ?  "X" : " ", task.getDescription()));
            tasks.forEach(task -> LOGGER.debug("{}. [{}] {}", task.getId(), (task.isCompleted() ?  "X" : " "), task.getDescription()));
        }
    }

    private void toggleCommand(String[] command){
        String args = createString(command, 1);
        LOGGER.debug("Была введена команда 'toggle' с аргументами: " + args);
            if (command.length != 2) {
                System.out.println("Неверные аргументы коменды 'toggle'");
                LOGGER.error(args + " - Неверные аргументы коменды 'toggle'");
            }
        try {
            int toggleId;
            toggleId = Integer.parseInt(command[1]);
            taskManager.toggle(toggleId);
        } catch (NumberFormatException e){
            System.out.println("Введенное id не является числом");
            LOGGER.error("Введенное id не является числом", e);
        }
    }

    private void deleteCommand(String[] command){
        String args = createString(command, 1);
        LOGGER.debug("Была введена команда 'delete' с аргументами: " + args);
        if (command.length != 2){
            System.out.println("Неверные агрументы команды 'delete'");
            return;
        }
        int deletedId;
        try {
            deletedId = Integer.parseInt(command[1]);
            taskManager.delete(deletedId);
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
            LOGGER.error("Введенное id не является числом", e);
        }
    }

    private void editCommand(String[] command){
        String args = createString(command, 1);
        LOGGER.debug("Была введена команда 'edit' с аргументами: " + args);
        if (command.length < 3){
            System.out.println("Неверные агрументы команды 'edit'");
            return;
        }
        int editId;
        try {
            editId = Integer.parseInt(command[1]);
            taskManager.edit(editId, createString(command, 2));
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
            LOGGER.error("Введенное id не является числом", e);
        }
    }

    private String createString(String[] arrString, int index){
        StringBuilder sbString = new StringBuilder();
        for (int i = index; i < arrString.length; i++){
            sbString.append(arrString[i]).append(" ");
        }
        return sbString.toString().trim();
    }
}
