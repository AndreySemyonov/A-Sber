DELETE FROM phone_transfer;
DELETE FROM phone_code;
DELETE FROM data_for_international;
DELETE FROM card_transfer;
DELETE FROM transfer_details;
DELETE FROM transfer_type;
DELETE FROM transfer_system;

INSERT INTO transfer_system(transfer_system_id,transfer_system_name)
VALUES (1111110,'system1'),
       (2222220,'system2');

INSERT INTO transfer_type(transfer_type_id,transfer_type_name, min_transfer_sum, max_transfer_sum, min_commission, max_commission, commission_percent, commission_fix, currency_from)
VALUES (1111111,'card', 0,1000000,1,10,5,10, 'RUB'),
       (2222222,'phone',0, 50000,1,25,null,10, 'RUB'),
       (3333333,'phone',0, 50000,1,25,null,null, 'EUR');

INSERT INTO phone_code(phone_code, country_name, available, old_code, update_at)
VALUES (7,'RUSSIA',true,7,'2022-02-02'),
       (1,'USA',true,1,'2022-02-02');

INSERT INTO transfer_details(transfer_id,sender_id, receiver_id, transfer_type_id, transfer_sum, currency_from, currency_to, exchange_rate, transfer_exch_sum, commission, message, transfer_date, transfer_system_id, is_favourite, is_auto, periodicity, start_date, expiration_date, purpose_of_transfer, authorization_code, status, is_deleted)
VALUES (1111111,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (2222222,'7973767c-5eaa-11ed-9b6a-0242ac120002', '9a2382ea-5eaa-11ed-9b6a-0242ac120002',1111111,100,'RUB','USD',70,1,5,'hi!', '2022-12-15', 1111110, false,false,0,'2022-12-15',null,'private',111,'EXECUTED', false),
       (3333333,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',2222222, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-02-02', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111,'EXECUTED', false),
       (4444444,'7973767c-5eaa-11ed-9b6a-0242ac120002', '9a2382ea-5eaa-11ed-9b6a-0242ac120002',2222222,100,'RUB','USD',70,1,5,'hi!', '2022-12-15', 1111110, false,false,0,'2022-12-15',null,'private',111,'EXECUTED', false),
       (5555555,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'DRAFT', false),
       (6666666,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (7777777,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (8888888,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (9999999,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1000000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1100000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1200000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1300000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1400000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1500000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1600000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1700000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1800000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (1900000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (2000000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (2100000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (2200000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (2300000,'38dca868-5eaa-11ed-9b6a-0242ac120002', '7973767c-5eaa-11ed-9b6a-0242ac120002',1111111, 100, 'RUB', 'RUB', 1, 1, 5,'hi!', '2022-01-17', 1111110, true, false, 12, '2022-01-17', '2023-02-17', 'private', 111, 'EXECUTED', false),
       (2400000,'6f300f08-932b-11ed-a1eb-0242ac120002', '79cd628a-932b-11ed-a1eb-0242ac120002',2222222,100,'RUB','USD',70,1,5,'hi!', '2022-12-15', 1111110, false,false,0,'2022-12-15',null,'private',111,'DRAFT', false);

INSERT INTO card_transfer(id,transfer_details_id, sender_card_number, receiver_card_number)
VALUES (1111111,1111111,'408181057000012', '408181057000013'),
       (2222222,2222222, '408181057000013', '408181057000014');

INSERT INTO data_for_international(data_int_id,card_transfer_id, cvv, receiver_name, receiver_surname, expire_date)
VALUES (1111111,1111111,111,'IVAN IVANOV', 'PETR PETROV', '2022-01-17'),
       (2222222,2222222,222, 'PETR PETROV', 'STEVE BLACK','2022-12-15');

INSERT INTO phone_transfer(id,transfer_details_id, sender_card_number, receiver_card_number, receiver_phone, phone_code)
VALUES (1111111,3333333,'408181057000012','408181057000013', 78472345736,7),
       (2222222,4444444, '408181057000013', '408181057000014', 12223334455, 1);


