package ru.snapgot.todolist.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.snapgot.todolist.model.Task;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface TaskRepo extends JpaRepository<Task, Integer> {

    @Modifying
    @Query(
            value = "UPDATE Tasks SET completed = NOT completed WHERE id = ?1",
            nativeQuery = true
    )
    void toggleTask(int id);

    @Modifying
    @Query(
            value = "UPDATE Tasks SET description = ?2 WHERE id = ?1",
            nativeQuery = true
    )
    void editTask(int id, String text);

    @Query(
            value = "SELECT * FROM Tasks WHERE (?1 OR completed =?1) AND ((?2 = '') OR description LIKE %?2%)",
            nativeQuery = true
    )
    List<Task> getFilteredTask(boolean isAll, String subString);
}
