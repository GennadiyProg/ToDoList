package ru.snapgot.todolist.model.clientdto;

import lombok.Data;
import ru.snapgot.todolist.model.clientdto.enumeration.TaskStatus;

@Data
public class ClientTaskDto {
    private Long id;
    private String description;
    private TaskStatus taskStatus;
}
