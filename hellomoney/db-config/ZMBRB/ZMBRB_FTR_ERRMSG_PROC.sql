BEGIN
   FTR_ERRMSG_PROC_SSA('ZMBRB');
END;
/

INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','INFO','FTR00507',null,null,null,null,null,null);
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The account selected cannot be used for the transaction. Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.','MB','ZMBRB','FTR00507');

COMMIT;