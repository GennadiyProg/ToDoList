package ru.snapgot.todolist.controllers;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import ru.snapgot.todolist.controllers.client.ClientRequests;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.model.dto.TaskDto;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.repos.UserRepo;
import ru.snapgot.todolist.service.impl.ServerTaskServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

@SpringBootTest
@TestPropertySource("/application_test.properties")
public class TaskControllerClientTestIT {
    @MockBean
    private TaskRepo taskRepo;
    @Autowired
    private TaskController taskController;
    private final Principal principal = new UserPrincipal("User");

    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void registerDynamicProperties (DynamicPropertyRegistry registry) {
        registry.add("feign.client.url", wm::baseUrl);
    }


    @Test
    public void getTasks_ReturnRightTasksList_Always(WireMockRuntimeInfo wireMockRuntimeInfo) throws ExecutionException, InterruptedException {
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(get("/tasks?printMod=ALL").willReturn(
                okJson("[{\"id\":3, \"description\":\"random task\", \"taskStatus\":\"CREATED\"}]"))
        );

        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto() {
            @Override
            public Long getId() {
                return 3L;
            }

            @Override
            public String getDescription() {
                return "description";
            }

            @Override
            public Boolean getCompleted() {
                return true;
            }
        });
        when(taskRepo.getFilteredTask(true, "", null))
                .thenReturn(tasks);

        List<DisplayTaskDto> expectingTasks = new ArrayList<>();
        expectingTasks.add(new DisplayTaskDto("B3", "random task", false));
        expectingTasks.add(new DisplayTaskDto("A3", "description", true));

        List<DisplayTaskDto> resultingTasks = taskController.getTasks(true, "", principal);

        assertEquals(expectingTasks.get(0).getId(), resultingTasks.get(0).getId());
        assertEquals(expectingTasks.get(0).getDescription(), resultingTasks.get(0).getDescription());
        assertEquals(expectingTasks.get(0).isTaskStatus(), resultingTasks.get(0).isTaskStatus());

        assertEquals(expectingTasks.get(1).getId(), resultingTasks.get(1).getId());
        assertEquals(expectingTasks.get(1).getDescription(), resultingTasks.get(1).getDescription());
        assertEquals(expectingTasks.get(1).isTaskStatus(), resultingTasks.get(1).isTaskStatus());
    }
}
