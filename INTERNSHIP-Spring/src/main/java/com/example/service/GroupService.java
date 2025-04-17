package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.example.dto.GroupDTO;
import com.example.entity.Group;
import com.example.repository.GroupRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Used for calling stored procedures

    // Fetch all groups using the stored procedure displaygroup
    public List<GroupDTO> getAllGroups() {
        // Call the displaygroup stored procedure
        return jdbcTemplate.query("CALL displaygroup()", (rs, rowNum) -> 
            new GroupDTO(
                rs.getLong("group_id"),
                rs.getString("group_name"),
                rs.getBoolean("is_active"),
                rs.getString("created_at"),
                rs.getString("update_at")
            )
        );
    }

    // Add a group using the stored procedure AddGroup
    public String addGroup(String groupName) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("AddGroup");
        
        // Pass the input parameter and execute the procedure
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource().addValue("p_group_name", groupName));
        
        // Return the message from the procedure output
        return (String) result.get("p_message");
    }
    
    @Autowired
    private GroupRepository groupRepository;

    public String updateGroup(int groupId, String newGroupName) {
        return groupRepository.updateGroup(groupId, newGroupName);
    }
    
   

        @Autowired
        private EntityManager entityManager;

        public String deleteGroup(Integer groupId) {
            // Create a stored procedure call using the EntityManager
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DeleteGroup");
            query.registerStoredProcedureParameter("gid", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("result_message", String.class, ParameterMode.OUT);
            query.setParameter("gid", groupId);
            
            query.execute();
            
            return (String) query.getOutputParameterValue("result_message");
        }
        
        

        // Get all group names from the database
        public List<String> getAllGroupNames() {
            List<Group> groups = groupRepository.findAll();
            return groups.stream().map(Group::getGroup_name).collect(Collectors.toList());
        }
    }



