-- Unable to Render DDL with DBMS_METADATA using internal generator.
CREATE TABLE S_TXN_CUT_OFF_MST
(
  BUSINESS_ID VARCHAR2(10 BYTE) NOT NULL,
  SYSTEM_ID VARCHAR2(10 BYTE) NOT NULL,
  ACTIVITY_ID VARCHAR2(40 BYTE) NOT NULL,
  CUT_OFF_TIME TIMESTAMP(6)
, CONSTRAINT S_TXN_CUT_OFF_MST_PK PRIMARY KEY
  (
    BUSINESS_ID,
    SYSTEM_ID,
    ACTIVITY_ID
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

