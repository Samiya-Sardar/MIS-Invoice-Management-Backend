use invoice;
drop table `group`;
CREATE TABLE `Group` (
    group_id INT NOT NULL AUTO_INCREMENT primary key,
    group_name VARCHAR(255) UNIQUE not null,
    is_active BOOLEAN not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null
    
);

-- Display groups

DELIMITER $$
create procedure displaygroup()
begin
select * from `group`;
end $$

call displaygroup();


-- ADD A GROUP 
DELIMITER $$

CREATE PROCEDURE AddGroup(IN p_group_name VARCHAR(255), OUT p_message VARCHAR(255))
BEGIN
    -- Check if the group already exists
    IF EXISTS (SELECT 1 FROM `Group` WHERE group_name = p_group_name) THEN
        -- If the group already exists, set the message
        SET p_message = 'Group already exists';
    ELSE
        -- If the group does not exist, insert the new group and set the success message
        INSERT INTO `Group` (group_name, is_active) 
        VALUES (p_group_name, TRUE);
        SET p_message = 'Group added successfully';
    END IF;
END $$

DELIMITER 

-- Declare a variable to hold the output message
SET @message = '';

-- Call the procedure
CALL AddGroup('Persian Darbar', @message);

-- Retrieve the output message
SELECT @message AS message;

select * from `group`;

-- Update Group 
DELIMITER $$

CREATE PROCEDURE `updategroup`(
    IN p_group_id INT,
    IN p_new_group_name VARCHAR(255)
)
BEGIN
    -- Declare a variable to hold the result message
    DECLARE result_message VARCHAR(255);

    -- Check if the group with the provided group_id exists
    IF NOT EXISTS (SELECT 1 FROM `Group` WHERE group_id = p_group_id) THEN
        SET result_message = 'Record does not exist';
    ELSE
        -- Check if the new group name already exists in the table (except for the current group)
        IF EXISTS (SELECT 1 FROM `Group` WHERE group_name = p_new_group_name AND group_id != p_group_id) THEN
            SET result_message = 'Group name already exists, update failed';
        ELSE
            -- Update the group record with the new group name
            UPDATE `Group`
            SET group_name = p_new_group_name, update_at = CURRENT_TIMESTAMP
            WHERE group_id = p_group_id;
            
            SET result_message = 'Record updated successfully';
        END IF;
    END IF;

    -- Return the result message
    SELECT result_message AS message;
END$$

DELIMITER ;



CALL updategroup(3, 'China Wall');

-- Chain Table

CREATE table chains (
  chain_id INT PRIMARY KEY AUTO_INCREMENT,
  chainName VARCHAR(100) unique not null ,
  group_id INT,
  FOREIGN KEY (group_id) REFERENCES `group`(group_id)
);

-- Delete Group
DELIMITER //

CREATE PROCEDURE DeleteGroup(
  IN gid INT,
  OUT result_message VARCHAR(255)
)
BEGIN
  DECLARE chain_count INT;

  -- Count how many chains are linked to the group
  SELECT COUNT(*) INTO chain_count
  FROM chains
  WHERE group_id = gid;

  IF chain_count = 0 THEN
    DELETE FROM `Group` WHERE group_id = gid;
    SET result_message = 'Group deleted successfully.';
  ELSE
    SET result_message = 'Group cannot be deleted as it is linked to a chain.';
  END IF;
END //

DELIMITER ;









