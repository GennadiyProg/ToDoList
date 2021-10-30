package ru.snapgot.todolist;

import java.util.ArrayList;

public class TaskDAO {
    public static ArrayList<Task> tasks = new ArrayList<>();

    public static void addTask(String task){
        int id = tasks.size() == 0 ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        tasks.add(new Task(id, task));
    }

    public static void printTask(boolean argAll){
        if (argAll){
            if (tasks.isEmpty()) {
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isStatus() ?  "X" : " ", task.getDescribe()));
        } else {
            if (tasks.stream().allMatch(Task::isStatus)){
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.stream()
                    .filter(task -> !task.isStatus())
                    .forEach(task -> System.out.printf("%d. [ ] %s%n", task.getId(), task.getDescribe()));
        }
    }

    public static void searchTask(String subString){
        if (tasks.stream()
                .noneMatch(task -> task.getDescribe().contains(subString))){
            System.out.println("Задач с такое подстрокой нет");
            return;
        }
        tasks.stream()
                .filter(task -> task.getDescribe().contains(subString))
                .forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isStatus() ?  "X" : " ", task.getDescribe()));
    }

    public static void toggleTask(int id){
        if (tasks.stream().anyMatch(task -> task.getId() == id)){
            tasks.stream().filter(task -> task.getId() == id).forEach(task -> task.setStatus(!task.isStatus()));
        } else {
            System.out.println("Задачи с таким id не существует");
        }
    }

    public static void deleteTask(int id){
        if (tasks.stream().anyMatch(task -> task.getId() == id)){
            tasks.remove(tasks.stream().filter(task -> task.getId() == id).findAny().orElse(null));
        } else {
            System.out.println("Задачи с таким id не существует");
        }
    }

    public static void editTask(int id, String newTask){
        if (tasks.stream().noneMatch(task -> task.getId() == id)){
            System.out.println("Задачи с таким id не существует");
            return;
        }
        tasks.stream().filter(task -> task.getId() == id).forEach(task -> task.setDescribe(newTask));
    }
}
