use invoice;

desc chains;
desc `group`;
ALTER TABLE chains
MODIFY company_name varchar(255) NOT NULL;
alter table chains add column gst_no varchar(255) not null unique after chainName;
alter table chains change chainName  company_name varchar(255) not null unique;
alter table chains add column is_active boolean not null;
alter table chains add column created_at timestamp default current_timestamp not null;
alter table chains add column update_at timestamp default current_timestamp on update current_timestamp not null;
-- Display chains
DELIMITER $$

CREATE PROCEDURE displaychains()
BEGIN
  SELECT * FROM chains;
END $$
DELIMITER ;


call displaychains();
-- ADD A CHAIN
DELIMITER $$

CREATE PROCEDURE addChain (
    IN p_company_name VARCHAR(255),
    IN p_gst_no VARCHAR(255),
    IN p_group_name VARCHAR(255),
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_group_id INT;

    -- Create a labeled block for control flow
    main: BEGIN

        -- Check if group exists
        SELECT group_id INTO v_group_id
        FROM `group`
        WHERE group_name = p_group_name;

        IF v_group_id IS NULL THEN
            SET p_message = 'Group does not exist';
            LEAVE main;
        END IF;

        -- Check for duplicate company name
        IF EXISTS (SELECT 1 FROM chains WHERE company_name = p_company_name) THEN
            SET p_message = 'Chain already exists';
            LEAVE main;
        END IF;

        -- Check for duplicate GST number
        IF EXISTS (SELECT 1 FROM chains WHERE gst_no = p_gst_no) THEN
            SET p_message = 'GST No already exists';
            LEAVE main;
        END IF;

        -- Insert new chain
        INSERT INTO chains (company_name, gst_no, is_active, created_at, update_at, group_id)
        VALUES (p_company_name, p_gst_no, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, v_group_id);

        SET p_message = 'Chain added successfully';
    END;

END$$

DELIMITER ;

CALL addChain('NeelInfo', '23AAAAA0001A1Z5', 'Persian Darbar', @msg);
SELECT @msg;
select * from chains;


desc chains;

-- Update Chain
DELIMITER $$

CREATE PROCEDURE `UpdateChain` (
    IN p_chain_id INT,
    IN p_company_name VARCHAR(255),
    IN p_gst_no VARCHAR(255),
    IN p_group_name VARCHAR(255),
    OUT p_msg VARCHAR(255)
)
BEGIN
    DECLARE v_group_id INT;
    DECLARE v_count INT;
    DECLARE v_chain_exists INT;

    -- Check if the chain_id exists
    SELECT COUNT(*) INTO v_chain_exists
    FROM `Chains`
    WHERE `chain_id` = p_chain_id;

    IF v_chain_exists = 0 THEN
        SET p_msg = 'Chain ID does not exist';
    ELSE
        -- Check if the group exists
        SELECT group_id INTO v_group_id
        FROM `Group`
        WHERE `group_name` = p_group_name;

        -- If group doesn't exist, set message
        IF v_group_id IS NULL THEN
            SET p_msg = 'Group doesn''t exist';
        ELSE
            -- Check if company name or GST number already exists (excluding the current chain_id)
            SELECT COUNT(*) INTO v_count
            FROM `Chains`
            WHERE (`company_name` = p_company_name OR `gst_no` = p_gst_no) AND `chain_id` != p_chain_id;

            -- If company name or GST number exists, set message
            IF v_count > 0 THEN
                SET p_msg = 'Company name or GST number already exists';
            ELSE
                -- Update the chain record using the chain_id
                UPDATE `Chains`
                SET `company_name` = p_company_name,
                    `gst_no` = p_gst_no,
                    `group_id` = v_group_id,
                    `update_at` = CURRENT_TIMESTAMP
                WHERE `chain_id` = p_chain_id;

                -- Set success message
                SET p_msg = 'Record updated successfully';
            END IF;
        END IF;
    END IF;
END $$

DELIMITER ;




SET @msg = '';
CALL UpdateChain(1, 'Quint it', 'GST123456789', 'Persian Darbar', @msg);
SELECT @msg;
select * from chains;
select * from `group`;

-- Delete Chain

CREATE table Brand (
  brand_id bigint PRIMARY KEY AUTO_INCREMENT,
  brand_name VARCHAR(100) unique not null ,
  chain_id bigint,
  FOREIGN KEY (chain_id) REFERENCES `chains`(chain_id)
);

DELIMITER //

CREATE PROCEDURE DeleteChain(
  IN cid INT,
  OUT result_message VARCHAR(255)
)
BEGIN
  DECLARE chain_exists INT;
  DECLARE brand_count INT;

  -- Check if the chain ID exists
  SELECT COUNT(*) INTO chain_exists
  FROM chains
  WHERE chain_id = cid;

  IF chain_exists = 0 THEN
    SET result_message = 'Chain ID does not exist.';
  ELSE
    -- Count how many brands are linked to this chain
    SELECT COUNT(*) INTO brand_count
    FROM brand
    WHERE chain_id = cid;

    IF brand_count = 0 THEN
      DELETE FROM chains WHERE chain_id = cid;
      SET result_message = 'Chain deleted successfully.';
    ELSE
      SET result_message = 'Chain cannot be deleted as it is linked to a Brand.';
    END IF;
  END IF;
END //

DELIMITER ;


set @msg="";
call deletechain(4,@msg);
select @msg;
select * from chains;
use invoice