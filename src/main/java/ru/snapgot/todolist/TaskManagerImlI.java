package ru.snapgot.todolist;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerImlI implements TaskManager{
    private TaskDao taskDao;

    public TaskManagerImlI(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void add(String description){
        ArrayList<Task> tasks = taskDao.getTasks();
        int id = tasks.size() == 0 ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        taskDao.addTask(id, description);
    }

    @Override
    public List<Task> print(boolean argAll){
        ArrayList<Task> tasks = taskDao.getTasks();
        if (argAll){
            return tasks;
        } else {
            return tasks.stream()
                    .filter(task -> !task.isCompleted()).toList();
        }
    }

    @Override
    public List<Task> search(String description){
        ArrayList<Task> tasks = taskDao.getTasks();
        return tasks.stream()
                .filter(task -> task.getDescription().contains(description)).toList();
    }

    @Override
    public void toggle(int id){
        List<Task> tasks = taskDao.getTasks();
        tasks = tasks.stream().filter(task -> task.getId() == id).toList();
        if(!tasks.isEmpty()){
            taskDao.toggleTask(tasks.get(0).getId());
        }
    }

    @Override
    public void delete(int id){
        List<Task> tasks = taskDao.getTasks();
        tasks = tasks.stream().filter(task -> task.getId() == id).toList();
        if(!tasks.isEmpty()){
            taskDao.deleteTask(tasks.get(0).getId());
        }
    }

    @Override
    public void edit(int id, String newTask){
        List<Task> tasks = taskDao.getTasks();
        tasks = tasks.stream().filter(task -> task.getId() == id).toList();
        if (!tasks.isEmpty()){
            taskDao.editTask(tasks.get(0).getId(), newTask);
        }
    }
}
