package ru.snapgot.todolist.logic.impI.commands;

import lombok.extern.slf4j.Slf4j;
import ru.snapgot.todolist.model.Task;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class PrintTasks implements Consumer<List<Task>> {

    @Override
    public void accept(List<Task> tasks) {
        tasks.forEach(task -> System.out.printf("%d. [%s] %s%n", task.getId(), task.isCompleted() ?  "X" : " ", task.getDescription()));
        tasks.forEach(task -> log.debug("{}. [{}] {}", task.getId(), (task.isCompleted() ?  "X" : " "), task.getDescription()));
    }
}
