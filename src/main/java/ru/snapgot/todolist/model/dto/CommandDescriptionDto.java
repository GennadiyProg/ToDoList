package ru.snapgot.todolist.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommandDescriptionDto {
    @NotEmpty
    private String text;
}
