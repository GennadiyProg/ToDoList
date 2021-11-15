package ru.snapgot.todolist.logic.impI;

import ru.snapgot.todolist.logic.TaskDao;
import ru.snapgot.todolist.logic.TaskManager;
import ru.snapgot.todolist.model.Task;

import java.util.List;

public class TaskManagerImpI implements TaskManager {
    private final TaskDao taskDao;

    public TaskManagerImpI(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void add(String description){
        Task task = new Task(description, false);
        taskDao.addTask(task);
    }

    @Override
    public List<Task> getAllTasks(){
        return taskDao.getAllTasks();
    }

    @Override
    public List<Task> getUncompletedTasks(){
        return taskDao.getUncompletedTasks();
    }

    @Override
    public List<Task> getFilteredTasks(String description){
        return taskDao.getFilteredTasks(description);
    }

    @Override
    public void toggle(int id){
        Task task = taskDao.getTaskById(id);
        if (task != null){
            task.setCompleted(!task.isCompleted());
        }
    }

    @Override
    public void delete(int id){
        taskDao.deleteTask(id);
    }

    @Override
    public void edit(int id, String newTask){
        Task task = taskDao.getTaskById(id);
        if (task != null){
            task.setDescription(newTask);
        }
    }
}
