package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.GroupDTO;
import com.example.service.GroupService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService; // Inject service to handle business logic

    // Endpoint to get all groups (GET request)
    @GetMapping("/all")
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }

    // Endpoint to add a new group (POST request)
    @PostMapping("/addgroup")
    public ResponseEntity<String> addGroup(@RequestBody GroupDTO groupDTO) {
        String message = groupService.addGroup(groupDTO.getGroupName());
        return ResponseEntity.ok(message);
    }
    
    @PatchMapping("/update/{groupId}")
    public ResponseEntity<String> updateGroup(
            @PathVariable int groupId, 
            @RequestBody GroupDTO groupDto) {

        if (!groupDto.isValidForUpdate()) {
            return ResponseEntity.badRequest().body("New group name is required");
        }

        String resultMessage = groupService.updateGroup(groupId, groupDto.getNewGroupName());

        if (resultMessage.equals("Record updated successfully")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.badRequest().body(resultMessage);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Integer id) {
        String resultMessage = groupService.deleteGroup(id);
        if (resultMessage.equals("Group deleted successfully.")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
    }


}
