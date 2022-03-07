package ru.snapgot.todolist.controllers;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sun.security.auth.UserPrincipal;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;
import ru.snapgot.todolist.controllers.client.ClientRequests;
import ru.snapgot.todolist.model.dto.DisplayTaskDto;
import ru.snapgot.todolist.model.dto.TaskDto;
import ru.snapgot.todolist.repos.TaskRepo;
import ru.snapgot.todolist.repos.UserRepo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ExtendWith(MockitoExtension.class)

@SpringBootTest
@TestPropertySource("/application_test.properties")
@WireMockTest(httpPort = 8081)
public class TaskControllerClientTestIT {
    @Mock
    private TaskRepo taskRepo;
    @Mock
    private UserRepo userRepo;
    @Autowired
    private ClientRequests clientRequests;
    private TaskController taskController;
    private final Principal principal = new UserPrincipal("User");

    @BeforeEach
    public void setUp(){
        taskController = new TaskController(taskRepo, userRepo, clientRequests);
    }

    @Test
    public void deleteTask_SendDeleteRequestToClient_Always(WireMockRuntimeInfo wireMockRuntimeInfo){
        WireMock wireMock = wireMockRuntimeInfo.getWireMock();
        wireMock.register(delete("/tasks/12").willReturn(ok()));

        taskController.deleteTask("B12", principal);
        verify(deleteRequestedFor(urlEqualTo("/tasks/12")));
    }

    @Test
    public void getTasks(WireMockRuntimeInfo wireMockRuntimeInfo){
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
        Mockito.when(taskRepo.getFilteredTask(true, "", userRepo.findByUsername(principal.getName())))
                .thenReturn(tasks);
        List<DisplayTaskDto> expectingTasks = new ArrayList<>();
        expectingTasks.add(new DisplayTaskDto("A3", "description", true));
        expectingTasks.add(new DisplayTaskDto("B3", "random task", false));

        List<DisplayTaskDto> resultingTasks = taskController.getTasks(true, "", principal);

        Assertions.assertEquals(expectingTasks.get(0).getId(), resultingTasks.get(0).getId());
        Assertions.assertEquals(expectingTasks.get(0).getDescription(), resultingTasks.get(0).getDescription());
        Assertions.assertEquals(expectingTasks.get(0).isTaskStatus(), resultingTasks.get(0).isTaskStatus());

        Assertions.assertEquals(expectingTasks.get(1).getId(), resultingTasks.get(1).getId());
        Assertions.assertEquals(expectingTasks.get(1).getDescription(), resultingTasks.get(1).getDescription());
        Assertions.assertEquals(expectingTasks.get(1).isTaskStatus(), resultingTasks.get(1).isTaskStatus());
    }
}
