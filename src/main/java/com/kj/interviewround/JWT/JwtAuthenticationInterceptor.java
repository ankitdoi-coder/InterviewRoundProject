package com.kj.interviewround.JWT;

import com.kj.interviewround.Exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Skip authentication for login and register endpoints
        String path = request.getRequestURI();
        if (path.contains("/api/auth/login") || path.contains("/api/admin/register")) {
            return true;
        }

        String bearerToken = extractTokenFromHeader(request.getHeader("Authorization"));
        
        if (bearerToken == null || bearerToken.isEmpty()) {
            throw new UnauthorizedException("Authorization header missing or invalid");
        }

        if (!jwtTokenProvider.validateToken(bearerToken)) {
            throw new UnauthorizedException("Invalid or expired token");
        }

        return true;
    }

    private String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
