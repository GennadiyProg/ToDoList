package ru.snapgot.todolist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DisplayTaskDto {
    private String id;
    private String description;
    private boolean taskStatus;
}
