package org.angel.taskboard.service;

import org.angel.taskboard.entity.Session;
import org.angel.taskboard.entity.User;
import org.angel.taskboard.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {


   @Autowired
   private SessionRepository sessionRepository;



   public Session createSession(User user, String ipAddress) {
      Session session = new Session();

      session.setUser(user);
      session.setDate(LocalDateTime.now());
      session.setDateLastAction(LocalDateTime.now());

      session.setToken(UUID.randomUUID().toString());
      session.setActive(true);
      session.setIp(ipAddress);

      return sessionRepository.save(session);
   }

  public void closeSession(Long sessionId) {
     Session session = sessionRepository.findById(sessionId)
             .orElseThrow(() -> new RuntimeException("Session not found"));

     session.setActive(false);
     session.setDateLastAction(LocalDateTime.now());
     sessionRepository.save(session);
  }

   public List<Session> getActiveSessionsByUser(Long userId) {
      return sessionRepository.findByUserIdAndActiveTrue(userId);
   }



   public Optional<Session> findByToken(String token) {
      return sessionRepository.findByToken(token);
   }


    public Optional<Session> getSessionByToken(String token) {
        return sessionRepository.findByToken(token);
    }

}
