DROP
ALL OBJECTS;
CREATE TABLE Users
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    user_name           varchar2(100),
    email               VARCHAR(250) DEFAULT NULL,
    password            VARCHAR2(300),
    email_Verified      varchar2(10),
    account_Locked      varchar2(10),
    account_Expired     varchar2(10),
    credentials_Expired varchar2(10),
    status              varchar2(1),
    roles               varchar2(250),
    provider            varchar2(250),
    provider_Id         varchar2(250),
    IMAGE_URL           varchar2(250),
    blocked_by          varchar2(250)
);
CREATE TABLE Coffee
(
    coffee_id       INT AUTO_INCREMENT PRIMARY KEY,
    brand           VARCHAR(20),
    origin          VARCHAR(20),
    characteristics VARCHAR(30)
);

CREATE TABLE ` MENU `
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION       varchar2(100),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6),
    BATCH_NO          varchar2(10)
);

CREATE TABLE MENU_DETAIL
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION varchar2(100),
    ON_CLICK    varchar2(100),
    ID_MENU     INT
);

CREATE TABLE STUDENT
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    NAME        varchar2(100),
    ROLL_NUMBER varchar2(100)
);

CREATE TABLE BAGS
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    NAME        varchar2(100),
    DESCRIPTION varchar2(100),
    ID_STUDENT  int
);

CREATE TABLE TRADE
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    isin     varchar2(100),
    quantity int,
    PRICE    double,
    customer varchar2(100),
    xmlns    varchar2(100),
    text     varchar2(100)
);

CREATE TABLE PERSON
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    NAME              varchar2(100),
    DATE_OF_BIRTH     TIMESTAMP(6),
    ADDRESS_ID        int,
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6),
    status            varchar2(1)
);

CREATE TABLE ADDRESS
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    CITY              varchar2(100),
    DISTRICT          varchar2(100),
    ADDRESS_DETAIL    varchar2(200),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6),
    status            varchar2(1)
);

CREATE TABLE UPLOAD_FILE_ERROR
(
    ID                   INT AUTO_INCREMENT PRIMARY KEY,
    UPLOAD_FILE_ERROR    VARCHAR2(50),
    UPLOAD_FILE_ERROR_ID VARCHAR2(50),
    LINE_NUMBER          NUMBER(15),
    LINE                 VARCHAR2(4000),
    ERROR_CODE           VARCHAR2(10),
    ERROR_MESSAGE        VARCHAR2(500),
    ETL_DATE             TIMESTAMP(6),
    BATCH_NO             VARCHAR2(20)
);

CREATE TABLE UPLOAD_FILE_COFFEE
(
    ID              INT AUTO_INCREMENT PRIMARY KEY,
    LINE_NUMBER     NUMBER(15),
    LINE            VARCHAR2(4000),
    brand           VARCHAR(20),
    origin          VARCHAR(20),
    characteristics VARCHAR(30),
    BATCH_NO        VARCHAR2(20)
);



create table USER_STATUS
(
    USER_STATUS_ID  INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID         NUMBER(15),
    status          VARCHAR(10),
    LAST_TIME_LOGIN VARCHAR(20)
);

create table CHAT
(
    CHAT_ID    INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID1      NUMBER(15),
    USER_ID2      NUMBER(15),
    BLOCKED_BY NUMBER(15),
    CREATED_AT timestamp(6),
    UPDATED_AT timestamp(6)
);

create table MESSAGE
(
    MESSAGE_ID   INT AUTO_INCREMENT PRIMARY KEY,
    SENDER_ID    NUMBER(15),
    recipient_Id NUMBER(15),
    CHAT_ID      NUMBER(15),
    content      VARCHAR2(4000),
    CONTENT_TYPE VARCHAR2(15),
    CREATE_AT    timestamp(6),
    UPDATE_AT    timestamp(6),
    READ         VARCHAR2(1)
);

create table FILE
(
    FILE_ID    INT AUTO_INCREMENT PRIMARY KEY,
    MESSAGE_ID NUMBER(15),
    URL        VARCHAR2(250),
    type       VARCHAR2(250),
    FILE_NAME  VARCHAR2(250)
);


