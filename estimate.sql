use invoice;

create table estimate(
						estimate_id bigint primary key not null auto_increment,
                        chain_id bigint not null,
                        group_name varchar(255)  not null,
                        brand_name varchar(255)  not null,
                        zone_name varchar(255)  not null,
                        service varchar(255) not null,
                        quantity int not null,
                        cost_per_unit float not null,
                        total_cost float not null,
                        delivery_date date not null,
                        delivery_details text,
						created_at timestamp default current_timestamp not null,
						update_at timestamp default current_timestamp on update current_timestamp not null,
                        foreign key(chain_id) references chains(chain_id)
                        );
                        
-- Display Estimates
delimiter //
create procedure DisplayEstimates()
begin
select * from estimate;
end //

call DisplayEstimates();

-- Add Estimates

DELIMITER $$

CREATE PROCEDURE Addestimate(
    IN p_group_name VARCHAR(255),
    IN p_chain_id BIGINT,
    IN p_brand_name VARCHAR(255),
    IN p_zone_name VARCHAR(255),
    IN p_service VARCHAR(255),
    IN p_quantity INT,
    IN p_cost_per_unit FLOAT,
    IN p_delivery_date DATE,
    IN p_delivery_details TEXT,
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_chain_exists INT;

    -- Step 1: Validate the Chain ID
    SELECT COUNT(*) INTO v_chain_exists
    FROM chains
    WHERE chain_id = p_chain_id;

    IF v_chain_exists = 0 THEN
        -- Chain does not exist
        SET p_message = 'Chain ID does not exist';
    ELSE
        -- Step 2, 3 & 4: Insert into estimate table with calculated total cost
        INSERT INTO estimate (
            chain_id,
            group_name,
            brand_name,
            zone_name,
            service,
            quantity,
            cost_per_unit,
            total_cost,
            delivery_date,
            delivery_details
        ) VALUES (
            p_chain_id,
            p_group_name,
            p_brand_name,
            p_zone_name,
            p_service,
            p_quantity,
            p_cost_per_unit,
            p_quantity * p_cost_per_unit,
            p_delivery_date,
            p_delivery_details
        );

        SET p_message = 'Successfully Added';
    END IF;
END$$

DELIMITER ;


CALL Addestimate(
    'Group X',
    1,
    'Brand Alpha',
    'Zone South',
    'Monthly Cleaning Service',
    20,
    150.00,
    '2025-05-05',
    'Deliver all equipment by the morning of 5th May.',
    @msg
);

SELECT @msg;



-- Update Estimates
DELIMITER $$

CREATE PROCEDURE UpdateEstimate(
    IN p_estimate_id BIGINT,
    IN p_chain_id BIGINT,
    IN p_group_name VARCHAR(255),
    IN p_brand_name VARCHAR(255),
    IN p_zone_name VARCHAR(255),
    IN p_service VARCHAR(255),
    IN p_quantity INT,
    IN p_cost_per_unit FLOAT,
    IN p_delivery_date DATE,
    IN p_delivery_details TEXT,
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_estimate_exists INT;
    DECLARE v_chain_exists INT;

    -- Check if the estimate ID exists
    SELECT COUNT(*) INTO v_estimate_exists
    FROM estimate
    WHERE estimate_id = p_estimate_id;

    IF v_estimate_exists = 0 THEN
        SET p_message = 'Estimate ID does not exist';
    ELSE
        -- Check if the chain ID exists
        SELECT COUNT(*) INTO v_chain_exists
        FROM chains
        WHERE chain_id = p_chain_id;

        IF v_chain_exists = 0 THEN
            SET p_message = 'Chain ID does not exist';
        ELSE
            -- Perform the update
            UPDATE estimate
            SET
                chain_id = p_chain_id,
                group_name = p_group_name,
                brand_name = p_brand_name,
                zone_name = p_zone_name,
                service = p_service,
                quantity = p_quantity,
                cost_per_unit = p_cost_per_unit,
                total_cost = p_quantity * p_cost_per_unit,
                delivery_date = p_delivery_date,
                delivery_details = p_delivery_details
            WHERE estimate_id = p_estimate_id;

            SET p_message = 'Record updated successfully';
        END IF;
    END IF;
END$$

DELIMITER ;

CALL UpdateEstimate(
    1, -- p_estimate_id
    1, -- p_chain_id
    'Group Z',
    'Brand B',
    'Zone East',
    'Pest Control',
    15,
    120.50,
    '2025-05-10',
    'Service must be done before 10 AM',
    @msg
);

SELECT @msg;


-- Delete Estimates

DELIMITER $$

CREATE PROCEDURE DeleteEstimate(
    IN p_estimate_id BIGINT,
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_exists INT;

    -- Check if the estimate ID exists
    SELECT COUNT(*) INTO v_exists
    FROM estimate
    WHERE estimate_id = p_estimate_id;

    IF v_exists = 0 THEN
        SET p_message = 'Estimate ID does not exist';
    ELSE
        DELETE FROM estimate
        WHERE estimate_id = p_estimate_id;

        SET p_message = 'Record deleted successfully';
    END IF;
END$$

DELIMITER ;

CALL DeleteEstimate(22, @msg);
SELECT @msg;

select * from estimate

select * from subzones;
use invoice;

