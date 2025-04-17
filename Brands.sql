use invoice;
desc brand;
alter table brand add column is_active boolean not null;
alter table brand add column created_at timestamp default current_timestamp not null;
alter table brand add column update_at timestamp default current_timestamp on update current_timestamp not null;
alter table brand change brandName brand_name varchar(255) not null unique;
show tables;
drop table brand;
drop table chain_entity;
-- Display Brands
delimiter //
create procedure DisplayBrands()
begin
select * from brand;
end //
drop table brand;
call DisplayBrands();
alter table brand drop column brand_name;

-- Add brands
DELIMITER $$

CREATE PROCEDURE addBrand (
    IN p_brand_name VARCHAR(255),
    IN p_company_name VARCHAR(255),
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_chain_id INT;

    main: BEGIN

        SELECT chain_id INTO v_chain_id
        FROM `chains`
        WHERE company_name = p_company_name;

        IF v_chain_id IS NULL THEN
            SET p_message = 'Company does not exist';
            LEAVE main;
        END IF;

        
        IF EXISTS (SELECT 1 FROM brand WHERE brand_name = p_brand_name) THEN
            SET p_message = 'Brand already exists';
            LEAVE main;
        END IF;


        INSERT INTO brand (brand_name, is_active, created_at, update_at, chain_id)
        VALUES (p_brand_name,  1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, v_chain_id);

        SET p_message = 'Brand added successfully';
    END;

END$$

DELIMITER ;
call addBrand('Panaya','Delta Tech',@msg);
set @msg="";
select @msg;
drop procedure addbrand;
select * from chains;
SELECT * FROM chain_entity WHERE company_name = 'Delta Tech';
show tables;
select * from brand;
drop table chain_entity;
-- Update Brands

DELIMITER $$

CREATE PROCEDURE `UpdateBrand` (
    IN p_brand_id INT,
    IN p_brand_name VARCHAR(255),
    IN p_company_name VARCHAR(255),
    OUT p_msg VARCHAR(255)
)
BEGIN
    DECLARE v_chain_id INT;
    DECLARE v_count INT;
    DECLARE v_brand_exists INT;

    
    SELECT COUNT(*) INTO v_brand_exists
    FROM `brand`
    WHERE `brand_id` = p_brand_id;

    IF v_brand_exists = 0 THEN
        SET p_msg = 'Brand ID does not exist';
    ELSE
       
        SELECT chain_id INTO v_chain_id
        FROM `chains`
        WHERE `company_name` = p_company_name;

        
        IF v_chain_id IS NULL THEN
            SET p_msg = 'Company doesn''t exist';
        ELSE
            
            SELECT COUNT(*) INTO v_count
            FROM `brand`
            WHERE (`brand_name` = p_company_name ) AND `brand_id` != p_brand_id;

           
                UPDATE `brand`
                SET `brand_name` = p_brand_name,
                   
                    `chain_id` = v_chain_id,
                    `update_at` = CURRENT_TIMESTAMP
                WHERE `brand_id` = p_brand_id;

                -- Set success message
                SET p_msg = 'Record updated successfully';
            END IF;
        END IF;
    
END $$

DELIMITER ;
CALL Updatebrand(1, 'Panayas', 'Delta Tech', @msg);
set @msg="";
select @msg;
select * from brand;

-- Delete Brands

CREATE table SubZones (
 zone_id bigint PRIMARY KEY AUTO_INCREMENT,
  zone_name VARCHAR(100) unique not null ,
  Brand_id bigint,
  FOREIGN KEY (Brand_id) REFERENCES `brand`(brand_id)
);

DELIMITER //

CREATE PROCEDURE DeleteBrand(
  IN bid INT,
  OUT result_message VARCHAR(255)
)
BEGIN
  DECLARE brand_exists INT;
  DECLARE zone_count INT;

  -- Check if the chain ID exists
  SELECT COUNT(*) INTO brand_exists
  FROM brand
  WHERE brand_id = bid;

  IF brand_exists = 0 THEN
    SET result_message = 'Brand ID does not exist.';
  ELSE
    
    SELECT COUNT(*) INTO zone_count
    FROM SubZones
    WHERE brand_id = bid;

    IF zone_count = 0 THEN
      DELETE FROM brand WHERE brand_id = bid;
      SET result_message = 'Brand deleted successfully.';
    ELSE
      SET result_message = 'Brand cannot be deleted as it is linked to a Sub Zone.';
    END IF;
  END IF;
END //

DELIMITER ;

call deletebrand(1,@msg);
set @msg="";
select @msg;
select * from brand;
