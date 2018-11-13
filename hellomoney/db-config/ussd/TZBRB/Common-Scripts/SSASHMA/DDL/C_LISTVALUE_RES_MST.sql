-- Unable to Render DDL with DBMS_METADATA using internal generator.
CREATE TABLE C_LISTVALUE_RES_MST
(
  SYSTEM_ID VARCHAR2(10 BYTE) NOT NULL,
  BUSINESS_ID VARCHAR2(10 BYTE) NOT NULL,
  GROUP_ID VARCHAR2(30 BYTE) NOT NULL,
  LIST_VALUE_KEY VARCHAR2(30 BYTE) NOT NULL,
  LANGUAGE_ID VARCHAR2(5 BYTE) NOT NULL,
  VALUE VARCHAR2(200 BYTE),
  LIST_VALUE_ORDER NUMBER(10, 0)
, PRIMARY KEY
  (
    SYSTEM_ID,
    BUSINESS_ID,
    GROUP_ID,
    LIST_VALUE_KEY,
    LANGUAGE_ID
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

