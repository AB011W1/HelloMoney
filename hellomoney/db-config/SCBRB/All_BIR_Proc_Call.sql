BEGIN
  BIR_Procedure_SSA('SCBRB');
END;
/



BEGIN
   ACCT_ERRMSG_PROC_SSA('SCBRB');
END;
/



BEGIN
   AUTH_ERRMSG_PROC_SSA('SCBRB');
END;
/


INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','ATH00119',null,null,null,null,null,'AUTH');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your Mobile banking access has been blocked. Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','ATH00119');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','ATH00121',null,null,null,null,null,'AUTH');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your account has not been activated. Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','ATH00121');

COMMIT;


BEGIN
   BP_ERRMSG_PROC_SSA('SCBRB');
END;
/


BEGIN
   FTR_ERRMSG_PROC_SSA('SCBRB');
END;
/

INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','FTR00507',null,null,null,null,null,null);
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The account selected cannot be used for the transaction. Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','FTR00507');

COMMIT;


BEGIN
   OTH_ERRMSG_PROC_SSA('SCBRB');
END;
/


INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Temporary out of service. Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','BMB99999');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','BMB99999',null,null,null,null,null,'UNKNOWN');
INSERT INTO	C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','BMB90004');
INSERT INTO	C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','BMB90004',null,null,null,null,null,NULL);

COMMIT;
