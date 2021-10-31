package ru.snapgot.todolist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Consumer;

public class ProcessingInput implements InputHandler {
    private  HashMap<String, Consumer<String[]>> commands = new HashMap<>();
    private TaskHandler taskHandler = new TaskDAO();

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
            commands.put("add", this::add);
            commands.put("print", this::print);
            commands.put("search", this::search);
            commands.put("toggle", this::toggle);
            commands.put("delete", this::delete);
            commands.put("edit", this::edit);
            try{
                commands.get(command[0]).accept(command);
            } catch (NullPointerException e){
                if (command[0].equals("quit")){
                    return;
                }
                System.out.println("Введена неизвестная команда");
            }
        }
    }

    private void add(String[] command){
        if (command.length == 1){
            System.out.println("Не было ввода задачи");
            return;
        }
        StringBuilder sbTask = new StringBuilder();
        for (int i = 1; i < command.length; i++){
            sbTask.append(command[i]).append(" ");
        }
        taskHandler.addTask(sbTask.toString().trim());
    }

    private void print(String[] command){
        if (command.length == 2 && command[1].equals("all")){
            taskHandler.printTask(true);
        } else if (command.length == 1){
            taskHandler.printTask(false);
        } else {
            System.out.println("Неверные агрументы команды 'print'");
        }
    }

    private void search(String[] command){
        if (command.length == 1){
            System.out.println("Не было ввода подстроки");
            return;
        }
        StringBuilder sbTask = new StringBuilder();
        for (int i = 1; i < command.length; i++){
            sbTask.append(command[i]).append(" ");
        }
        taskHandler.searchTask(sbTask.toString().trim());
}

    private void toggle(String[] command){
        if (command.length != 2){
            System.out.println("Неверные агрументы команды 'toggle'");
            return;
        }
        int toggleId;
        try {
            toggleId = Integer.parseInt(command[1]);
            taskHandler.toggleTask(toggleId);
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }

    private void delete(String[] command){
        if (command.length != 2){
            System.out.println("Неверные агрументы команды 'delete'");
            return;
        }
        int deletedId;
        try {
            deletedId = Integer.parseInt(command[1]);
            taskHandler.deleteTask(deletedId);
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }

    private void edit(String[] command){
        if (command.length < 3){
            System.out.println("Неверные агрументы команды 'edit'");
            return;
        }
        int editId;
        try {
            editId = Integer.parseInt(command[1]);
            StringBuilder sbTask = new StringBuilder();
            for (int i = 2; i < command.length; i++){
                sbTask.append(command[i]).append(" ");
            }
            taskHandler.editTask(editId, sbTask.toString().trim());
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }
}
