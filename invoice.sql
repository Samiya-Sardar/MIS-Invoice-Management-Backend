use invoice;

create table invoice(
						invoice_no bigint primary key  not null,
                        estimate_id bigint not null,
                        chain_id bigint not null,
                        service_details varchar(255),
                        quantity int not null,
                        cost_per_unit float not null,
                        amount_payable float not null,
                        balance float not null,
                        date_of_payment datetime not null,
                        date_of_service date not null,
                        delivery_details text not null,
                        email_id varchar(255) not null,
                        foreign key(estimate_id) references estimate(estimate_id),
                        foreign key(chain_id) references chains(chain_id)
                        );
                        
-- Display Invoice
delimiter //
create procedure displayinvoice()
begin
select * from invoice;
end //

call displayinvoice();

-- Get data by estimate Id from estimates Table
DELIMITER $$

CREATE PROCEDURE GetEstimateDetailsByEstimateID (
    IN p_estimate_id BIGINT
)
BEGIN
    -- Declare a variable to store the count of records
    DECLARE v_count INT;

    -- Check if the estimate_id exists in the estimate table
    SELECT COUNT(*) INTO v_count
    FROM estimate
    WHERE estimate_id = p_estimate_id;

    -- If no matching record, return an empty result
    IF v_count = 0 THEN
        SELECT NULL AS estimate_id; -- Returning a NULL result if no record is found
    ELSE
        -- Otherwise, retrieve and display the data for the given estimate_id
        SELECT 
            estimate_id,
            chain_id,
            group_name,
            brand_name,
            zone_name,
            service,
            quantity,
            cost_per_unit,
            total_cost,
            delivery_date,
            delivery_details,
            created_at,
            update_at
        FROM estimate
        WHERE estimate_id = p_estimate_id;
    END IF;
END $$

DELIMITER ;

call GetEstimateDetailsByEstimateID(4);


CALL GetEstimateDetailsByEstimateID(1, @message);
select @message;

-- Add Invoice
DELIMITER $$

CREATE PROCEDURE addInvoice(
    IN p_invoice_no BIGINT,
    IN p_estimate_id BIGINT,
    IN p_chain_id BIGINT,
    IN p_service_details VARCHAR(255),
    IN p_quantity INT,
    IN p_cost_per_unit FLOAT,
    IN p_amount_payable FLOAT,
    IN p_balance FLOAT,
    IN p_date_of_payment DATETIME,
    IN p_date_of_service DATE,
    IN p_delivery_details TEXT,
    IN p_email_id VARCHAR(255)
)
BEGIN
    INSERT INTO invoice (
        invoice_no,
        estimate_id,
        chain_id,
        service_details,
        quantity,
        cost_per_unit,
        amount_payable,
        balance,
        date_of_payment,
        date_of_service,
        delivery_details,
        email_id
    ) VALUES (
        p_invoice_no,
        p_estimate_id,
        p_chain_id,
        p_service_details,
        p_quantity,
        p_cost_per_unit,
        p_amount_payable,
        p_balance,
        p_date_of_payment,
        p_date_of_service,
        p_delivery_details,
        p_email_id
    );
END $$

DELIMITER ;

-- Search by Invoice Number

DELIMITER $$

CREATE PROCEDURE SearchInvoiceByNumber(IN p_invoice_no BIGINT)
BEGIN
    SELECT 
        i.invoice_no,
        i.estimate_id,
        i.chain_id,
        i.service_details,
        i.quantity,
        i.cost_per_unit,
        i.amount_payable,
        i.balance,
        i.date_of_payment,
        i.date_of_service,
        i.delivery_details,
        i.email_id
    FROM 
        invoice i
    WHERE 
        i.invoice_no = p_invoice_no;
END$$

DELIMITER ;

call SearchInvoiceByNumber(2181);

select * from invoice

-- Update Invoice

DELIMITER $$

CREATE PROCEDURE updateInvoiceEmail(
    IN p_invoice_no BIGINT,
    IN p_email_id VARCHAR(255),
    OUT p_message VARCHAR(50)
)
BEGIN
    DECLARE invoice_count INT;

    -- Check if the invoice exists
    SELECT COUNT(*) INTO invoice_count
    FROM invoice
    WHERE invoice_no = p_invoice_no;

    IF invoice_count = 1 THEN
        -- Update email if invoice exists
        UPDATE invoice
        SET email_id = p_email_id
        WHERE invoice_no = p_invoice_no;

        SET p_message = 'Successfully updated';
    ELSE
        SET p_message = 'Invalid invoice number';
    END IF;
END$$

DELIMITER ;

CALL updateInvoiceEmail(2181, 'newemail@example.com', @msg);

SELECT @msg;

select * from invoice;

-- Delete Invoice

DELIMITER $$

CREATE PROCEDURE DeleteInvoice(IN p_invoice_no BIGINT, OUT p_message VARCHAR(255))
BEGIN
    DECLARE v_count INT;
    
    -- Check if the invoice exists
    SELECT COUNT(*) INTO v_count
    FROM invoice
    WHERE invoice_no = p_invoice_no;
    
    -- If the invoice exists, delete it and return success message
    IF v_count > 0 THEN
        DELETE FROM invoice
        WHERE invoice_no = p_invoice_no;
        SET p_message = 'Successfully deleted';
    ELSE
        SET p_message = 'Invalid Invoice number';
    END IF;
END$$

call DeleteInvoice(1,@msg);
select * from invoice;

DELIMITER ;

                        
                        