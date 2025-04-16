create database invoice;
use invoice;

create table Users(
					user_id int auto_increment primary key ,
                    full_name varchar(255) not null ,
                    email varchar(255) unique not null,
                    upassword varchar(255) not null,
                    role varchar (100) not null,
                    status enum('active','inactive') default 'active',
                    created_at timestamp default current_timestamp,
                    FOREIGN KEY (role) REFERENCES role_details(rolecode),
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

                    );
                    
create table role_details
(
id int auto_increment primary key,
rolecode varchar(100) not null unique,
roledescription varchar(255) not null,
status  enum('active','inactive') default 'active',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

insert into role_details(rolecode,roledescription) values('SP','Sales Person');
insert into role_details(rolecode,roledescription) values('AD','Administrative Role');

create table role_component_mapping(role_id int primary key auto_increment, rolecode varchar(15),component_url varchar(20),
										status enum('active','inactive') default 'active',created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
                                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);
                                        
insert into role_component_mapping(rolecode,component_url,status)values('AD','Admin','active'),('SP','SalesPerson','active');

-- Registration Procedure 

DELIMITER $$

CREATE PROCEDURE NewLoginDetails(
    IN p_fullname VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_upassword VARCHAR(255),
    IN p_urole VARCHAR(100),
    OUT p_userid INT,
    OUT p_ufullname VARCHAR(255),
    OUT p_uemail VARCHAR(255),
    OUT p_upass VARCHAR(255),
    OUT p_curl VARCHAR(255),
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_component_url VARCHAR(255);
    DECLARE v_email_exists INT;

    -- ✅ Force default role to 'SP'
    SET p_urole = 'SP';

    -- Check if email already exists
    SELECT COUNT(*) INTO v_email_exists
    FROM Users
    WHERE email = p_email;

    IF v_email_exists > 0 THEN
        SET p_message = 'Email already exists';
        SET p_userid = NULL;
        SET p_ufullname = NULL;
        SET p_uemail = NULL;
        SET p_upass = NULL;
        SET p_curl = NULL;
    ELSE
        -- Try to get component URL based on role
        SELECT component_url INTO v_component_url
        FROM role_component_mapping
        WHERE rolecode = p_urole;

        -- If role is invalid (URL not found)
        IF v_component_url IS NULL THEN
            SET p_message = 'Failure: Role not found';
            SET p_userid = NULL;
            SET p_ufullname = NULL;
            SET p_uemail = NULL;
            SET p_upass = NULL;
            SET p_curl = NULL;
        ELSE
            -- Insert user details
            INSERT INTO Users (full_name, email, upassword, role, status, created_at)
            VALUES (p_fullname, p_email, p_upassword, p_urole, 'active', CURRENT_TIMESTAMP);

            -- Fetch inserted user details
            SET p_userid = LAST_INSERT_ID();

            SELECT full_name, email, upassword
            INTO p_ufullname, p_uemail, p_upass
            FROM Users
            WHERE user_id = p_userid;

            -- Set URL and success message
            SET p_curl = v_component_url;
            SET p_message = 'Success';
        END IF;
    END IF;
END$$

DELIMITER ;


-- Declare output variables
SET @userid = 0;
SET @ufullname = '';
SET @uemail = '';
SET @upass = '';
SET @curl = '';
SET @message = '';

-- Call the procedure
CALL NewLoginDetails(
    'Selena Williams',                -- Full Name
    'selwill@example.com',        -- Email
    '1234567',       -- Password
    'AD',                   -- Role (must exist in role_details and role_component_mapping)
    @userid,
    @ufullname,
    @uemail,
    @upass,
    @curl,
    @message
);

-- Get the result
SELECT 
    @userid AS user_id,
    @ufullname AS full_name,
    @uemail AS email,
    @upass AS password,
    @curl AS component_url,
    @message AS result_message;
select * from users;

-- Validation Procedure

DELIMITER $$

CREATE PROCEDURE ValidateLogin(
    IN p_email VARCHAR(255),
    IN p_upassword VARCHAR(255),
    OUT p_message VARCHAR(255),
    OUT p_name VARCHAR(255),
    OUT p_email_out VARCHAR(255),
    OUT p_id INT,
    OUT p_curl VARCHAR(255),
    OUT p_role VARCHAR(255)
)
BEGIN
    DECLARE user_exists INT DEFAULT 0;
    DECLARE correct_password INT DEFAULT 0;

    -- Check if the user exists with the given email
    SELECT COUNT(*) INTO user_exists
    FROM Users
    WHERE email COLLATE utf8mb4_bin = p_email COLLATE utf8mb4_bin;

    IF user_exists = 1 THEN
        -- Check if password matches
        SELECT COUNT(*) INTO correct_password
        FROM Users
        WHERE email COLLATE utf8mb4_bin = p_email COLLATE utf8mb4_bin
          AND upassword COLLATE utf8mb4_bin = p_upassword COLLATE utf8mb4_bin;

        IF correct_password = 1 THEN
            -- Set the output parameters with user info and component_url
            SELECT 
                u.full_name,
                u.email,
                u.user_id,
                r.component_url,
                u.role
            INTO 
                p_name, 
                p_email_out, 
                p_id, 
                p_curl, 
                p_role
            FROM Users u
            JOIN role_component_mapping r ON r.rolecode = u.role
            WHERE u.email COLLATE utf8mb4_bin = p_email COLLATE utf8mb4_bin
              AND u.upassword COLLATE utf8mb4_bin = p_upassword COLLATE utf8mb4_bin;

            -- Set the success message
            SET p_message = 'Success';

        ELSE 
            -- Return failure message for incorrect password
            SET p_message = 'Failure: Incorrect password';
            SET p_name = NULL;
            SET p_email_out = NULL;
            SET p_id = NULL;
            SET p_curl = NULL;
            SET p_role = NULL;
        END IF;

    ELSE 
        -- Return failure message for non-existent user
        SET p_message = 'Failure: User does not exist';
        SET p_name = NULL;
        SET p_email_out = NULL;
        SET p_id = NULL;
        SET p_curl = NULL;
        SET p_role = NULL;
    END IF;

END$$

DELIMITER ;




-- FORGOT PASSWORD

DELIMITER $$

CREATE PROCEDURE ForgotPassword (
    IN p_full_name VARCHAR(255),
    IN p_email VARCHAR(255)
)
BEGIN
    DECLARE v_userid INT;
    DECLARE v_correct_email VARCHAR(255);
    DECLARE v_role VARCHAR(10);

    -- Step 1: Check if full_name exists
    SELECT email INTO v_correct_email
    FROM users
    WHERE full_name = p_full_name
    LIMIT 1;

    -- Case 1: Full name not found
    IF v_correct_email IS NULL THEN
        SELECT 'User not found' AS message;

    -- Case 2: Full name exists, but email doesn't match
    ELSEIF v_correct_email != p_email THEN
        SELECT 'Email is incorrect for the given user' AS message;

    -- Case 3: Full match found
    ELSE
        SELECT user_id, role INTO v_userid, v_role
        FROM users
        WHERE full_name = p_full_name AND email = p_email
        LIMIT 1;

        -- Return user details along with curl values from role_component_mapping in a single result set
        SELECT 
            u.user_id, u.full_name, u.email, u.upassword, u.role, 
            rcm.component_url
        FROM users u
        LEFT JOIN role_component_mapping rcm
            ON rcm.rolecode = u.role
        WHERE u.user_id = v_userid;
    END IF;
END$$

DELIMITER ;

select * from users;
insert into users(full_name,email,upassword,role,status)values('Samiya Sardar','samiyasardars85@gmail.com','samsam','AD','active');
call ForgotPassword('Selena Williams','selwill@example.com');


-- RESET PASSWORD

DELIMITER $$

CREATE PROCEDURE ResetPassword (
    IN input_email VARCHAR(255),
    IN input_full_name VARCHAR(255),
    IN new_password1 VARCHAR(255),
    IN new_password2 VARCHAR(255)
)
BEGIN
    DECLARE userCount INT;

    -- Step 1: Check if user exists
    SELECT COUNT(*) INTO userCount
    FROM Users
    WHERE email = input_email AND full_name = input_full_name;

    IF userCount = 0 THEN
        SELECT 'Error: No user found with the given email and full name.' AS message;

    ELSEIF new_password1 != new_password2 THEN
        -- Step 2: Check if both passwords match
        SELECT 'Error: Passwords do not match.' AS message;

    ELSE
        -- Step 3: Update password
        UPDATE Users
        SET upassword = new_password1,
            updated_at = CURRENT_TIMESTAMP
        WHERE email = input_email AND full_name = input_full_name;

        SELECT 'Success: Password updated successfully.' AS message;
    END IF;
END$$

DELIMITER ;

select * from users;

CALL ResetPassword(
    'will@gmail.com',
    'Jac Will',
    'NewPass123',
    'NewPass123'
);
