package org.angel.taskboard.service;

import org.angel.taskboard.dto.TaskDTO;
import org.angel.taskboard.entity.Task;
import org.angel.taskboard.entity.User;
import org.angel.taskboard.enums.StatusEnums;
import org.angel.taskboard.repository.TaskRepository;
import org.angel.taskboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(Long userId, Task task){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        task.setDateInserted(LocalDateTime.now());
        task.setStatus(StatusEnums.TO_DO);

        return taskRepository.save(task);
    }

    // Devuelve lista de DTOs para no exponer la entidad completa
    public List<TaskDTO> getTasksByUserIdDto(Long userId){
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByUserIdOrderedDto(Long userId) {
        List<Task> tasks = getTasksByUserIdOrdered(userId);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Task getTaskByIdAndUserId(Long taskId, Long userId) {
        return taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found or not authorized"));
    }



    // Conversión Entity -> DTO
    public TaskDTO convertToDto(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setDateInserted(task.getDateInserted());
        dto.setDateEnd(task.getDateEnd());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setUrgency(task.getUrgency());
        return dto;
    }

    public Task updateTaskStatus(Long taskId, StatusEnums newStatus){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDateEnd(updatedTask.getDateEnd());
        task.setStatus(updatedTask.getStatus());
        task.setUrgency(updatedTask.getUrgency());

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId){
        if(!taskRepository.existsById(taskId)){
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(taskId);
    }

    public List<Task> getTasksByUserIdOrdered(Long userId){
        List<Task> tasks = taskRepository.findByUserId(userId);
        tasks.sort((t1, t2) -> {
            LocalDateTime now = LocalDateTime.now();

            boolean t1Urgent = t1.getStatus() != StatusEnums.COMPLETED && t1.getDateEnd().isBefore(now);
            boolean t2Urgent = t2.getStatus() != StatusEnums.COMPLETED && t2.getDateEnd().isBefore(now);

            if(t1Urgent && !t2Urgent) return -1;
            if(!t1Urgent && t2Urgent) return 1;

            return t1.getDateEnd().compareTo(t2.getDateEnd());
        });

        return tasks;
    }

    public List<Task> getTasksByUserId(Long id) {
        return taskRepository.findByUserId(id);
    }

}
