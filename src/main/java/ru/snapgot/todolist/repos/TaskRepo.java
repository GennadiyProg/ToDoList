package ru.snapgot.todolist.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.snapgot.todolist.model.Task;
import ru.snapgot.todolist.model.dto.TaskDto;
import ru.snapgot.todolist.model.User;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = :taskId AND t.user = :user")
    void deleteTask(@Param("taskId") long id,@Param("user") User user);

    @Modifying
    @Query("UPDATE Task t SET t.completed = (case t.completed when false then true else false end) WHERE t.id = :taskId AND t.user = :user")
    void toggleTask(@Param("taskId") long id,@Param("user") User user);

    @Modifying
    @Query(value = "UPDATE Task t SET t.description = :description WHERE t.id = :taskId AND t.user = :user")
    void editTask(@Param("taskId") long id,@Param("description") String text,@Param("user") User user);

    @Query(value = "SELECT t.id AS id, t.description AS description, t.completed AS completed FROM Task t WHERE (:isAll = true OR t.completed = :isAll) AND " +
            "((:subString = '') OR t.description LIKE %:subString%) AND t.user = :user")
    List<TaskDto> getFilteredTask(@Param("isAll") boolean isAll, @Param("subString") String subString, @Param("user") User user);
}
