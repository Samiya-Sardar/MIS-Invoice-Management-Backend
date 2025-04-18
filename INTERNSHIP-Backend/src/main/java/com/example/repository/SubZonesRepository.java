package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.example.entity.Brand;
import com.example.entity.SubZones;

public interface SubZonesRepository extends JpaRepository<SubZones, Long> {
	@Query(value = "CALL DisplayZones()", nativeQuery = true)
    List<SubZones> getAllZones();
	
	@Procedure(procedureName = "UpdateZone")
    String updateZone(
        @Param("p_zone_id") Long zoneId,
        @Param("p_zone_name") String zoneName,
        @Param("p_brand_name") String brandName
    );

}
