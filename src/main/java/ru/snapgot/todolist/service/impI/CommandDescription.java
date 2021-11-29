package ru.snapgot.todolist.service.impI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
public class CommandDescription {
    @NotEmpty
    private String text;
}
