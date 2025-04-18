use invoice;
alter table SubZones add column is_active boolean not null;
alter table SubZones add column created_at timestamp default current_timestamp not null;
alter table SubZones add column update_at timestamp default current_timestamp on update current_timestamp not null;
desc SubZones;

-- Display Zones
delimiter //
create procedure DisplayZones()
begin
select * from SubZones;
end //

call DisplayZones();

-- Add Zones

DELIMITER $$

CREATE PROCEDURE addZone (
    IN p_zone_name VARCHAR(255),
    IN p_brand_name VARCHAR(255),
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_brand_id INT;

    main: BEGIN

        SELECT brand_id INTO v_brand_id
        FROM `brand`
        WHERE brand_name = p_brand_name;

        IF v_brand_id IS NULL THEN
            SET p_message = 'Brand does not exist';
            LEAVE main;
        END IF;

        
        IF EXISTS (SELECT 1 FROM SubZones WHERE zone_name = p_zone_name) THEN
            SET p_message = 'Zone already exists';
            LEAVE main;
        END IF;


        INSERT INTO SubZones (zone_name, is_active, created_at, update_at, brand_id)
        VALUES (p_zone_name,  1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, v_brand_id);

        SET p_message = 'Zone added successfully';
    END;

END$$


call addZone('Kurla','Nike',@msg);
set @msg="";
select @msg;
select * from subzones;

-- Update Zone
DELIMITER $$

CREATE PROCEDURE `UpdateZone` (
    IN p_zone_id INT,
    IN p_zone_name VARCHAR(255),
    IN p_brand_name VARCHAR(255),
    OUT p_msg VARCHAR(255)
)
BEGIN
    DECLARE v_brand_id INT;
    DECLARE v_count INT;
    DECLARE v_zone_exists INT;

    -- Check if the zone exists
    SELECT COUNT(*) INTO v_zone_exists
    FROM `subzones`
    WHERE `zone_id` = p_zone_id;

    IF v_zone_exists = 0 THEN
        SET p_msg = 'Zone ID does not exist';
    ELSE
        -- Check if the brand exists
        SELECT brand_id INTO v_brand_id
        FROM `brand`
        WHERE `brand_name` = p_brand_name
        LIMIT 1;  -- Ensure we get one record if it exists

        IF v_brand_id IS NULL THEN
            SET p_msg = 'Brand doesn''t exist';
        ELSE
            -- Check for duplicate zone name
            SELECT COUNT(*) INTO v_count
            FROM `subzones`
            WHERE `zone_name` = p_zone_name AND `zone_id` != p_zone_id;

            IF v_count > 0 THEN
                SET p_msg = 'Duplicate zone name exists';
            ELSE
                -- Perform the update
                UPDATE `subzones`
                SET `zone_name` = p_zone_name,
                    `brand_id` = v_brand_id,
                    `update_at` = CURRENT_TIMESTAMP
                WHERE `zone_id` = p_zone_id;

                -- Set success message
                SET p_msg = 'Record updated successfully';
            END IF;
        END IF;
    END IF;

END $$

DELIMITER ;
drop procedure updatezone;
CALL UpdateZone(1, 'Khar', 'Nike', @msg);
select @msg;
select * from subzones;

-- Delete Zone
DELIMITER //

CREATE PROCEDURE DeleteZone(
  IN zid INT,
  OUT result_message VARCHAR(255)
)
BEGIN
  DECLARE zone_exists INT;
  DECLARE zone_count INT;

  -- Check if the chain ID exists
  SELECT COUNT(*) INTO zone_exists
  FROM subzones
  WHERE zone_id = zid;

  IF zone_exists = 0 THEN
    SET result_message = 'Zone ID does not exist.';
  ELSE
      DELETE FROM subzones WHERE zone_id = zid;
      SET result_message = 'Zone deleted successfully.';
  END IF;
END //

DELIMITER ;
call deleteZone(2,@msg);
select @msg;
select * from subzones;
