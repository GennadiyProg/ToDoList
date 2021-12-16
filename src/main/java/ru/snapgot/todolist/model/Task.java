package ru.snapgot.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Task")
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean completed;

    @ManyToOne
    private User user;

    public Task(Long id, String description, boolean completed){
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public Task() {

    }

    public Task(String description, boolean completed, User user) {
        this.description = description;
        this.completed = completed;
        this.user = user;
    }
}
