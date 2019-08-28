BEGIN
  BIR_Procedure_SSA('GHBRB');
END;
/



BEGIN
   ACCT_ERRMSG_PROC_SSA('GHBRB');
END;
/


BEGIN
   AUTH_ERRMSG_PROC_SSA('GHBRB');
END;
/


Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','ATH00119',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your Mobile banking access has been blocked. Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','ATH00119');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','ATH00121',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your account has not been activated. Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','ATH00121');

COMMIT;


BEGIN
   BP_ERRMSG_PROC_SSA('GHBRB');
END;
/


BEGIN
   FTR_ERRMSG_PROC_SSA('GHBRB');
END;
/

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','FTR00507',null,null,null,null,null,null);
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The account selected cannot be used for the transaction. Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','FTR00507');

COMMIT;


BEGIN
   OTH_ERRMSG_PROC_SSA('GHBRB');
END;
/


Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Temporary out of service. Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','BMB99999');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','BMB99999',null,null,null,null,null,'UNKNOWN');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please contact our Customer Service Centre at 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','BMB90004');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','FATAL','BMB90004',null,null,null,null,null,NULL);


COMMIT;
