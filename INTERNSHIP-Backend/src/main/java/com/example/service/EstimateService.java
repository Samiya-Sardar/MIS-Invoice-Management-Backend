package com.example.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.EstimateDTO;
import com.example.entity.Brand;
import com.example.entity.Estimate;
import com.example.repository.BrandRepository;
import com.example.repository.EstimateRepository;
import javax.sql.DataSource; 


@Service
public class EstimateService {
	private final EstimateRepository estimateReopsitory;
	
	 public EstimateService(EstimateRepository estimateReopsitory) {
	        this.estimateReopsitory = estimateReopsitory;
	    }
	
	public List<Estimate> fetchEstimate() {
       return estimateReopsitory.getAllEstimates();
   }
	@Autowired
    private DataSource dataSource;

	
	@Transactional
    public String addEstimate(EstimateDTO estimateDTO) {
        String message = "";
        
        try (Connection connection = dataSource.getConnection()) {
            
            String query = "{CALL Addestimate(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, estimateDTO.getGroupName());
            callableStatement.setLong(2, estimateDTO.getChainId());
            callableStatement.setString(3, estimateDTO.getBrandName());
            callableStatement.setString(4, estimateDTO.getZoneName());
            callableStatement.setString(5, estimateDTO.getService());
            callableStatement.setInt(6, estimateDTO.getQuantity());
            callableStatement.setFloat(7, estimateDTO.getCostPerUnit());
            callableStatement.setDate(8, java.sql.Date.valueOf(estimateDTO.getDeliveryDate()));
            callableStatement.setString(9, estimateDTO.getDeliveryDetails());

            
            callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);

           
            callableStatement.execute();

           
            message = callableStatement.getString(10);
        } catch (SQLException e) {
            e.printStackTrace();
            message = "An error occurred while adding the estimate.";
        }

        return message;
    }
	
	@Transactional
    public String updateEstimate(EstimateDTO estimateDTO) {
        String message = "";

        try (Connection connection = dataSource.getConnection()) {
           
            String query = "{CALL UpdateEstimate(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(query);

            
            callableStatement.setLong(1, estimateDTO.getEstimateId());
            callableStatement.setLong(2, estimateDTO.getChainId());
            callableStatement.setString(3, estimateDTO.getGroupName());
            callableStatement.setString(4, estimateDTO.getBrandName());
            callableStatement.setString(5, estimateDTO.getZoneName());
            callableStatement.setString(6, estimateDTO.getService());
            callableStatement.setInt(7, estimateDTO.getQuantity());
            callableStatement.setFloat(8, estimateDTO.getCostPerUnit());
            callableStatement.setDate(9, java.sql.Date.valueOf(estimateDTO.getDeliveryDate()));
            callableStatement.setString(10, estimateDTO.getDeliveryDetails());

           
            callableStatement.registerOutParameter(11, java.sql.Types.VARCHAR);

            
            callableStatement.execute();

            
            message = callableStatement.getString(11);
        } catch (SQLException e) {
            e.printStackTrace();
            message = "An error occurred while updating the estimate.";
        }

        return message;
    }
	
	@Transactional
    public String deleteEstimate(Long estimateId) {
        String message = "";

        try (Connection connection = dataSource.getConnection()) {
            
            String query = "{CALL DeleteEstimate(?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(query);

            
            callableStatement.setLong(1, estimateId);

            
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

            
            callableStatement.execute();

           
            message = callableStatement.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
            message = "An error occurred while deleting the estimate.";
        }

        return message;
    }

}
