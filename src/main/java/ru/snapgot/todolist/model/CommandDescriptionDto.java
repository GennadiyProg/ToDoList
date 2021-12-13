package ru.snapgot.todolist.model;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommandDescriptionDto {
    @NotEmpty
    private String text;
}
