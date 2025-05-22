package org.angel.taskboard.controller;


import org.angel.taskboard.entity.User;
import org.angel.taskboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }


    @PutMapping("/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id, @RequestParam boolean active) {
        userService.blockUser(id, active);
        return ResponseEntity.ok(active ? "User activated" : "User blocked");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User and tasks deleted");
    }

}
