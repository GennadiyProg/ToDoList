package ru.snapgot.todolist.logic.impI;

import lombok.extern.slf4j.Slf4j;
import ru.snapgot.todolist.logic.ErrorHandler;

@Slf4j
public class ErrorHandlerImpI implements ErrorHandler {
    @Override
    public void handler(String message) {
        System.out.println(message);
        log.error(message);
    }
}
