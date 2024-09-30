CREATE TABLE PRICES (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    BRAND_ID BIGINT,
    START_DATE TIMESTAMP,
    END_DATE TIMESTAMP,
    PRICE_LIST INT,
    PRODUCT_ID BIGINT,
    PRIORITY INT,
    PRICE DECIMAL(19,2),
    CURR VARCHAR(3)
);