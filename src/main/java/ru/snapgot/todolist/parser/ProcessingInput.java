package ru.snapgot.todolist.parser;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ProcessingInput {
    private static final Pattern COMMAND_PATTERN =
            Pattern.compile("\\s*(?<cmd>\\w+)(?:\\s+(?<args>(?:(?<id>\\d+)\\b)?(?<text>.*)))?");


    public CommandDescription processingInput(String command){
        log.debug("Пользователь ввел: '{}'", command);
        Matcher matcher = COMMAND_PATTERN.matcher(command);
        if (matcher.find()) {
            CommandDescription commandDescription;
            CommandDescription.CommandDescriptionBuilder builder = CommandDescription.builder()
                    .name(matcher.group("cmd"))
                    .args(matcher.group("args"))
                    .text(matcher.group("text"));
            String taskId = matcher.group("id");
            if (taskId != null) {
                try {
                    builder.taskId(Integer.parseInt(taskId));
                } catch (NumberFormatException ex) {
                    log.error("Не удается считать число: {}", taskId, ex);
                }
            }
            commandDescription = builder.build();
            return commandDescription;
        } else {
            System.out.println("Отсутствует команда");
        }
        return null;
    }
}
