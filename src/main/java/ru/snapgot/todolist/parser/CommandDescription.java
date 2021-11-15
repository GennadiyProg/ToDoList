package ru.snapgot.todolist.parser;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommandDescription {
    private String name;
    private int taskId;
    private String text;
    private String args;
}
