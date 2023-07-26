INSERT INTO USERS (USER_NAME, email, password, STATUS, ROLES, provider, provider_id, email_Verified, ACCOUNT_LOCKED,
                   ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, image_url)

VALUES ('cat', 'cat@gmail.com', '$2a$10$a/bzZilAvwJfZSTas2mih.HX/F6l2JfX9vkg7/FA0t6vt9eIsGN02', 'A', 'cat', null, null,
        null, null, null, null,
        'https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187_square.jpg') ,
('dog', 'dog@gmail.com', '$2a$10$DxkO.3PKCa4aCUiKxrzU0Otu3bQff.TfQGn5E836qXWfDlkKxktlu', 'A', 'dog', null, null, null, null, null, null,
    'https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*')
,
('1', '1@gmail.com', '$2a$10$GaxkVA7THtmNvzlfwkhFFO6fUqACzZe92cx4PLbNurKEk91UKjr/u', 'A', '1', null, null, null, null, null, null,
    'https://th.bing.com/th/id/OIP.A6LQhq2ajzLGaHkEwd-dJAAAAA?w=140&h=180&c=7&r=0&o=5&pid=1.7')
,
('2', '2@gmail.com', '$2a$10$wd78U7mkxzmCiNbKlwtkAewavkD2tCuh4.JP4XG6kghlWRk78mX5u', 'A', '2', null, null, null, null, null, null,
    'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7QzepfUSevewnR6X0XbTTRs98qvh1c10fAg&usqp=CAU');
