package com.example.controller;

import com.example.dto.ResetPasswordDTO;
import com.example.service.ResetPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pass")
@CrossOrigin(origins = "*")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO dto) {
    	System.out.println(dto.getInput_email());
        String result = resetPasswordService.resetPassword(dto);
        return ResponseEntity.ok(result);
    }
}
