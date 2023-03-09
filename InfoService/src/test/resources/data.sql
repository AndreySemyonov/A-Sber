INSERT INTO exchange_rate(
    id, is_cross, selling_rate, buying_rate, unit, sign, name, iso_code, currency_code, update_at)
VALUES ('e2c6d6d1-d56f-43a6-a8f6-44c01cca9058', true, 322.0, 228.0, 1, '$', 'Доллар США', '123', 'USD', '2022-12-14 13:06:28.580532'),
       ('2ca47c73-ae42-4e57-bdef-d93c44f68695', true, 78.5730, 70.7157, 1, 'sign', 'EUR', 'iso', 'EUR', '2023-02-09 13:03:41.334652');

INSERT INTO bank_branch (
    id, insurance, consultation, replenish_account, replenish_card, ramp, exotic_currency, currency_exchange,
    accept_payment, money_transfer, cash_withdraw, work_at_weekends, closing_time, opening_time, is_closed,
    branch_address, city, branch_coordinates, branch_number, bank_branch_type)
VALUES ('123e4567-e89b-12d3-a456-426614174000', true, true, true, true, true, false, true, true, true, true, false,
        '18:00', '09:00', false, 'pl. Krasnaya, 1', 'Moscow', 'Po centry', '1', 'Otdeleniye');

INSERT INTO bank_branch (
    id, insurance, consultation, replenish_account, replenish_card, ramp, exotic_currency, currency_exchange,
    accept_payment, money_transfer, cash_withdraw, work_at_weekends, closing_time, opening_time, is_closed,
    branch_address, city, branch_coordinates, branch_number, bank_branch_type)
VALUES ('178a2322-86b7-11ed-a1eb-0242ac120002', false, false, false, false, true, false, false, true, true, true, true,
        '23:59', '00:00', false, 'ul. Lenina, 2', 'Moscow', 'Chut'' levee', '2', 'ATM');
