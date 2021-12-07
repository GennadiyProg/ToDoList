package ru.snapgot.todolist.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.snapgot.todolist.model.Task;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface TaskRepo extends JpaRepository<Task, Integer> {

    @Modifying
    @Query("UPDATE Task t SET t.completed = (case t.completed when false then true else false end) WHERE t.id = ?1")
    void toggleTask(int id);

    @Modifying
    @Query(value = "UPDATE Task t SET t.description = ?2 WHERE t.id = ?1")
    void editTask(int id, String text);

    @Query(value = "SELECT t FROM Task t WHERE (:isAll = true OR t.completed = :isAll) AND " +
            "((:subString = '') OR t.description LIKE %:subString%)")
    List<Task> getFilteredTask(@Param("isAll") boolean isAll, @Param("subString") String subString);
}
