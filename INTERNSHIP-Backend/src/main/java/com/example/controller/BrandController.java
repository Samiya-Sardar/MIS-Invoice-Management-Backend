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
import com.example.dto.ChainDTO;
import com.example.dto.UpdateChainDTO;
import com.example.entity.Brand;
import com.example.entity.ChainEntity;
import com.example.service.BrandService;
import com.example.service.ChainService;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/brands")
public class BrandController {
	private final BrandService brandsService;
	
	public BrandController(BrandService brandsService) {
        this.brandsService = brandsService;
    }

    @GetMapping("/all")
    public List<Brand> getChains() {
        return brandsService.fetchChains();
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addBrand(@RequestBody BrandDTO brandDTO) {
        String response =brandsService.addBrand(
            brandDTO.getBrand_name(),
            
            brandDTO.getChain()
        );
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/update")
    public String updateBrand(@RequestBody BrandDTO request) {
        return brandsService.updateBrand(request);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Integer id) {
        String resultMessage = brandsService.deleteBrand(id);
        if (resultMessage.equals("Brand deleted successfully.")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
    }

}
