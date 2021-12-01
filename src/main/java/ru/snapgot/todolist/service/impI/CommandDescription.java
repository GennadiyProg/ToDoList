package ru.snapgot.todolist.service.impI;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommandDescription {
    @NotEmpty
    private String text;
}
