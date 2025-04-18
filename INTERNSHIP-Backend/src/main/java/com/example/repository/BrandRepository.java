package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.entity.Brand;
import com.example.entity.ChainEntity;

public interface BrandRepository extends JpaRepository<Brand, Long>{
	@Query(value = "CALL DisplayBrands()", nativeQuery = true)
    List<Brand> getAllBrands();
	
	@Procedure(procedureName = "Updatebrand")
    String updateBrand(
        @Param("p_brand_id") Long brandId,
        @Param("p_brand_name") String brandName,
        @Param("p_company_name") String chainName
    );

}
