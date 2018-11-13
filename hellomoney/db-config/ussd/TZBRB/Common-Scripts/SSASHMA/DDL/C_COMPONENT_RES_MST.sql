-- Unable to Render DDL with DBMS_METADATA using internal generator.

CREATE TABLE C_COMPONENT_RES_MST
(
  LANGUAGE_ID VARCHAR2(5 BYTE) NOT NULL,
  LABEL_VALUE VARCHAR2(400 BYTE),
  TOOL_TIP VARCHAR2(200 BYTE),
  HELP_TEXT VARCHAR2(200 BYTE),
  LENGTH NUMBER(10, 0),
  SCREEN_ID VARCHAR2(30 BYTE) NOT NULL,
  SYSTEM_ID VARCHAR2(10 BYTE) NOT NULL,
  BUSINESS_ID VARCHAR2(10 BYTE) NOT NULL,
  COMPONENT_KEY VARCHAR2(40 BYTE) NOT NULL
, PRIMARY KEY
  (
    SCREEN_ID,
    SYSTEM_ID,
    BUSINESS_ID,
    COMPONENT_KEY,
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

