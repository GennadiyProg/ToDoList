package ru.snapgot.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.service.CompositeTaskService;
import ru.snapgot.todolist.service.PersonalTaskService;
import ru.snapgot.todolist.service.TaskService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements CompositeTaskService {
    private final List<PersonalTaskService> taskServices;

    private final char SERVER_ID = 'A';

    @Override
    public Task save(String description, User user) {
        return getSupportedService(Character.toString(SERVER_ID)).save(description, user);
    }

    @Override
    public void delete(String id, User user) {
        getSupportedService(id).delete(id, user);
    }

    @Override
    public void edit(String newDescription, String id, User user) {
        getSupportedService(id).edit(newDescription, id, user);
    }

    @Override
    public void toggle(String id, User user) {
        getSupportedService(id).toggle(id, user);
    }

    @Override
    public List<DisplayTaskDto> get(boolean isAll, String search, User user) {
        List<DisplayTaskDto> tasks = new ArrayList<>();
        for(TaskService service : taskServices){
            tasks.addAll(service.get(isAll, search, user));
        }
        return tasks;
    }

    private TaskService getSupportedService(String id){
        for (PersonalTaskService service : taskServices) {
            if (service.supported(id)) return service;
        }
        throw new UnsupportedOperationException();
    }
}
