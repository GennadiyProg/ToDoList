package ru.snapgot.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.service.PersonalTaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ServerTaskServiceImpl implements PersonalTaskService {
    private final TaskRepo taskRepo;

    private final char SERVER_ID = 'A';

    @Override
    public Task save(String description, User user) {
        Task task = new Task(description, false, user);
        taskRepo.save(task);
        return task;
    }

    @Override
    public void delete(String id, User user) {
        taskRepo.deleteTask(Long.parseLong(id.substring(1)), user);
    }

    @Override
    public void edit(String newDescription, String id, User user) {
        taskRepo.editTask(Long.parseLong(id.substring(1)), newDescription, user);
    }

    @Override
    public void toggle(String id, User user) {
        taskRepo.toggleTask(Long.parseLong(id.substring(1)), user);
    }

    @Async
    @Override
    public Future<List<DisplayTaskDto>> get(boolean isAll, String search, User user) {
        List<DisplayTaskDto> listTasks = new ArrayList<>();
        taskRepo.getFilteredTask(isAll, search, user)
                .forEach(task -> listTasks.add(
                                new DisplayTaskDto(
                                        SERVER_ID + task.getId().toString(),
                                        task.getDescription(),
                                        task.getCompleted())
                        )
                );
        return AsyncResult.forValue(listTasks);
    }

    @Override
    public boolean supported(String id) {
        return id.charAt(0) == SERVER_ID;
    }
}
