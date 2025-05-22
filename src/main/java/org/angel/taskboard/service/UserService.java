package org.angel.taskboard.service;

import org.angel.taskboard.entity.User;
import org.angel.taskboard.repository.TaskRepository;
import org.angel.taskboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.angel.taskboard.enums.RoleEnums;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TaskRepository taskRepository;


    public User registerUser(User user){

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getRole() == null){
            user.setRole(RoleEnums.USER);
        }
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);


    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deactivateUser(Long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setActive(false);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User updateUserRoleOrStatus(Long userId, RoleEnums newRole, Boolean activeStatus) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            if (newRole != null) {
                user.setRole(newRole);
            }
            if (activeStatus != null) {
                user.setActive(activeStatus);
            }
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public Optional<User> findByUsername(String usernameOrEmail) {

        return userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail));
    }


    public User blockUser(Long userId, boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(active);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        taskRepository.deleteByUser(user);

        userRepository.delete(user);
    }

}
