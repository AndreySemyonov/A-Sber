DELETE FROM payment_schedule;
DELETE FROM operation;
DELETE FROM operation_type;
DELETE FROM card;
DELETE FROM account;
DELETE FROM agreement;
DELETE FROM credit;
DELETE FROM credit_order;
DELETE FROM product;

INSERT INTO product(id, name, min_sum, max_sum, currency_code, min_interest_rate, max_interest_rate, need_guarantees, delivery_in_cash, early_repayment, need_income_details,
                    min_period_months, max_period_months, is_active, details, calculation_mode, grace_period_months, rate_is_adjustable)
VALUES (1,'Mortgages for secondary house', 300000.00, 100000000.00, 'RUB', 10.40, 17.00, true, false, false, true, 36, 360, true, 'details', 'calculation_mode', 12, false),
       (2, 'Education loan', 100000.00, 1000000.00, 'RUB', 3.00, 17.73, false, false, false, true, 36, 180, true, 'details', 'calculation_mode', 12, false),
       (3, 'Car loan', 300000.00, 2000000.00, 'RUB', 15.00, 17.73, true, false, false, true, 36, 180, true, 'details', 'calculation_mode', 12, false);

INSERT INTO credit_order(id, number, client_id, product_id, status, amount, grace_period_months, creation_date, monthly_income, monthly_expenditure, employer_identification_number)
VALUES ('8a5cbaa4-6e6b-11ed-a1eb-0242ac120001', 'credit_num1', '798060ff-ed9b-4745-857c-a7a2f4a2e3a2', 1, 'ACTIVE', 2000000,  36, current_date, 30000 + 30000*0.12, 30000*0.12, 'id1'),
       ('8a5cbaa4-6e6b-11ed-a1eb-0242ac120002', 'credit_num2', '695aecfe-152f-421d-9c4b-5831b812cd2a', 3, 'ACTIVE', 200000, 6, current_date, 6000 + 6000*0.155, 6000*0.155,'id1');

INSERT INTO credit(id, order_id, type, credit_limit, currency_code, interest_rate, personal_guarantees, grace_period_months, status, late_payment_rate)
VALUES ('9093905a-6e6b-11ed-a1eb-0242ac120001', '8a5cbaa4-6e6b-11ed-a1eb-0242ac120001', 'MORTGAGE' , 2000000, 'RUB',  12.00, true,  36, 'ACTIVE', 0.5),
       ('9093905a-6e6b-11ed-a1eb-0242ac120002', '8a5cbaa4-6e6b-11ed-a1eb-0242ac120002', 'CAR_LOAN' , 200000, 'RUB',  15.50, true,  6, 'ACTIVE', 3.0);

INSERT INTO agreement(id, credit_id, agreement_number, agreement_date, termination_date, responsible_specialist, is_active)
VALUES ('42e0623c-6e6d-11ed-a1eb-0242ac120001', '9093905a-6e6b-11ed-a1eb-0242ac120001', 'agreement_number1',  current_date, make_date(CAST(date_part('year', current_date) as INT) +  20, CAST(date_part('month', current_date) as INT), CAST(date_part('day', current_date) as INT)), 'specialist1', true),
       ('42e0623c-6e6d-11ed-a1eb-0242ac120002', '9093905a-6e6b-11ed-a1eb-0242ac120002', 'agreement_number2',  current_date, make_date(CAST(date_part('year', current_date) as INT) + 3, CAST(date_part('month', current_date) as INT), CAST(date_part('day', current_date) as INT)), 'specialist1', true);

INSERT INTO account(id, credit_id, account_number, principal_debt, interest_debt, is_active,opening_date, currency_code, outstanding_principal_debt, outstanding_interest_debt)
VALUES ('7daa6ee6-6e7a-11ed-a1eb-0242ac120001', '9093905a-6e6b-11ed-a1eb-0242ac120001', 'account_number1', 0, 0, true, '2020-04-15', 'RUB', 0, 0),
       ('7daa6ee6-6e7a-11ed-a1eb-0242ac120002', '9093905a-6e6b-11ed-a1eb-0242ac120002', 'account_number2', 0, 0, true, '2019-12-01', 'RUB', 0, 0);

INSERT INTO card(id, card_number, account_id, holder_name,expiration_date, payment_system, balance, status, transaction_limit, delivery_point, is_digital_wallet, is_virtual)
VALUES ('46561a3e-6e80-11ed-a1eb-0242ac120001', '408181057000011', '7daa6ee6-6e7a-11ed-a1eb-0242ac120001',  'IVAN IVANOV', '2020-04-15', 'MIR', 0, 'ACTIVE', 1000000, 'delivery_point', false, true),
       ('46561a3e-6e80-11ed-a1eb-0242ac120002', '408181057000002', '7daa6ee6-6e7a-11ed-a1eb-0242ac120002',  'PETR PETROV', '2019-12-01', 'MIR', 0, 'ACTIVE', 1000000, 'delivery_point', false, true);

INSERT INTO  operation_type(id, type, is_debt)
VALUES ('52cd04da-6e80-11ed-a1eb-0242ac120001', 'TRANSFER', false),
       ('52cd04da-6e80-11ed-a1eb-0242ac120002', 'TRANSFER', false);

INSERT INTO  operation(id, account_id, operation_type_id, sum, completed_at, details, currency_code)
VALUES ('898b9c7a-6e80-11ed-a1eb-0242ac120001', '7daa6ee6-6e7a-11ed-a1eb-0242ac120001','52cd04da-6e80-11ed-a1eb-0242ac120001', 30000 + 30000*0.12, current_date, 'details', 'RUB'),
       ('898b9c7a-6e80-11ed-a1eb-0242ac120002', '7daa6ee6-6e7a-11ed-a1eb-0242ac120002','52cd04da-6e80-11ed-a1eb-0242ac120002', 6000 + 6000*0.155, current_date, 'details', 'RUB');

INSERT INTO payment_schedule(id, account_id, payment_date, principal, interest)
VALUES ('ce149c48-6e80-11ed-a1eb-0242ac120001', '7daa6ee6-6e7a-11ed-a1eb-0242ac120001', '2022-12-01', 30000, 30000*0.12),
        ('ce149c48-6e80-11ed-a1eb-0242ac120002', '7daa6ee6-6e7a-11ed-a1eb-0242ac120002', '2022-12-01', 6000, 6000*0.155);