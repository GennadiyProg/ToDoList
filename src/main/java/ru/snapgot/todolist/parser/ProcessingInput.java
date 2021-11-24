package ru.snapgot.todolist.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ProcessingInput implements Function<String, CommandDescription> {
    private static final Pattern COMMAND_PATTERN =
            Pattern.compile("\\s*(?<cmd>\\w+)(?:\\s+(?<args>(?:(?<id>\\d+)\\b)?(?<text>.*)))?");

    @Override
    public CommandDescription apply(String command) {
        log.debug("Пользователь ввел: '{}'", command);
        Matcher matcher = COMMAND_PATTERN.matcher(command);
        if (matcher.find()) {
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
            return builder.build();
        }
        return null;
    }
}
