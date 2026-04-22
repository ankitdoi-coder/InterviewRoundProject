package com.kj.interviewround.Controller;

import com.kj.interviewround.DTO.LoginRequest;
import com.kj.interviewround.DTO.LoginResponse;
import com.kj.interviewround.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
