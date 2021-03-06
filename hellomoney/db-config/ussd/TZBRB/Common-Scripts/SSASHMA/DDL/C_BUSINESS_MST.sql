-- Unable to Render DDL with DBMS_METADATA using internal generator.
CREATE TABLE C_BUSINESS_MST
(
  BUSINESS_ID VARCHAR2(10 BYTE) NOT NULL,
  NAME VARCHAR2(100 BYTE),
  ISO_COUNTRY_CD VARCHAR2(3 BYTE),
  DEFAULT_LANG_KEY VARCHAR2(5 BYTE),
  LCY_CD VARCHAR2(3 BYTE),
  DATE_FMT_KEY VARCHAR2(5 BYTE),
  AMOUNT_FMT_KEY VARCHAR2(5 BYTE),
  REGIONAL_FLG CHAR(1 BYTE),
  MODIFIED_BY VARCHAR2(20 BYTE),
  MODIFIED_DTM TIMESTAMP(6),
  AUTHORIZED_BY VARCHAR2(20 BYTE),
  AUTHORIZED_DTM TIMESTAMP(6),
  DELETE_FLG CHAR(1 BYTE),
  TIMEZONE_OFFSET NUMBER(4, 2),
  CARDONLY_FLG CHAR(1 BYTE) DEFAULT 'N',
  RATE_PRECISION VARCHAR2(20 BYTE),
  CREATED_DTM TIMESTAMP(6),
  CREATED_BY VARCHAR2(20 BYTE),
  CASE_RETENTION_MONTH VARCHAR2(10 BYTE),
  CAMPAIGN_RETENTION_MONTH VARCHAR2(10 BYTE),
  WAVE_RETENTION_PERIOD VARCHAR2(10 BYTE),
  PAST_OPPTY_RETENTION_PERIOD VARCHAR2(10 BYTE)
, PRIMARY KEY
  (
    BUSINESS_ID
  )
)
PARTITION BY LIST(BUSINESS_ID)
(
 PARTITION P_TZBRB VALUES('TZBRB')
    TABLESPACE "TZBRB"
    LOGGING 
    PCTFREE 10
    INITRANS 1
    MAXTRANS 255
    STORAGE
    (
      INITIAL 64K
      NEXT 64K
      MINEXTENTS 1
      MAXEXTENTS 2147483645
      PCTINCREASE 0
      BUFFER_POOL DEFAULT
    )
    NOCOMPRESS
, PARTITION P_UGBRB VALUES('UGBRB')
    TABLESPACE "UGBRB"
    LOGGING 
    PCTFREE 10
    INITRANS 1
    MAXTRANS 255
    STORAGE
    (
      INITIAL 64K
      MINEXTENTS 1
      MAXEXTENTS 2147483645
      BUFFER_POOL DEFAULT
    )
    NOCOMPRESS
)
;

