package ru.snapgot.todolist.service.impI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.snapgot.todolist.service.ErrorHandler;

@Slf4j
@Component
public class ErrorHandlerImpI implements ErrorHandler {
    @Override
    public void handler(String message) {
        System.out.println(message);
        log.error(message);
    }
}
