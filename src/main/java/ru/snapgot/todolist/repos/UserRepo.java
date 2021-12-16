package ru.snapgot.todolist.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.snapgot.todolist.model.User;


@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    void deleteByUsername(String username);
}
