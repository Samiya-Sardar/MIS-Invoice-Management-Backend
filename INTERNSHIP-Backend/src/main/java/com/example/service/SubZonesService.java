package com.example.service;

import java.sql.CallableStatement;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.BrandDTO;
import com.example.dto.ChainDTO;
import com.example.dto.SubZonesDTO;
import com.example.entity.Brand;
import com.example.entity.ChainEntity;
import com.example.entity.SubZones;
import com.example.repository.BrandRepository;
import com.example.repository.SubZonesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class SubZonesService {
	private final SubZonesRepository subzonesReopsitory;
	
	 public SubZonesService(SubZonesRepository subzonesReopsitory) {
	        this.subzonesReopsitory = subzonesReopsitory;
	    }
	
	public List<SubZones> fetchZones() {
       return subzonesReopsitory.getAllZones();
   }
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public String addZone(String zoneName,  String brandName) {
        
        CallableStatementCreator statementCreator = connection -> {
            CallableStatement statement = connection.prepareCall("{call addZone(?, ?, ?)}");
            statement.setString(1,zoneName);
            statement.setString(2, brandName);
            statement.registerOutParameter(	3, java.sql.Types.VARCHAR); 
            return statement;
        };

        
        CallableStatementCallback<String> statementCallback = statement -> {
            statement.execute();
            return statement.getString(3); 
        };

        
        return jdbcTemplate.execute(statementCreator, statementCallback);
    }
    
    public String updateZone(SubZonesDTO request) {
        return subzonesReopsitory.updateZone(
            request.getZone_id(),
            request.getZone_name(),
            request.getBrand()
        );
    }
    
    @Autowired
    private EntityManager entityManager;

    public String deleteZone(Integer zoneId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Deletezone");
        query.registerStoredProcedureParameter("zid", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("result_message", String.class, ParameterMode.OUT);
        query.setParameter("zid",  zoneId);
        
        query.execute();
        
        return (String) query.getOutputParameterValue("result_message");
    }
    public List<String> fetchAllZoneNames() {
        List<SubZones> zones = subzonesReopsitory.getAllZones(); // ✅ Use the procedure call
        return zones.stream()
                    .map(SubZones::getZone_name)
                    .collect(Collectors.toList());
    }

    



}
