package ru.snapgot.todolist.service.impI.commands;

import lombok.extern.slf4j.Slf4j;
import ru.snapgot.todolist.model.Task;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class PrintTasks implements Consumer<List<Task>> {

    @Override
    public void accept(List<Task> tasks) {
        StringBuilder stringBuilder = new StringBuilder();
        tasks.forEach(task -> {
            StringBuilder message = new StringBuilder();
            message.append(task.getId()).append(".")
                    .append(task.isCompleted() ?  " [X] " : " [ ] ").append(task.getDescription());
            stringBuilder.append(message).append("\n");
        });
        System.out.print(stringBuilder);
        log.debug(stringBuilder.toString());
    }
}
