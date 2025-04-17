package com.example.service;

import java.sql.CallableStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.BrandDTO;
import com.example.dto.UpdateChainDTO;
import com.example.entity.Brand;
import com.example.entity.ChainEntity;
import com.example.repository.BrandRepository;
import com.example.repository.ChainRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class BrandService {
	private final BrandRepository brandReopsitory;
	
	 public BrandService(BrandRepository brandReopsitory) {
	        this.brandReopsitory = brandReopsitory;
	    }
	
	public List<Brand> fetchChains() {
        return brandReopsitory.getAllBrands();
    }
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

	    public String addBrand(String brandName,  String companyName) {
	        // Creating the CallableStatementCreator for the stored procedure
	        CallableStatementCreator statementCreator = connection -> {
	            CallableStatement statement = connection.prepareCall("{call addBrand(?, ?, ?)}");
	            statement.setString(1,brandName);
	            statement.setString(2, companyName);
	            statement.registerOutParameter(	3, java.sql.Types.VARCHAR); 
	            return statement;
	        };

	        // CallableStatementCallback to execute the statement and get the OUT parameter
	        CallableStatementCallback<String> statementCallback = statement -> {
	            statement.execute();
	            return statement.getString(3); 
	        };

	        // Execute the stored procedure and return the result (message)
	        return jdbcTemplate.execute(statementCreator, statementCallback);
	    }
	    
	    public String updateBrand(BrandDTO request) {
	        return brandReopsitory.updateBrand(
	            request.getBrand_id(),
	            request.getBrand_name(),
	            request.getChain()
	        );
	    }
	    
	    @Autowired
        private EntityManager entityManager;

        public String deleteBrand(Integer brandId) {
            // Create a stored procedure call using the EntityManager
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Deletebrand");
            query.registerStoredProcedureParameter("bid", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("result_message", String.class, ParameterMode.OUT);
            query.setParameter("bid",  brandId);
            
            query.execute();
            
            return (String) query.getOutputParameterValue("result_message");
        }

}
