package org.angel.taskboard.repository;

import org.angel.taskboard.entity.Task;
import org.angel.taskboard.entity.User;
import org.angel.taskboard.enums.StatusEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
   List<Task> findByUserId(Long userId);
    List<Task> findByStatus(StatusEnums status);

    void deleteByUser(User user);
    List<Task> findByUserIdOrderByUrgencyDesc(Long userId);
    List<Task> findByUserIdOrderByDateEndAsc(Long userId);
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);

}
