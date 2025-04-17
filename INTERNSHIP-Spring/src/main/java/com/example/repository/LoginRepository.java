package com.example.repository;

import com.example.dto.LoginDTO;
import com.example.dto.LoginOutDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // ✅ Method to call ValidateLogin stored procedure
    public LoginOutDTO validateLoginProcedure(String email, String password) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ValidateLogin");

        // Register IN parameters
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_upassword", String.class, ParameterMode.IN);

        // Register OUT parameters
        query.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_name", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_email_out", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_curl", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_role", String.class, ParameterMode.OUT);

        // Set IN parameter values
        query.setParameter("p_email", email);
        query.setParameter("p_upassword", password);

        // Execute stored procedure
        query.execute();

        // Prepare and return the result
        LoginOutDTO response = new LoginOutDTO();
        response.setMessage((String) query.getOutputParameterValue("p_message"));
        response.setUfullname((String) query.getOutputParameterValue("p_name"));
        response.setUemail((String) query.getOutputParameterValue("p_email_out"));
        response.setUserid((Integer) query.getOutputParameterValue("p_id"));
        response.setCurl((String) query.getOutputParameterValue("p_curl"));
        response.setUrole((String) query.getOutputParameterValue("p_role"));

        return response;
    }

    // ✅ Existing method (unchanged)
    public LoginOutDTO callNewLoginProcedure(LoginDTO loginDTO) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("NewLoginDetails");

        // IN parameters
        query.registerStoredProcedureParameter("p_fullname", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_upassword", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_urole", String.class, ParameterMode.IN);

        // OUT parameters
        query.registerStoredProcedureParameter("p_userid", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_ufullname", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_uemail", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_upass", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_curl", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        // Set input
        query.setParameter("p_fullname", loginDTO.getFullname());
        query.setParameter("p_email", loginDTO.getEmail());
        query.setParameter("p_upassword", loginDTO.getUpassword());
        query.setParameter("p_urole", loginDTO.getUrole());

        // Execute
        query.execute();

        // Return response
        LoginOutDTO response = new LoginOutDTO();
        response.setUserid((Integer) query.getOutputParameterValue("p_userid"));
        response.setUfullname((String) query.getOutputParameterValue("p_ufullname"));
        response.setUemail((String) query.getOutputParameterValue("p_uemail"));
        response.setUpass((String) query.getOutputParameterValue("p_upass"));
        response.setCurl((String) query.getOutputParameterValue("p_curl"));
        response.setMessage((String) query.getOutputParameterValue("p_message"));

        return response;
    }
}
