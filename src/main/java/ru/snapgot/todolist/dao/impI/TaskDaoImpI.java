package ru.snapgot.todolist.dao.impI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.dao.TaskDao;

import java.util.List;

@Component
public class TaskDaoImpI implements TaskDao {
    private final TaskRepo taskRepo;

    @Autowired
    public TaskDaoImpI(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public void add(String description){
        taskRepo.save(new Task(description, false));
    }

    @Override
    public List<Task> getTasks(boolean isAll, String searchFilter){
        return taskRepo.getFilteredTask(isAll, searchFilter);
    }

    @Override
    public void toggle(int id){
        taskRepo.toggleTask(id);
    }

    @Override
    public void delete(int id){
        taskRepo.deleteById(id);
    }

    @Override
    public void edit(int id, String newTask){
        taskRepo.editTask(id, newTask);
    }
}
