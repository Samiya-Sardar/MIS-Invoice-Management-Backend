package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ResetPasswordDTO;
import com.example.repository.LoginRepository;
import com.example.repository.ResetPasswordRepository;

@Service
public class ResetPasswordService {

    @Autowired
    private ResetPasswordRepository resetPassword;

    public String resetPassword(ResetPasswordDTO dto) {
        return resetPassword.resetPassword(
            dto.getInput_email(),
            dto.getInput_full_name(),
            dto.getNew_password1(),
            dto.getNew_password2()
        );
    }
}
