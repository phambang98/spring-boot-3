INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('cat', 'cat@gmail.com', '$2a$10$a/bzZilAvwJfZSTas2mih.HX/F6l2JfX9vkg7/FA0t6vt9eIsGN02', 'A', 'cat', null, null,
        null, null, null, null,
        'https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187_square.jpg');
INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('dog', 'dog@gmail.com', '$2a$10$DxkO.3PKCa4aCUiKxrzU0Otu3bQff.TfQGn5E836qXWfDlkKxktlu', 'A', 'dog', null, null,
        null, null, null, null,
        'https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*');
INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('1', '1@gmail.com', '$2a$10$GaxkVA7THtmNvzlfwkhFFO6fUqACzZe92cx4PLbNurKEk91UKjr/u', 'A', '1', null, null, null,
        null, null, null,
        'https://th.bing.com/th/id/OIP.A6LQhq2ajzLGaHkEwd-dJAAAAA?w=140&h=180&c=7&r=0&o=5&pid=1.7');
INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('2', '2@gmail.com', '$2a$10$wd78U7mkxzmCiNbKlwtkAewavkD2tCuh4.JP4XG6kghlWRk78mX5u', 'A', '2', null, null, null,
        null, null, null,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7QzepfUSevewnR6X0XbTTRs98qvh1c10fAg&usqp=CAU');
INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('3', '3@gmail.com', '$2a$10$6Gjtn2LrsfFKxyoQHgQ2zu6araKKKKzxEevbzHyEQSjU2Y6zQkdGC', 'A', '3', null, null, null,
        null, null, null,
        'https://png.pngtree.com/thumb_back/fw800/back_our/20190622/ourmid/pngtree-countdown-3-cool-stars-image_211460.jpg');
INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('4', '4@gmail.com', '$2a$10$b5QzvI3qqdnL3W9P9wXDEuhx0q1EHqNV1bqQ/OIM9hFgZsdiAvvXK', 'A', '4', null, null, null,
        null, null, null,
        'https://thansohoconline.com/wp-content/uploads/2023/01/than-so-hoc-so-4-8.jpg');
INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('5', '5@gmail.com', '$2a$10$iOVVy/gref2kBFC48cVy0eKNExChdUuZr5.gOXz5R3/Hw6jWOvsem', 'A', '5', null, null, null,
        null, null, null,
        'https://bahaiteachings.s3.us-west-1.amazonaws.com/2021/01/spiritual-significance-number-five.jpg');



INSERT INTO USER_STATUS(USER_ID, status, LAST_TIME_LOGIN)
VALUES (1, 'OFFLINE', '2022-10-10 16:16:16');
INSERT INTO USER_STATUS(USER_ID, status, LAST_TIME_LOGIN)
VALUES (2, 'OFFLINE', '2023-01-01 15:54:20');
INSERT INTO USER_STATUS(USER_ID, status, LAST_TIME_LOGIN)
VALUES (3, 'OFFLINE', '2023-04-04 05:20:11');
INSERT INTO USER_STATUS(USER_ID, status, LAST_TIME_LOGIN)
VALUES (4, 'OFFLINE', '2020-04-04 05:20:11');
INSERT INTO USER_STATUS(USER_ID, status, LAST_TIME_LOGIN)
VALUES (5, 'OFFLINE', '2023-01-01 15:54:20');
INSERT INTO USER_STATUS(USER_ID, status, LAST_TIME_LOGIN)
VALUES (6, 'OFFLINE', '2023-04-04 05:20:11');
INSERT INTO USER_STATUS(USER_ID, status, LAST_TIME_LOGIN)
VALUES (7, 'OFFLINE', '2020-04-04 05:20:11');;

INSERT INTO CHAT(USER_ID1, USER_ID2, CHAT_TYPE, DISPLAY_NAME, IMAGE_URL, BLOCKED_BY, CREATED_AT, UPDATED_AT)
VALUES (3, 4, 'NORMAL', NULL, NULL, NULL, DATE '2023-05-19', DATE '2023-05-19');
INSERT INTO CHAT(USER_ID1, USER_ID2, CHAT_TYPE, DISPLAY_NAME, IMAGE_URL, BLOCKED_BY, CREATED_AT, UPDATED_AT)
VALUES (3, 1, 'NORMAL', NULL, NULL, NULL, DATE '2023-05-19', DATE '2023-05-19');

INSERT INTO CHAT(USER_ID1, USER_ID2, CHAT_TYPE, DISPLAY_NAME, IMAGE_URL, BLOCKED_BY, CREATED_AT, UPDATED_AT)
VALUES (NULL, NULL, 'GROUP', 'nhom 1', NULL, NULL, DATE '2023-05-19', DATE '2023-05-19');

INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (3, 4, 1, 'cc', 'text', TIMESTAMP '2023-05-19 09:55:32.616', TIMESTAMP '2023-05-19 09:55:32.616',
        NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (4, 3, 1, 'ok ban', 'text', TIMESTAMP '2023-05-19 09:56:32.616', TIMESTAMP '2023-05-19 09:56:32.616',
        NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (3, 4, 1, 'nhan nham`', 'text', TIMESTAMP '2023-05-19 09:57:32.616',
        TIMESTAMP '2023-05-19 09:57:32.616', NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (3, 1, 2, 'cuu ', 'text', TIMESTAMP '2023-05-19 09:54:32.616', TIMESTAMP '2023-05-19 09:54:32.616',
        NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (3, 1, 2, 'toi sap oang', 'text', TIMESTAMP '2023-05-19 09:55:32.616',
        TIMESTAMP '2023-05-19 09:55:32.616', NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (1, 3, 2, 'ok de toi cuu ban', 'text', TIMESTAMP '2023-05-19 09:56:32.616',
        TIMESTAMP '2023-05-19 09:56:32.616', NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (3, 1, 2, '33', 'file', TIMESTAMP '2023-05-25 09:57:32.616', TIMESTAMP '2023-05-25 09:57:32.616',
        NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (1, 3, 2, null, 'file', TIMESTAMP '2023-05-25 09:58:32.616', TIMESTAMP '2023-05-25 09:58:32.616',
        NULL);
INSERT INTO MESSAGE(SENDER_ID, recipient_Id, CHAT_ID, CONTENT, CONTENT_TYPE, CREATE_AT, UPDATE_AT, READ)
VALUES (1, NULL, 3, 'nhom 1 dc tao', 'text', TIMESTAMP '2023-05-26 09:58:32.616',
        TIMESTAMP '2023-05-26 09:58:32.616', NULL);

INSERT INTO FILE(URL, TYPE, MESSAGE_ID, FILE_NAME)
VALUES ('\data\upload\chat\2\1685002236836_2', 'image/png', 7, '1685002236850_2');
INSERT INTO FILE(URL, TYPE, MESSAGE_ID, FILE_NAME)
VALUES ('\data\upload\chat\2\1685002321848_2', 'image/png', 8, '1685002321848_2');
INSERT INTO FILE(URL, TYPE, MESSAGE_ID, FILE_NAME)
VALUES ('\data\upload\chat\2\1685002321861_2', 'image/png', 8, '1685002321861_2');
INSERT INTO FILE(URL, TYPE, MESSAGE_ID, FILE_NAME)
VALUES ('\data\upload\chat\2\1685002758502_3.gif', 'image/gif', 8, '1685002758515_3.gif');

INSERT INTO CHAT_GROUP(CHAT_ID, USER_ID, JOIN_DATE)
VALUES (3, 3, DATE '2023-05-21');
INSERT INTO CHAT_GROUP(CHAT_ID, USER_ID, JOIN_DATE)
VALUES (3, 1, DATE '2023-05-21');
INSERT INTO CHAT_GROUP(CHAT_ID, USER_ID, JOIN_DATE)
VALUES (3, 4, DATE '2023-05-25');

insert into PRIZE_GROUP(DESCRIPTION, DATE_TIME, STATUS, LAST_APPROVE_BY, LAST_APPROVE_DATE, LAST_UPDATE_BY,
                        LAST_UPDATE_DATE)
VALUES ('nhom 1', current_date, 'A', 'SYSTEM', current_date, 'SYSTEM', current_date);


insert into PRIZES(PRIZE_GROUP_ID, DESCRIPTION, LUCKY_NUMBER, IMAGE_URL, TYPE, DISPLAY_NUMBER, QUANTITY, STATUS,
                   LAST_APPROVE_BY, LAST_APPROVE_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)
VALUES (1, 'giai 1', 10, NULL, '1', 1, 1, 'A', 'SYSTEM', current_date, 'SYSTEM', current_date);

insert into PRIZES(PRIZE_GROUP_ID, DESCRIPTION, LUCKY_NUMBER, IMAGE_URL, TYPE, DISPLAY_NUMBER, QUANTITY, STATUS,
                   LAST_APPROVE_BY, LAST_APPROVE_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)
VALUES (1, 'giai 2', 20, NULL, '2', 2, 2, 'A', 'SYSTEM', current_date, 'SYSTEM', current_date);


insert into PRIZES(PRIZE_GROUP_ID, DESCRIPTION, LUCKY_NUMBER, IMAGE_URL, TYPE, DISPLAY_NUMBER, QUANTITY, STATUS,
                   LAST_APPROVE_BY, LAST_APPROVE_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)
VALUES (1, 'giai 3', 50, NULL, '3', 3, 5, 'A', 'SYSTEM', current_date, 'SYSTEM', current_date);





