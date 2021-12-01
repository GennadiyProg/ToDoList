package ru.snapgot.todolist.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
    private int id;
    private String description;
    private boolean completed;

    public Task(int id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public Task(String description, boolean completed){
        this.description = description;
        this.completed = completed;
    }
}
