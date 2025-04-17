package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.ChainEntity;

@Repository
public interface ChainRepository extends CrudRepository<ChainEntity, Long> {

    @Query(value = "CALL displaychains()", nativeQuery = true)
    List<ChainEntity> getAllChains();
    
    @Procedure(procedureName = "UpdateChain")
    String updateChain(
        @Param("p_chain_id") Integer chainId,
        @Param("p_company_name") String companyName,
        @Param("p_gst_no") String gstNo,
        @Param("p_group_name") String groupName
    );
}

