package org.angel.taskboard.controller;

import org.angel.taskboard.dto.TaskDTO;
import org.angel.taskboard.entity.Task;
import org.angel.taskboard.enums.StatusEnums;
import org.angel.taskboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.angel.taskboard.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@AuthenticationPrincipal User user, @RequestBody Task task){
        Task createdTask = taskService.createTask(user.getId(), task);
        TaskDTO dto = taskService.convertToDto(createdTask); // Usar método público
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@AuthenticationPrincipal User user){
        List<TaskDTO> tasks = taskService.getTasksByUserIdDto(user.getId());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId, @AuthenticationPrincipal User user) {
        Task task = taskService.getTaskByIdAndUserId(taskId, user.getId());
        return ResponseEntity.ok(taskService.convertToDto(task));
    }


    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long taskId, @RequestParam StatusEnums status) {
        Task updatedTask = taskService.updateTaskStatus(taskId, status);
        TaskDTO dto = taskService.convertToDto(updatedTask);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(taskId, updatedTask);
        TaskDTO dto = taskService.convertToDto(task);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<TaskDTO>> getTasksByUserOrdered(@AuthenticationPrincipal User user){
        List<TaskDTO> dtos = taskService.getTasksByUserIdOrderedDto(user.getId());
        return ResponseEntity.ok(dtos);
    }
}


