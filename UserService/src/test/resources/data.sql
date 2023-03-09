DELETE FROM db_verification;
DELETE FROM db_fingerprint;
DELETE FROM db_user_profile;
DELETE FROM db_passport_data;
DELETE FROM db_contacts;
DELETE FROM db_client;

ALTER SEQUENCE db_contacts_id_seq RESTART WITH 1;
ALTER SEQUENCE db_passport_data_id_seq RESTART WITH 1;
ALTER SEQUENCE db_user_profile_id_seq RESTART WITH 1;
ALTER SEQUENCE db_verification_id_seq RESTART WITH 1;

INSERT INTO db_client (id, first_name, last_name, sur_name, country_of_residence, date_accession, client_status)
VALUES ('798060ff-ed9b-4745-857c-a7a2f4a2e3a2', 'Ivan', 'Ivanov', 'Ivanovich', 'RU', now(), 'NOT_ACTIVE'),
       ('695aecfe-152f-421d-9c4b-5831b812cd2a', 'Petr', 'Petrov', 'Petrovich', 'RU', now(), 'NOT_ACTIVE'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa657c', 'Steve', 'Black', 'Frankovich', 'RU', now(), 'NOT_ACTIVE');

INSERT INTO db_contacts (id_client, sms_notification_enabled, push_notification_enabled, email, email_subscription_enabled, mobile_phone)
VALUES ('798060ff-ed9b-4745-857c-a7a2f4a2e3a2', false, false, 'ivanov_ivan@mail.ru', false, '89111234567'),
       ('695aecfe-152f-421d-9c4b-5831b812cd2a', false, false, 'petrov_petr@mail.ru', false, '89111234568'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa657c', false, false, 'steve1985@gmail.com', false, '89111234569');

INSERT INTO db_user_profile (id_client, password_encoded, password_salt, security_question, security_answer, date_app_registration)
VALUES ('798060ff-ed9b-4745-857c-a7a2f4a2e3a2', 'password', null, 'who is my cat', 'murzik', now()),
       ('695aecfe-152f-421d-9c4b-5831b812cd2a', 'password', null, 'who is my dog', 'pluto', now()),
       ('5f8c8fb4-503a-4727-a47a-19d539fa657c', 'password', null, 'do i believe in moon landing', 'ufo stole me', now());

INSERT INTO db_passport_data (id_client, identification_passport_number, issuance_date, expiry_date, nationality, birth_date)
VALUES ('798060ff-ed9b-4745-857c-a7a2f4a2e3a2', '1234567890', '2011-04-15 00:03:20', '2025-04-15 00:03:20', 'ukrainian', '1995-04-15'),
       ('695aecfe-152f-421d-9c4b-5831b812cd2a', '1234567891', '2010-04-15 00:03:20', '2024-04-15 00:03:20', 'american', '1996-04-15'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa657c', '1234567892', '2009-04-15 00:03:20', '2034-04-15 00:03:20', 'russian', '1997-04-15');

INSERT INTO db_verification(id_contact, sms_verification_code_6, sms_code_expiration, block_expiration)
VALUES (1, '123456', now() + (20 ||' minutes')::interval, null);

INSERT INTO db_fingerprint(id_client, fingerprint)
VALUES ('798060ff-ed9b-4745-857c-a7a2f4a2e3a2', '666666'),
       ('695aecfe-152f-421d-9c4b-5831b812cd2a', '666666');