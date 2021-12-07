package ru.snapgot.todolist.model;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommandDescription {
    @NotEmpty
    private String text;
}
