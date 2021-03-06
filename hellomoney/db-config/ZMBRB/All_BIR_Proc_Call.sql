BEGIN
  BIR_Procedure_SSA('ZMBRB');
END;
/


BEGIN
   ACCT_ERRMSG_PROC_SSA('ZMBRB');
END;
/


BEGIN
   AUTH_ERRMSG_PROC_SSA('ZMBRB');
END;
/

INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','FATAL','ATH00119',null,null,null,null,null,'AUTH');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your Mobile banking access has been blocked. Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.','MB','ZMBRB','ATH00119');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','FATAL','ATH00121',null,null,null,null,null,'AUTH');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your account has not been activated. Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.','MB','ZMBRB','ATH00121');


BEGIN
   BP_ERRMSG_PROC_SSA('ZMBRB');
END;
/

BEGIN
   FTR_ERRMSG_PROC_SSA('ZMBRB');
END;
/

INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','FATAL','FTR00507',null,null,null,null,null,null);
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The account selected cannot be used for the transaction. Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.','MB','ZMBRB','FTR00507');


BEGIN
   OTH_ERRMSG_PROC_SSA('ZMBRB');
END;
/


INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Temporary out of service. Please contact our Customer Service Centre at 250 579 within Zimbabwe or 00263 4 250 579 from outside Zimbabwe for assistance.','MB','ZMBRB','BMB99999');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','FATAL','BMB99999',null,null,null,null,null,'UNKNOWN');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please contact our Customer Service Centre at 250 579 within Zimbabwe or 00263 4 250 579 from outside Zimbabwe for assistance.','MB','ZMBRB','BMB90004');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','FATAL','BMB90004',null,null,null,null,null,NULL);

COMMIT;




