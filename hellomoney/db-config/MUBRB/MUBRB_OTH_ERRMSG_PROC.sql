BEGIN
   OTH_ERRMSG_PROC_SSA('MUBRB');
END;
/


INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Temporary out of service. Please contact our Customer Service Centre at 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBRB','BMB99999');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBRB','INFO','BMB99999',null,null,null,null,null,'UNKNOWN');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please contact our Customer Service Centre at 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBRB','BMB90004');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBRB','INFO','BMB90004',null,null,null,null,null,NULL);

COMMIT;