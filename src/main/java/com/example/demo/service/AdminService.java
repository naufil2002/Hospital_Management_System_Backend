package com.example.demo.service;

import com.example.demo.models.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    public boolean login(String username, String password) {
        Admin admin = adminRepo.findByUsername(username);

        if (admin == null) {
            System.out.println("❌ No admin found for username: " + username);
            return false;
        }

        System.out.println("✅ Admin found: " + admin.getUsername() + ", password in DB: " + admin.getPassword());

        return admin.getPassword().equals(password);
    }


}
