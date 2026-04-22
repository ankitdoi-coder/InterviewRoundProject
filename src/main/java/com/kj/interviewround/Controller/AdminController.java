package com.kj.interviewround.Controller;

import com.kj.interviewround.Entity.Admin;
import com.kj.interviewround.Services.AdminService;
import com.kj.interviewround.Exception.ResourceNotFoundException;
import com.kj.interviewround.Exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {

        if (adminService.adminExists(admin.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + admin.getEmail());
        }

        if (admin.getRole() == null || admin.getRole().isEmpty()) {
            admin.setRole("ADMIN");
        }
        admin.setActive(true);

        Admin registeredAdmin = adminService.registerAdmin(admin);

        return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            throw new ResourceNotFoundException("Admin not found with id: " + id);
        }
    }
}
