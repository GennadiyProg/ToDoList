package ru.snapgot.todolist.repos;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.snapgot.todolist.model.Task;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(initializers = {TaskRepoTestIT.Initializer.class})
public class TaskRepoTestIT {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TestEntityManager entityManager;

    @ClassRule
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1-alpine");

    @BeforeAll
    public static void init(){
        container.start();
    }

    @Test
    public void addTask_saveTaskinRepo_Always(){
        taskRepo.save(createTask());

        assertEquals(1, taskRepo.count());
    }

    @Test
    public void editTask_changeTaskDescription_Always(){
        entityManager.persist(createTask());

        taskRepo.editTask(1, "newdesc", null);
        Task newTask = taskRepo.findAll().stream().findFirst().orElse(createTask());

        assertEquals(createTask().getDescription(), newTask.getDescription());
    }

    private Task createTask(){
        return new Task("description", false, null);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            ).applyTo(configurableApplicationContext);
        }
    }
}
