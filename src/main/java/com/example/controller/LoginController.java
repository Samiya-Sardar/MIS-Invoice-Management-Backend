package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.LoginDTO;
import com.example.dto.LoginOutDTO;
import com.example.service.LoginService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<LoginOutDTO> register(@RequestBody LoginDTO loginDTO) {
        LoginOutDTO response = loginService.registerUser(loginDTO);
        return ResponseEntity.ok(response);
    }

    // ✅ Updated endpoint to validate user login and return full user details
    // Example URL: http://localhost:8080/validate?email=abc@example.com&password=123
    @GetMapping("/validate")
    public ResponseEntity<LoginOutDTO> validateLogin(@RequestParam String email, @RequestParam String password) {
        LoginOutDTO response = loginService.validateLogin(email, password);

        if (response.getMessage() != null && response.getMessage().contains("Failure")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/fp")
    public Object forgotPassword(@RequestParam String fullName, @RequestParam String email) {
        return loginService.forgotPassword(fullName, email);
    }
}
