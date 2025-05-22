package org.angel.taskboard.repository;

import org.angel.taskboard.entity.Message;
import org.angel.taskboard.entity.User;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiver(User receiver);
    List<Message> findBySender(User sender);


}
