package org.angel.taskboard.controller;

import org.angel.taskboard.entity.Session;
import org.angel.taskboard.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Session>> getActiveSessionsByUser(@PathVariable Long userId) {
        List<Session> sessions = sessionService.getActiveSessionsByUser(userId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/{sessionId}/close")
    public ResponseEntity<String> closeSession(@PathVariable Long sessionId) {
        sessionService.closeSession(sessionId);
        return ResponseEntity.ok("Session closed successfully");
    }
}
