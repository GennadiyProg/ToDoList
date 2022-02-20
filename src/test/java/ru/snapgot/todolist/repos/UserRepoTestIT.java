package ru.snapgot.todolist.repos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import ru.snapgot.todolist.model.Role;
import ru.snapgot.todolist.model.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource("/application_test.properties")
class UserRepoTestIT {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TestEntityManager testEntityManager;
    private final String username = "testname";
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(username, "pass", Role.CUSTOMER);
        testEntityManager.persist(user);
    }

    @Test
    void findByUsername() {
        User newUser = userRepo.findByUsername(username);

        assertEquals(username, newUser.getUsername());
        assertEquals(user.getPassword(), newUser.getPassword());
    }

    @Test
    void deleteByUsername() {
        userRepo.deleteByUsername(username);

        assertEquals(0, userRepo.count());
    }
}