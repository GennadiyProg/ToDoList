package ru.snapgot.todolist.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(name = "completed")
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

    public Task() {

    }
}
