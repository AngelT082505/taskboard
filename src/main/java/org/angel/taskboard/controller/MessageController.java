package org.angel.taskboard.controller;

import org.angel.taskboard.entity.Message;
import org.angel.taskboard.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestBody Message message) {
        Message sent = messageService.sendMessage(senderId, receiverId, message);
        return ResponseEntity.ok(sent);
    }

    @GetMapping("/inbox/{userId}")
    public ResponseEntity<List<Message>> getInbox(@PathVariable Long userId) {
        List<Message> inbox = messageService.getMessagesByUserId(userId);
        return ResponseEntity.ok(inbox);
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<Message>> getSent(@PathVariable Long userId) {
        List<Message> sent = messageService.getMessagesBySenderId(userId);
        return ResponseEntity.ok(sent);
    }

    @PutMapping("/read/{messageId}")
    public ResponseEntity<Message> markAsRead(@PathVariable Long messageId) {
        Message msg = messageService.markAsReaded(messageId);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}

