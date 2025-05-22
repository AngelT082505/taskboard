package org.angel.taskboard.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.angel.taskboard.dto.LoginRequestDTO;
import org.angel.taskboard.dto.LoginResponseDTO;
import org.angel.taskboard.dto.RegisterRequestDTO;
import org.angel.taskboard.entity.Session;
import org.angel.taskboard.entity.User;
import org.angel.taskboard.service.SessionService;
import org.angel.taskboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SessionService  sessionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userService.findByUsername(loginRequest.getUsernameOrEmail())
                    .orElseGet(() -> userService.findByEmail(loginRequest.getUsernameOrEmail())
                            .orElseThrow(() -> new UsernameNotFoundException("User not found")));

            String ipAddress = request.getRemoteAddr(); // obtiene IP cliente

            Session session = sessionService.createSession(user, ipAddress);

            LoginResponseDTO response = new LoginResponseDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getRole().name(),
                    session.getToken(),
                    session.getId(),
                    session.getDate()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());

            User savedUser = userService.registerUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
