package ru.snapgot.todolist.logic.impI;

import ru.snapgot.todolist.logic.TaskDao;
import ru.snapgot.todolist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskDao implements TaskDao {
    private final ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public void addTask(Task task){
        int id = tasks.size() == 0 ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        task.setId(id);
        tasks.add(task);
    }

    @Override
    public ArrayList<Task> getAllTasks(){
        return tasks;
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
        return tasks.stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void deleteTask(int id){
        tasks.removeIf(task -> task.getId() == id);
    }
}
