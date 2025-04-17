package com.example.controller;

import com.example.entity.Login;
import com.example.repository.EmailRepository;
import com.example.repository.LoginRepository;
import com.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRepository emailrepository;

    @GetMapping("/sendmail")
    public ResponseEntity<?> forgotPassword(@RequestParam String fullName, @RequestParam String email) {
        Optional<Login> userOpt = emailrepository.findByFullnameAndEmail(fullName, email);

        if (userOpt.isPresent()) {
            Login user = userOpt.get();

            emailService.sendPasswordEmail(user.getEmail(), user.getFullname(), user.getUpassword());

            Map<String, String> result = new HashMap<>();
            result.put("fullName", user.getFullname());
            result.put("email", user.getEmail());
            result.put("password", user.getUpassword());
            result.put("role", user.getRole().getRolecode()); // Assuming getRolecode() exists
            result.put("curl", "dashboard/home");

            return ResponseEntity.ok(result);
        } else {
            Map<String, String> err = new HashMap<>();
            err.put("message", "User not found or email incorrect");
            return ResponseEntity.ok(Collections.singletonList(err));
        }
    }
}