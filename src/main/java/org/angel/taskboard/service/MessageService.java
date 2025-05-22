package org.angel.taskboard.service;


import org.angel.taskboard.entity.Message;
import org.angel.taskboard.entity.User;
import org.angel.taskboard.repository.MessageRepository;
import org.angel.taskboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;



    public Message sendMessage(Long senderId, Long receiverId, Message message){
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setReaded(false);

        return messageRepository.save(message);
    }

    public List<Message> getMessagesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return messageRepository.findByReceiver(user);
    }

    public List<Message> getMessagesBySenderId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return messageRepository.findBySender(user);
    }


    public Message markAsReaded(Long messageId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setReaded(true);

        return messageRepository.save(message);
    }

    public void deleteMessage(Long messageId){
        if (!messageRepository.existsById(messageId)) {
            throw new RuntimeException("Message not found");
        }
        messageRepository.deleteById(messageId);
    }

}
