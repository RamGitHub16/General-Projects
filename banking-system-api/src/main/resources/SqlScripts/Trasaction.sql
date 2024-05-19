CREATE TABLE Transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES BankAccount(id),
    timestamp TIMESTAMP,
    amount DECIMAL(19, 2),
    description VARCHAR(255)
);