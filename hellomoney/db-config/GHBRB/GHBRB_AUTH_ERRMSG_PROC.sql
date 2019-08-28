BEGIN
   AUTH_ERRMSG_PROC_SSA('GHBRB');
END;
/


Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','ATH00119',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your Mobile banking access has been blocked. Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','ATH00119');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','ATH00121',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your account has not been activated. Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','ATH00121');

COMMIT;