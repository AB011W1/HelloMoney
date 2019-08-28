BEGIN
   AUTH_ERRMSG_PROC_SSA('TZBRB');
END;
/


Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','TZBRB','FATAL','ATH00119',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your Mobile banking access has been blocked. Please call our 24/7 Contact Centre at 0774 700 703 or 0774 700 708 within Tanzania or (+255) 774 700 703 or (+255) 774 700 708 from outside Tanzania for assistance.','MB','TZBRB','ATH00119');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','TZBRB','FATAL','ATH00121',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your account has not been activated. Please call our 24/7 Contact Centre at 0774 700 703 or 0774 700 708 within Tanzania or (+255) 774 700 703 or (+255) 774 700 708 from outside Tanzania for assistance.','MB','TZBRB','ATH00121');

COMMIT;