package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EstimateDTO;
import com.example.entity.Brand;
import com.example.entity.Estimate;
import com.example.service.BrandService;
import com.example.service.EstimateService;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/estimate")
public class EstimateController {
	private final EstimateService estimateService;
	
	public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    @GetMapping("/all")
    public List<Estimate> getEstimate() {
        return estimateService.fetchEstimate();
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addEstimate(@RequestBody EstimateDTO estimateDTO) {
        String message = estimateService.addEstimate(estimateDTO);
        return ResponseEntity.ok(message);  
    }
    
    @PatchMapping("/update")
    public ResponseEntity<String> updateEstimate(@RequestBody EstimateDTO estimateDTO) {
        String message = estimateService.updateEstimate(estimateDTO);
        return ResponseEntity.ok(message);  
    }
    @DeleteMapping("/delete/{estimateId}")
    public ResponseEntity<String> deleteEstimate(@PathVariable Long estimateId) {
        String message = estimateService.deleteEstimate(estimateId);
        return ResponseEntity.ok(message);  // Respond with the message from the procedure
    }
    
    @GetMapping("/details/{estimateId}")
    public List<EstimateDTO> getEstimateDetails(@PathVariable Long estimateId) {
        return estimateService.getEstimateDetailsByEstimateId(estimateId);
    }
}