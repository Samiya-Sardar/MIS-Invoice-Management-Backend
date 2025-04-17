package com.example.service;


import com.example.dto.ChainDTO;
import com.example.dto.UpdateChainDTO;
import com.example.entity.ChainEntity;
import com.example.repository.ChainRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChainService {

	 private final ChainRepository chainsRepository;

	    public ChainService(ChainRepository chainsRepository) {
	        this.chainsRepository = chainsRepository;
	    }

	    public List<ChainEntity> fetchChains() {
	        return chainsRepository.getAllChains();
	    }
	    
	    @Autowired
	    private JdbcTemplate jdbcTemplate;

	    public String addChain(String companyName, String gstNo, String groupName) {
	        // Creating the CallableStatementCreator for the stored procedure
	        CallableStatementCreator statementCreator = connection -> {
	            CallableStatement statement = connection.prepareCall("{call addChain(?, ?, ?, ?)}");
	            statement.setString(1, companyName);
	            statement.setString(2, gstNo);
	            statement.setString(3, groupName);
	            statement.registerOutParameter(4, java.sql.Types.VARCHAR); // Register OUT parameter
	            return statement;
	        };

	        // CallableStatementCallback to execute the statement and get the OUT parameter
	        CallableStatementCallback<String> statementCallback = statement -> {
	            statement.execute();
	            return statement.getString(4);  // Retrieve the OUT parameter
	        };

	        // Execute the stored procedure and return the result (message)
	        return jdbcTemplate.execute(statementCreator, statementCallback);
	    }
	    public String updateChain(UpdateChainDTO request) {
	        return chainsRepository.updateChain(
	            request.getChainId(),
	            request.getCompanyName(),
	            request.getGstNo(),
	            request.getGroupName()
	        );
	    }
	    
	    @Autowired
        private EntityManager entityManager;

        public String deleteChain(Integer chainId) {
            // Create a stored procedure call using the EntityManager
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Deletechain");
            query.registerStoredProcedureParameter("cid", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("result_message", String.class, ParameterMode.OUT);
            query.setParameter("cid", chainId);
            
            query.execute();
            
            return (String) query.getOutputParameterValue("result_message");
        }
        
}
