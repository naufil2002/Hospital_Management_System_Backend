package com.example.demo.controllers;

import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean isValid = adminService.login(username, password);
        return isValid ? "success" : "fail";
    }
}
