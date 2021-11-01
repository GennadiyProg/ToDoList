package ru.snapgot.todolist;

import java.util.ArrayList;

public class InMemoryTaskDao implements TaskDao {
    private ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> getTasks(){
        return tasks;
    }

    @Override
    public void addTask(int id, String description){
        tasks.add(new Task(id, description));
    }

    @Override
    public void toggleTask(int id){
        tasks.stream().filter(task -> task.getId() == id).forEach(task -> task.setCompleted(!task.isCompleted()));
    }

    @Override
    public void deleteTask(int id){
        tasks.remove(tasks.stream().filter(task -> task.getId() == id).toList().get(0));
    }

    @Override
    public void editTask(int id, String newTask){
        tasks.stream().filter(task -> task.getId() == id).toList().get(0).setDescription(newTask);
    }
}
