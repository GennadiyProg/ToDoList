package ru.snapgot.todolist.controllers.client;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.clientdto.ClientTaskDto;
import ru.snapgot.todolist.model.dto.CommandDescriptionDto;
import ru.snapgot.todolist.model.dto.TaskDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@FeignClient(name = "new-client", url = "${feign.client.url}")
public interface ClientRequests {
    @PostMapping
    public ClientTaskDto create(@Valid @RequestBody CommandDescriptionDto request);

    @DeleteMapping("/{id}")
    public void deleteTask(@Min(0) @PathVariable(value = "id") Long id);

    @PatchMapping("/{id}")
    public ClientTaskDto editTask(@Min(0) @PathVariable(value = "id") Long id, @NotBlank @Size(min = 2, max = 100) @RequestParam(value = "description") String description);

    @PostMapping("/{id}/toggle")
    public ClientTaskDto toggleTask(@Min(0) @PathVariable(value = "id") Long id);

    @GetMapping
    public List<ClientTaskDto> getList(@RequestParam(value = "printMod", required = false) String printMod);
}
