BEGIN
   OTH_ERRMSG_PROC_SSA('EGBRB');
END;
/


INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Temporary out of service. Please contact our Call Centre at 04 428600 within Egypt or 00971 800 22725297 from outside Egypt.','MB','EGBRB','BMB99999');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','EGBRB','INFO','BMB99999',null,null,null,null,null,'UNKNOWN');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please contact our Call Centre at 04 428600 within Egypt or 00971 800 22725297 from outside Egypt.','MB','EGBRB','BMB90004');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','EGBRB','INFO','BMB90004',null,null,null,null,null,NULL);

COMMIT;