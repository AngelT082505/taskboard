package org.angel.taskboard.repository;

import org.angel.taskboard.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserIdAndActiveTrue(Long userId);

    Optional<Session> findByToken(String token);
}
