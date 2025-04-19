package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.example.dto.EstimateDTO;
import com.example.entity.ChainEntity;
import com.example.entity.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
	@Query(value = "CALL DisplayEstimates()", nativeQuery = true)
    List<Estimate> getAllEstimates();

	
	 @Procedure(procedureName = "GetEstimateDetailsByEstimateID")
	    EstimateDTO callGetEstimateDetailsByEstimateID(@Param("p_estimate_id") Long estimateId,
	                                                          @Param("p_message") String message);
}
