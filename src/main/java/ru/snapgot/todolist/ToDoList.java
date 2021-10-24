package ru.snapgot.todolist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ToDoList {
    public static final String ADD = "add";
    public static final String PRINT = "print";
    public static final String TOGGLE = "toggle";
    public static final String QUIT = "quit";
    public static final String DELETE = "delete";
    public static final String EDIT = "edit";
    public static final String SEARCH = "search";
    public static HashMap<Integer, String> tasks = new HashMap<>();
    public static HashMap<Integer, Boolean> status = new HashMap<>();

    public static void main(String[] args) throws IOException {
        while (true){
            System.out.print("Ввод команды: ");
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String[] command = console.readLine().trim().split(" ");
            if (command[0].equals("")){
                System.out.println("Отсутствует команда");
                continue;
            }
            switch (command[0]){
                case ADD:
                    add(command);
                    break;
                case PRINT:
                    print(command);
                    break;
                case SEARCH:
                    search(command);
                    break;
                case TOGGLE:
                    toggle(command);
                    break;
                case DELETE:
                    delete(command);
                    break;
                case EDIT:
                    edit(command);
                    break;
                case QUIT:
                    return;
                default:
                    System.out.println("Введена некорректная команда");
                    break;
            }
        }
    }

    private static void add(String[] command){
        if (command.length == 1){
            System.out.println("Не было ввода задачи");
            return;
        }
        StringBuilder sbTask = new StringBuilder();
        for (int i = 1; i < command.length; i++){
            sbTask.append(command[i]).append(" ");
        }
        int id = tasks.size() == 0 ? 1 : Collections.max(tasks.keySet()) + 1;
        tasks.put(id, sbTask.toString().trim());
        status.put(id, false);
    }

    private static void print(String[] command){
        if (command.length == 2 && command[1].equals("all")){
            if (tasks.isEmpty()) {
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.forEach((id, task) -> System.out.printf("%d. [%s] %s%n", id, status.get(id) ?  "X" : " ", task));
        } else if (command.length == 1){
            if (status.entrySet().stream().allMatch(Map.Entry::getValue)){
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.keySet()
                    .stream()
                    .filter(id -> !status.get(id))
                    .forEach(id -> System.out.printf("%d. [ ] %s%n", id, tasks.get(id)));
        } else {
            System.out.println("Неверные агрументы команды 'print'");
        }
    }

    private static void search(String[] command){
        if (command.length == 1){
            System.out.println("Не было ввода подстроки");
            return;
        }
        StringBuilder sbTask = new StringBuilder();
        for (int i = 1; i < command.length; i++){
            sbTask.append(command[i]).append(" ");
        }
        if (tasks.entrySet()
                .stream()
                .noneMatch(task -> task.getValue().contains(sbTask.toString().trim()))){
            System.out.println("Задач с такое подстрокой нет");
            return;
        }
        tasks.keySet()
                .stream()
                .filter(task -> tasks.get(task).contains(sbTask.toString().trim()))
                .forEach(id -> System.out.printf("%d. [%s] %s%n", id, status.get(id) ?  "X" : " ", tasks.get(id)));
    }

    private static void toggle(String[] command){
        if (command.length != 2){
            System.out.println("Неверные агрументы команды 'toggle'");
            return;
        }
        int toggleId;
        try {
            toggleId = Integer.parseInt(command[1]);
            if (tasks.keySet().stream().anyMatch(id -> id == toggleId)){
                status.replace(toggleId, !status.get(toggleId));
            } else {
                System.out.println("Задачи с таким id не существует");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }

    private static void delete(String[] command){
        if (command.length != 2){
            System.out.println("Неверные агрументы команды 'delete'");
            return;
        }
        int deletedId;
        try {
            deletedId = Integer.parseInt(command[1]);
            if (tasks.keySet().stream().anyMatch(id -> id == deletedId)){
                tasks.remove(deletedId);
                status.remove(deletedId);
            } else {
                System.out.println("Задачи с таким id не существует");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }

    private static void edit(String[] command){
        if (command.length < 3){
            System.out.println("Неверные агрументы команды 'edit'");
            return;
        }
        int editId;
        try {
            editId = Integer.parseInt(command[1]);
            if (tasks.keySet().stream().noneMatch(id -> id == editId)){
                System.out.println("Задачи с таким id не существует");
                return;
            }
            StringBuilder sbTask = new StringBuilder();
            for (int i = 2; i < command.length; i++){
                sbTask.append(command[i]).append(" ");
            }
            tasks.put(editId, sbTask.toString().trim());
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }
}
