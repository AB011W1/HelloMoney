-- Unable to Render DDL with DBMS_METADATA using internal generator.

CREATE TABLE C_BANKROUTING_MST
(
  BANKCODE VARCHAR2(10 BYTE) NOT NULL,
  BANKNAME VARCHAR2(80 BYTE),
  BRANCHCODE VARCHAR2(10 BYTE) NOT NULL,
  BRANCHNAME VARCHAR2(80 BYTE),
  BUSINESS_ID VARCHAR2(10 BYTE) NOT NULL,
  BANK_ROUTING_CODE VARCHAR2(20 BYTE),
  CITY_NAME VARCHAR2(80 BYTE)
, PRIMARY KEY
  (
    BANKCODE,
    BRANCHCODE,
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

