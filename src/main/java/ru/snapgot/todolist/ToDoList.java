package ru.snapgot.todolist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ToDoList {
    public static final String ADD = "add";
    public static final String PRINT = "print";
    public static final String TOGGLE = "toggle";
    public static final String QUIT = "quit";
    public static final String DELETE = "delete";
    public static final String EDIT = "edit";
    public static final String SEARCH = "search";
    public static ArrayList<Task> tasks = new ArrayList<>();

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
        int id = tasks.size() == 0 ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        tasks.add(new Task(id, sbTask.toString().trim()));
    }

    private static void print(String[] command){
        if (command.length == 2 && command[1].equals("all")){
            if (tasks.isEmpty()) {
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isStatus() ?  "X" : " ", task.getDescribe()));
        } else if (command.length == 1){
            if (tasks.stream().allMatch(Task::isStatus)){
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.stream()
                    .filter(task -> !task.isStatus())
                    .forEach(task -> System.out.printf("%d. [ ] %s%n", task.getId(), task.getDescribe()));
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
        if (tasks.stream()
                .noneMatch(task -> task.getDescribe().contains(sbTask.toString().trim()))){
            System.out.println("Задач с такое подстрокой нет");
            return;
        }
        tasks.stream()
                .filter(task -> task.getDescribe().contains(sbTask.toString().trim()))
                .forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isStatus() ?  "X" : " ", task.getDescribe()));
    }

    private static void toggle(String[] command){
        if (command.length != 2){
            System.out.println("Неверные агрументы команды 'toggle'");
            return;
        }
        int toggleId;
        try {
            toggleId = Integer.parseInt(command[1]);
            if (tasks.stream().anyMatch(task -> task.getId() == toggleId)){
                tasks.stream().filter(task -> task.getId() == toggleId).forEach(task -> task.setStatus(!task.isStatus()));
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
            if (tasks.stream().anyMatch(task -> task.getId() == deletedId)){
                tasks.remove(tasks.stream().filter(task -> task.getId() == deletedId).findAny().get());
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
            if (tasks.stream().noneMatch(task -> task.getId() == editId)){
                System.out.println("Задачи с таким id не существует");
                return;
            }
            StringBuilder sbTask = new StringBuilder();
            for (int i = 2; i < command.length; i++){
                sbTask.append(command[i]).append(" ");
            }
            tasks.stream().filter(task -> task.getId() == editId).forEach(task -> task.setDescribe(sbTask.toString().trim()));
        } catch (NumberFormatException e) {
            System.out.println("Введенное id не является числом");
        }
    }
}
