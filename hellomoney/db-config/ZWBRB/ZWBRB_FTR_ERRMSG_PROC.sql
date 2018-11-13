BEGIN
   FTR_ERRMSG_PROC_SSA('ZWBRB');
END;
/

INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZWBRB','INFO','FTR00507',null,null,null,null,null,null);
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The account selected cannot be used for the transaction. Please contact our Customer Service Centre at 250 579 within Zimbabwe or 00263 4 250 579 from outside Zimbabwe for assistance.','MB','ZWBRB','FTR00507');

COMMIT;