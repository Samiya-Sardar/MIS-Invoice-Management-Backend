package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.LoginDTO;
import com.example.dto.LoginOutDTO;
import com.example.repository.LoginRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Method to register a user (calls NewLoginDetails stored procedure)
    public LoginOutDTO registerUser(LoginDTO loginDTO) {
        return loginRepository.callNewLoginProcedure(loginDTO);
    }

    // Method to validate login (calls ValidateLogin stored procedure)
    public LoginOutDTO validateLogin(String email, String password) {
        return loginRepository.validateLoginProcedure(email, password);
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Object forgotPassword(String fullName, String email) {
        // Prepare SQL query to call stored procedure
        String sql = "CALL ForgotPassword(?, ?)";

        // Execute the stored procedure and return the result
        try {
            return jdbcTemplate.queryForList(sql, fullName, email);
        } catch (Exception e) {
            return "Error occurred while processing the request: " + e.getMessage();
        }
    }
    

   
}
