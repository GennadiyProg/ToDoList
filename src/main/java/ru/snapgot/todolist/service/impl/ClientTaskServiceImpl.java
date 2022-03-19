package ru.snapgot.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.snapgot.todolist.controllers.client.ClientRequests;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.model.clientdto.enumeration.TaskStatus;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.service.PersonalTaskService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientTaskServiceImpl implements PersonalTaskService {
    private final ClientRequests clientRequests;

    private final char CLIENT_ID = 'B';

    @Override
    public Task save(String description, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String id, User user) {
        clientRequests.deleteTask(Long.parseLong(id.substring(1)));
    }

    @Override
    public void edit(String newDescription, String id, User user) {
        clientRequests.editTask(Long.parseLong(id.substring(1)), newDescription);
    }

    @Override
    public void toggle(String id, User user) {
        clientRequests.toggleTask(Long.parseLong(id.substring(1)));
    }

    @Override
    public List<DisplayTaskDto> get(boolean isAll, String search, User user) {
        List<DisplayTaskDto> listTasks = new ArrayList<>();
        if (search.equals("")){
            if (isAll){
                clientRequests.getList("ALL")
                        .forEach(task -> listTasks.add(
                                        new DisplayTaskDto(
                                                CLIENT_ID + task.getId().toString(),
                                                task.getDescription(),
                                                task.getTaskStatus() == TaskStatus.COMPLETED)
                                )
                        );
            } else {
                clientRequests.getList("CREATED")
                        .forEach(task -> listTasks.add(
                                        new DisplayTaskDto(
                                                CLIENT_ID + task.getId().toString(),
                                                task.getDescription(),
                                                false)
                                )
                        );
            }
        } else {
            if (isAll){
                clientRequests.getList("ALL")
                        .forEach(task -> {
                            if (task.getDescription().contains(search)) {
                                listTasks.add(
                                        new DisplayTaskDto(
                                                CLIENT_ID + task.getId().toString(),
                                                task.getDescription(),
                                                task.getTaskStatus() == TaskStatus.COMPLETED)
                                );
                            }
                        });
            } else {
                clientRequests.getList("CREATED")
                        .forEach(task -> {
                            if (task.getDescription().contains(search)) {
                                listTasks.add(
                                        new DisplayTaskDto(
                                                CLIENT_ID + task.getId().toString(),
                                                task.getDescription(),
                                                false)
                                );
                            }
                        });
            }
        }
        return listTasks;
    }

    @Override
    public boolean supported(String id) {
        return id.charAt(0) == CLIENT_ID;
    }
}
