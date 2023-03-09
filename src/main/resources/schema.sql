DROP TABLE IF EXISTS Client;
CREATE TABLE Client
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    user_name         varchar2(100),
    email             VARCHAR(250) DEFAULT NULL,
    first_name        VARCHAR(250),
    last_name         VARCHAR(250),
    password          VARCHAR2(300),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6),
    status            varchar2(1),
    roles             varchar2(250),
    provider  varchar2(250)
);