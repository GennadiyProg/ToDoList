package ru.snapgot.todolist;

import java.util.ArrayList;

public class TaskDao implements TaskHandler {
    private ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public void addTask(String task){
        int id = tasks.size() == 0 ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        tasks.add(new Task(id, task));
    }

    @Override
    public void printTask(boolean argAll){
        if (argAll){
            if (tasks.isEmpty()) {
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isCompleted() ?  "X" : " ", task.getDescription()));
        } else {
            if (tasks.stream().allMatch(Task::isCompleted)){
                System.out.println("Задачи отсутствуют");
                return;
            }
            tasks.stream()
                    .filter(task -> !task.isCompleted())
                    .forEach(task -> System.out.printf("%d. [ ] %s%n", task.getId(), task.getDescription()));
        }
    }

    @Override
    public void searchTask(String subString){
        if (tasks.stream()
                .noneMatch(task -> task.getDescription().contains(subString))){
            System.out.println("Задач с такое подстрокой нет");
            return;
        }
        tasks.stream()
                .filter(task -> task.getDescription().contains(subString))
                .forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isCompleted() ?  "X" : " ", task.getDescription()));
    }

    @Override
    public void toggleTask(int id){
        if (tasks.stream().anyMatch(task -> task.getId() == id)){
            tasks.stream().filter(task -> task.getId() == id).forEach(task -> task.setCompleted(!task.isCompleted()));
        } else {
            System.out.println("Задачи с таким id не существует");
        }
    }

    @Override
    public void deleteTask(int id){
        if (tasks.stream().anyMatch(task -> task.getId() == id)){
            tasks.remove(tasks.stream().filter(task -> task.getId() == id).findAny().orElse(null));
        } else {
            System.out.println("Задачи с таким id не существует");
        }
    }

    @Override
    public void editTask(int id, String newTask){
        if (tasks.stream().noneMatch(task -> task.getId() == id)){
            System.out.println("Задачи с таким id не существует");
            return;
        }
        tasks.stream().filter(task -> task.getId() == id).forEach(task -> task.setDescription(newTask));
    }
}
