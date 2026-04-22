package com.kj.interviewround.Services;

import com.kj.interviewround.Entity.Admin;
import com.kj.interviewround.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin registerAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public boolean adminExists(String email) {
        return adminRepository.findAll().stream()
                .anyMatch(admin -> admin.getEmail().equals(email));
    }
}
