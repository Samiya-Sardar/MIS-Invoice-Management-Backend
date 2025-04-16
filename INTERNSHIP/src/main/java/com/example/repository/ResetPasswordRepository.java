package com.example.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.entity.Login;

public interface ResetPasswordRepository extends CrudRepository<Login, Integer> {

    @Procedure(procedureName = "ResetPassword")
    String resetPassword(
        @Param("input_email") String email,
        @Param("input_full_name") String fullName,
        @Param("new_password1") String newPassword1,
        @Param("new_password2") String newPassword2
    );
}