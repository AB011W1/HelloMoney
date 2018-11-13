BEGIN
   OTH_ERRMSG_PROC_SSA('BWBRB');
END;
/


Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Temporary out of service. Please contact our Customer Service Centre at 0800 600 444 within Botswana or (+267) 315 9575 from outside Botswana for assistance. ','MB','BWBRB','BMB99999');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','BWBRB','FATAL','BMB99999',null,null,null,null,null,'UNKNOWN');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please contact our Customer Service Centre at 0800 600 444 within Botswana or (+267) 315 9575 from outside Botswana for assistance.','MB','BWBRB','BMB90004');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','BWBRB','FATAL','BMB90004',null,null,null,null,null,NULL);

COMMIT;