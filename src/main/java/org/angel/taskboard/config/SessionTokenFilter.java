package org.angel.taskboard.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.angel.taskboard.entity.Session;
import org.angel.taskboard.entity.User;
import org.angel.taskboard.service.SessionService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SessionTokenFilter extends OncePerRequestFilter {

    private final SessionService sessionService;

    public SessionTokenFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token != null && !token.isEmpty()) {
            Optional<Session> sessionOpt = sessionService.getSessionByToken(token);

            if (sessionOpt.isPresent() && sessionOpt.get().isActive()) {
                User user = sessionOpt.get().getUser();

                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                );

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            } else {
                // Token inválido o sesión inactiva: enviamos error 401 y cortamos filtro
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or inactive session token");
                response.getWriter().flush();
            }
        } else {

            filterChain.doFilter(request, response);
        }
    }

}


