package ru.snapgot.todolist;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskDao implements TaskDao {
    private ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public void addTask(String description){
        int id = tasks.size() == 0 ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        tasks.add(new Task(id, description));
    }

    @Override
    public ArrayList<Task> getAllTasks(){
        return tasks;
    }

    @Override
    public List<Task> getCompletedTasks(){
        return tasks.stream().filter(Task::isCompleted).toList();
    }

    @Override
    public List<Task> getUncompletedTasks(){
        return tasks.stream().filter(task -> !task.isCompleted()).toList();
    }

    @Override
    public List<Task> getFilteredTasks(String description){
        return tasks.stream()
                .filter(task -> task.getDescription().contains(description)).toList();
    }

    @Override
    public Task getTaskById(int id){
        List<Task> resultTask = tasks.stream().filter(task -> task.getId() == id).toList();
        return !resultTask.isEmpty() ? resultTask.get(0) : null;
    }

    @Override
    public void deleteTask(int id){
        tasks.removeIf(task -> task.getId() == id);
    }
}
