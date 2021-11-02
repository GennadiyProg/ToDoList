package ru.snapgot.todolist;

import java.util.List;

public class TaskManagerImlI implements TaskManager{
    private TaskDao taskDao;

    public TaskManagerImlI(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void add(String description){
        taskDao.addTask(description);
    }

    @Override
    public List<Task> getTasks(boolean argAll){
        if (argAll){
            return taskDao.getAllTasks();
        } else {
            return taskDao.getUncompletedTasks();
        }
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
