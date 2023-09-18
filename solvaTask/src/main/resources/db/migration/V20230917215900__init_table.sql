CREATE TYPE currency_shortcode AS ENUM ('USD', 'KZT', 'RUB');
CREATE TYPE expense_category AS ENUM ('PRODUCT', 'SERVICE');

create sequence limit_seq;
create table client_limit
(
    id                 bigint primary key,
    bank_account       VARCHAR(64),
    limit_sum          numeric,
    balance_product    numeric,
    balance_service    numeric,
    datetime           timestamp,
    currency_shortcode VARCHAR(16)
);

create sequence transaction_seq;
create table transaction
(
    id                 int primary key,
    account_from       VARCHAR(64),
    account_to         VARCHAR(64),
    sum                numeric,
    currency_shortcode VARCHAR(16),
    expense_category   VARCHAR(16),
    limit_exceed       bool,
    datetime           timestamp
);

CREATE TABLE currency_pair
(
    id             VARCHAR(255) PRIMARY KEY,
    shortcode_from VARCHAR(16),
    shortcode_to   VARCHAR(16),
    close          numeric,
    date           date
);
