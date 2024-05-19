CREATE TABLE BankAccount (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) UNIQUE,
    account_holder_name VARCHAR(255),
    account_type VARCHAR(50),
    balance DECIMAL(19, 2)
);
