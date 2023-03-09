DELETE FROM card;
DELETE FROM operation;
DELETE FROM operation_type;
DELETE FROM agreement;
DELETE FROM card_product;
DELETE FROM account;
DELETE FROM product;

INSERT INTO card_product(id, card_name, payment_system, cashback, co_brand, is_virtual, premium_status, service_price,
                         product_price, currency_code, is_active, card_duration)
VALUES (1, 'Youth Sbercard', 'MIR', null, null, false, 'CLASSIC', 40, 150, 'RUB', true, null),
       (2, 'Youth Sbercard', 'VISA', null, null, false, 'CLASSIC', 60, 150, 'RUB', false, null),
       (3, 'Sbercard for foreigners', 'MASTERCARD', null, null, false, 'GOLD', 100, 200, 'USD', true, null);

INSERT INTO account(id, account_number, client_id, currency_code, current_balance, open_date, close_date, is_active,
                    salary_project, blocked_sum)
VALUES ('38dca868-5eaa-11ed-9b6a-0242ac120002', 'accountnumber1', '798060ff-ed9b-4745-857c-a7a2f4a2e3a2', 'EUR', 100.0,
        '2022-01-15', '2022-01-16', true, 'salaryproject1', 50.0),
       ('7973767c-5eaa-11ed-9b6a-0242ac120002', 'accountnumber2', '695aecfe-152f-421d-9c4b-5831b812cd2a', 'RUB', 200.0,
        '2022-01-16', '2022-01-17', true, 'salaryproject2', 60.0),
       ('9a2382ea-5eaa-11ed-9b6a-0242ac120002', 'accountnumber3', '5f8c8fb4-503a-4727-a47a-19d539fa657c', 'RUB', 200.0,
        '2022-01-16', '2022-01-17', true, 'salaryproject3', 60.0),
       ('9a2382ea-5eaa-11ed-9b6a-0242ac120003', 'accountnumber4', '5f8c8fb4-503a-4727-a47a-19d539fa657c', 'RUB', 200.0,
        '2022-01-16', '2022-01-17', true, 'salaryproject4', 60.0);

INSERT INTO card(id, card_number, account_id, transaction_limit, status, expiration_date, holder_name, digital_wallet,
                 is_default, card_product_id)
VALUES ('c162b31c-5eb9-11ed-9b6a-0242ac120002', '408181057000012', '38dca868-5eaa-11ed-9b6a-0242ac120002', null,
        'ACTIVE', '2011-04-15 00:03:20', 'IVAN IVANOV', null, true, 1),
       ('c60bc3cc-5eb9-11ed-9b6a-0242ac120002', '408181057000013', '7973767c-5eaa-11ed-9b6a-0242ac120002', null,
        'ACTIVE', '2011-04-15 00:03:20', 'PETR PETROV', null, true, 2),
       ('d3db02f6-5eb9-11ed-9b6a-0242ac120002', '408181057000014', '9a2382ea-5eaa-11ed-9b6a-0242ac120002', null,
        'ACTIVE', '2011-04-15 00:03:20', 'STEVE BLACK', null, true, 3);

INSERT INTO product(id, name, schema_name, is_capitalization, amount_min, amount_max, currency_code,
                    is_active, is_revocable, min_interest_rate, max_interest_rate,
                    min_duration_months, max_duration_months, active_since, active_until)
VALUES (1, 'best deposit', 'RECCURING', true, 0, 100000, 'RUB', true, false, 5.8, 7.7, 0, 12, null, null),
       (2, 'best deposit', 'RECCURING', true, 0, 100000, 'EUR', false, false, 5.8, 7.7, 0, 12, null, null),
       (3, 'saving account', 'FIXED', true, 0, 3000, 'RUB', true, false, 4.0, 6.8, 0, 12, null, null);

INSERT INTO agreement(id, agreement_number, account_id, product_id, interest_rate, start_date, end_date,
                      initial_amount, current_balance, is_active, auto_renewal)
VALUES ('a965e7ee-5eae-11ed-9b6a-0242ac120002', '8026/80260/12/00382', '38dca868-5eaa-11ed-9b6a-0242ac120002', 1, 7.2,
        '2022-01-15', '2023-01-15', 100000, 100100, true, false),
       ('ae276528-5eae-11ed-9b6a-0242ac120002', '8026/80260/12/00383', '7973767c-5eaa-11ed-9b6a-0242ac120002', 1, 7.5,
        '2022-01-15', '2023-01-15', 120000, 120200, true, false),
       ('b289e118-5eae-11ed-9b6a-0242ac120002', '8026/80260/12/00384', '9a2382ea-5eaa-11ed-9b6a-0242ac120002', 2, 5.8,
        '2022-01-15', '2023-01-15', 40000, 40100, true, false);

INSERT INTO operation_type(id, type, is_debit)
VALUES (1, 'PAYMENT', true),
       (2, 'TRANSFER', true);

INSERT INTO operation(id, account_id, completed_at, sum, details, currency_code, operation_type_id)
VALUES ('38dca868-5eaa-11ed-9b6a-0242ac120004', '38dca868-5eaa-11ed-9b6a-0242ac120002', now(), 30.0, 'text', 'EUR', 1),
       ('38dca868-5eaa-11ed-9b6a-0242ac120006', '7973767c-5eaa-11ed-9b6a-0242ac120002', now(), 30.0, 'text', 'RUB', 2)