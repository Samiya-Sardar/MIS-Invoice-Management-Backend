package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.ChainEntity;
import com.example.entity.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
	@Query(value = "CALL DisplayEstimates()", nativeQuery = true)
    List<Estimate> getAllEstimates();

}
