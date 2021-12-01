package ru.snapgot.todolist.service.impI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.dao.TaskDao;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.service.TaskManager;

import java.util.List;

@Component
public class TaskManagerImpI implements TaskManager {
    private final TaskDao taskDao;

    @Autowired
    public TaskManagerImpI(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void add(String description){
        Task task = new Task(description, false);
        taskDao.addTask(task);
    }

    @Override
    public List<Task> getTasks(boolean isAll, String searchFilter){
        List<Task> tasks;
        if (searchFilter != null){
            tasks = taskDao.getFilteredTasks(searchFilter);
        } else {
            tasks = taskDao.getAllTasks();
        }
        if (isAll){
            return tasks;
        } else {
            return tasks.stream()
                    .filter(task -> !task.isCompleted()).toList();
        }
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
