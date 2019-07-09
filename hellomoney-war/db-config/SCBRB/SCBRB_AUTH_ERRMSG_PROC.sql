BEGIN
   AUTH_ERRMSG_PROC_SSA('SCBRB');
END;
/


Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','ATH00119',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your Mobile banking access has been blocked. Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','ATH00119');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','ATH00121',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your account has not been activated. Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','ATH00121');

COMMIT;