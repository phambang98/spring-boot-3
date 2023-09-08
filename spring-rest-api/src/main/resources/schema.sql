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

CREATE TABLE REFRESH_TOKEN
(
    ID            INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID       NUMBER(15),
    REFRESH_TOKEN VARCHAR(50),
    EXPIRY_DATE   TIMESTAMP(6)
);

CREATE TABLE Coffee
(
    coffee_id       INT AUTO_INCREMENT PRIMARY KEY,
    brand           VARCHAR(20),
    origin          VARCHAR(20),
    characteristics VARCHAR(30)
);

CREATE TABLE MENU
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION       VARCHAR2(100),
    STATUS            VARCHAR2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6),
    BATCH_NO          VARCHAR2(10)
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
    CHAT_ID      INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID1     NUMBER(15),
    USER_ID2     NUMBER(15),
    CHAT_TYPE    VARCHAR2(10),
    DISPLAY_NAME VARCHAR2(100),
    IMAGE_URL    VARCHAR2(250),
    BLOCKED_BY   NUMBER(15),
    CREATED_AT   timestamp(6),
    UPDATED_AT   timestamp(6),
    CREATED_BY   NUMBER(15)
);

create table CHAT_GROUP
(
    CHAT_GROUP_ID INT AUTO_INCREMENT PRIMARY KEY,
    CHAT_ID       NUMBER(15),
    USER_ID       NUMBER(15),
    JOIN_DATE     timestamp(6)
);

create table MESSAGE
(
    MESSAGE_ID   INT AUTO_INCREMENT PRIMARY KEY,
    SENDER_ID    NUMBER(15),
    recipient_Id NUMBER(15),
    CHAT_ID      NUMBER(15),
    CHAT_TYPE    VARCHAR2(15),
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


create table MOVIES
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    MOVIE_NAME        VARCHAR2(250),
    RELEASE_YEAR      NUMBER(4),
    STORY             VARCHAR2(500),
    FILE_ID           NUMBER(15),
    GENRE_ID          NUMBER(15),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table RATINGS
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    MOVIE_ID          NUMBER(15),
    USER_ID           NUMBER(15),
    LIKES             NUMBER(1),
    DISLIKE           NUMBER(1),
    RATING            NUMERIC(2, 1),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table REVIEWS
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    PARENT_ID         NUMBER(15),
    MOVIE_ID          NUMBER(15),
    USER_ID           NUMBER(15),
    COMMENTS          VARCHAR2(500),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table GENRES
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    DISPLAY_NAME      VARCHAR2(50),
    DESCRIPTION       VARCHAR2(500),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table MOVIE_GENRES
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    MOVIE_ID          NUMBER(15),
    GENRE_ID          NUMBER(15),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table USER_MONEY
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID           NUMBER(15),
    TOTAL_MONEY       NUMBER(20),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table TRANSACTION_USER_MONEY
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID           NUMBER(15),
    AMOUNT_MONEY      NUMBER(20),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table LUCKY_RESULT
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID           NUMBER(15),
    PRIZE_ID          NUMBER(15),
    LUCKY_NUMBER      NUMBER(10),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table PRIZE_GROUP
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION       VARCHAR2(500),
    DATE_TIME         TIMESTAMP(6),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table PRIZES
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    PRIZE_GROUP_ID    NUMBER(15),
    DESCRIPTION       VARCHAR2(500),
    LUCKY_NUMBER_FROM NUMBER(10),
    LUCKY_NUMBER_TO   NUMBER(10),
    IMAGE_URL         VARCHAR2(250),
    TYPE              VARCHAR2(5),
    DISPLAY_NUMBER    NUMBER(15),
    QUANTITY          NUMBER(2),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);


create table HORSE_RACING_RESULT
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    HORSE_RACING_ID   NUMBER(15),
    TOP               NUMBER(2),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

create table HORSE_RACING_RESULT_USER
(
    ID                     INT AUTO_INCREMENT PRIMARY KEY,
    HORSE_RACING_RESULT_ID NUMBER(15),
    USER_ID                NUMBER(15),
    BET_AMOUNT             NUMBER(17, 2),
    STATUS                 varchar2(1),
    LAST_APPROVE_BY        VARCHAR2(50),
    LAST_APPROVE_DATE      TIMESTAMP(6),
    LAST_UPDATE_BY         VARCHAR2(50),
    LAST_UPDATE_DATE       TIMESTAMP(6)
);

create table HORSE_RACING
(
    ID                    INT AUTO_INCREMENT PRIMARY KEY,
    HORSE_RACING_GROUP_ID NUMBER(15),
    DESCRIPTION           varchar2(500),
    DISPLAY_NUMBER        NUMBER(15),
    IMAGE_URL             VARCHAR2(250),
    STATUS                varchar2(1),
    LAST_APPROVE_BY       VARCHAR2(50),
    LAST_APPROVE_DATE     TIMESTAMP(6),
    LAST_UPDATE_BY        VARCHAR2(50),
    LAST_UPDATE_DATE      TIMESTAMP(6)
);

create table HORSE_RACING_GROUP
(
    ID                INT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION       varchar2(500),
    DATE_TIME         TIMESTAMP(6),
    STATUS            varchar2(1),
    LAST_APPROVE_BY   VARCHAR2(50),
    LAST_APPROVE_DATE TIMESTAMP(6),
    LAST_UPDATE_BY    VARCHAR2(50),
    LAST_UPDATE_DATE  TIMESTAMP(6)
);

