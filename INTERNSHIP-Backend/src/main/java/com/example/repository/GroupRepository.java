package com.example.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query(value = "CALL updategroup(:groupId, :newGroupName)", nativeQuery = true)
    String updateGroup(@Param("groupId") int groupId, @Param("newGroupName") String newGroupName);
}
