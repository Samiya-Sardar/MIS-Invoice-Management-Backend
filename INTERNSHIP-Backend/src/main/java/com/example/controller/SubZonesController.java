package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.example.dto.BrandDTO;
import com.example.dto.SubZonesDTO;
import com.example.entity.Brand;
import com.example.entity.SubZones;
import com.example.service.BrandService;
import com.example.service.SubZonesService;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/zones")
public class SubZonesController {
private final SubZonesService subzonesService;
	
	public SubZonesController(SubZonesService subzonesService) {
        this.subzonesService = subzonesService;
    }

    @GetMapping("/all")
    public List<SubZones> getZones() {
        return subzonesService.fetchZones();
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addZone(@RequestBody SubZonesDTO zoneDTO) {
        String response =subzonesService.addZone(
        		zoneDTO.getZone_name(),
            
        		zoneDTO.getBrand()
        );
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/update")
    public String updateZone(@RequestBody SubZonesDTO request) {
        return subzonesService.updateZone(request);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteZone(@PathVariable Integer id) {
        String resultMessage = subzonesService.deleteZone(id);
        if (resultMessage.equals("Zone deleted successfully.")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
    }

}
