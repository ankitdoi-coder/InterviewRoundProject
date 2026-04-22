package com.kj.interviewround.Services;

import com.kj.interviewround.Entity.Admin;
import com.kj.interviewround.Repository.AdminRepository;
import com.kj.interviewround.DTO.LoginRequest;
import com.kj.interviewround.DTO.LoginResponse;
import com.kj.interviewround.Exception.ResourceNotFoundException;
import com.kj.interviewround.JWT.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired 
    private PasswordEncoder passwordEncoder;


    public LoginResponse login(LoginRequest loginRequest) {

        Admin admin = adminRepository.findAll().stream()
                .filter(a -> a.getEmail().equals(loginRequest.getEmail()))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException("Admin not found with email: " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (!admin.isActive()) {
            throw new IllegalArgumentException("Admin account is not active");
        }

        String token = jwtTokenProvider.generateToken(admin.getId(), admin.getEmail(), admin.getRole());

        return new LoginResponse(token, "Bearer", admin.getId(), admin.getEmail(), admin.getName(), admin.getRole());
    }
}
