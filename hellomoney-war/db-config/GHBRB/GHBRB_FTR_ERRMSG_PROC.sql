BEGIN
   FTR_ERRMSG_PROC_SSA('GHBRB');
END;
/

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','FTR00507',null,null,null,null,null,null);
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The account selected cannot be used for the transaction. Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','FTR00507');

COMMIT;