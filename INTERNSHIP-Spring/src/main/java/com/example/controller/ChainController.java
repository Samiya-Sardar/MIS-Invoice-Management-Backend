package com.example.controller;

import com.example.dto.ChainDTO;
import com.example.dto.UpdateChainDTO;
import com.example.entity.ChainEntity;
import com.example.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/chains")
public class ChainController {

    @Autowired
    private ChainService chainService;

    private final ChainService chainsService;

    public ChainController(ChainService chainsService) {
        this.chainsService = chainsService;
    }

    @GetMapping("/all")
    public List<ChainEntity> getChains() {
        return chainsService.fetchChains();
    }
//    @PostMapping("/add")
//    public String addChain(@RequestParam String companyName, 
//                           @RequestParam String gstNo, 
//                           @RequestParam String groupName) {
//        return chainService.addChain(companyName, gstNo, groupName);
//    }
    @PostMapping("/add")
    public ResponseEntity<String> addChain(@RequestBody ChainDTO chainDTO) {
        String response = chainService.addChain(
            chainDTO.getCompany_name(),
            chainDTO.getGst_no(),
            chainDTO.getGroup()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    public String updateChain(@RequestBody UpdateChainDTO request) {
        return chainService.updateChain(request);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChain(@PathVariable Integer id) {
        String resultMessage = chainService.deleteChain(id);
        if (resultMessage.equals("Chain deleted successfully.")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
    }
}
