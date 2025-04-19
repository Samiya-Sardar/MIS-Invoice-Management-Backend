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


DELIMITER $$

CREATE PROCEDURE GetInvoiceDetailsByEstimateId(IN p_estimateId BIGINT)
BEGIN
  SELECT 
    i.invoice_no AS `Invoice No`,
    e.estimate_id AS `Estimate ID`,
    e.chain_id AS `Chain ID`,
    e.service AS `Service Provided`,
    e.quantity AS `Quantity`,
    e.cost_per_unit AS `Cost per Quantity`,
    i.amount_payable AS `Amount Payable in Rs`,
    i.paid_amount AS `Amount Paid in Rs`,
    i.balance AS `Balance in Rs`,
    e.delivery_date AS `Delivery Date`,
    e.delivery_details AS `Other Delivery Details`,
    i.email_id AS `Enter Email ID`
  FROM invoice i
  JOIN estimate e ON i.estimate_id = e.estimate_id
  WHERE e.estimate_id = p_estimateId;
END $$

DELIMITER ;



drop procedure   GetInvoiceDetailsByEstimateId(1);

-- Add Invoice
DELIMITER //

CREATE PROCEDURE AddInvoice(
    IN p_invoice_no INT,
    IN p_estimate_id BIGINT,
    IN p_email_id VARCHAR(255),
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_chain_id BIGINT;
    DECLARE v_service VARCHAR(255);
    DECLARE v_quantity INT;
    DECLARE v_cost_per_unit FLOAT;
    DECLARE v_total_cost FLOAT;
    DECLARE v_delivery_date DATE;
    DECLARE v_delivery_details TEXT;
    DECLARE invoice_exists INT;

    -- Use a labeled block so we can LEAVE properly
    main_block: BEGIN

        -- Check if the invoice number already exists
        SELECT COUNT(*) INTO invoice_exists FROM invoice WHERE invoice_no = p_invoice_no;
        IF invoice_exists > 0 THEN
            SET p_message = 'Invoice number already exists';
            LEAVE main_block;
        END IF;

        -- Try to fetch estimate data
        SELECT 
            chain_id, service, quantity, cost_per_unit, total_cost, delivery_date, delivery_details 
        INTO 
            v_chain_id, v_service, v_quantity, v_cost_per_unit, v_total_cost, v_delivery_date, v_delivery_details
        FROM 
            estimate
        WHERE 
            estimate_id = p_estimate_id;

        -- If no data found
        IF ROW_COUNT() = 0 THEN
            SET p_message = 'Incorrect estimate ID';
            LEAVE main_block;
        END IF;

        -- Insert invoice
        INSERT INTO invoice (
            invoice_no, estimate_id, chain_id, service_details, quantity, cost_per_unit, 
            amount_payable, balance, date_of_payment, date_of_service, delivery_details, email_id
        ) VALUES (
            p_invoice_no, p_estimate_id, v_chain_id, v_service, v_quantity, v_cost_per_unit, 
            v_total_cost, v_total_cost, NOW(), v_delivery_date, v_delivery_details, p_email_id
        );

        SET p_message = 'Invoice successfully generated';

    END main_block;
END;
//

DELIMITER ;


CALL AddInvoice(1, 'client@example.com', @msg);
SELECT @msg;



-- Update Invoice

-- Delete Invoice
                        
                        