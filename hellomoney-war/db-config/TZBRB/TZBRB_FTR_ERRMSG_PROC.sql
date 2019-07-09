BEGIN
   FTR_ERRMSG_PROC_SSA('TZBRB');
END;
/

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','TZBRB','FATAL','FTR00507',null,null,null,null,null,null);
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The account selected cannot be used for the transaction. Please call our 24/7 Contact Centre at 0774 700 703 or 0774 700 708 within Tanzania or (+255) 774 700 703 or (+255) 774 700 708 from outside Tanzania for assistance.','MB','TZBRB','FTR00507');

COMMIT;