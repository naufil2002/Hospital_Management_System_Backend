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
        Admin admin = adminRepo.findByUsernameAndPassword(username, password);
        return admin != null;
    }
}
