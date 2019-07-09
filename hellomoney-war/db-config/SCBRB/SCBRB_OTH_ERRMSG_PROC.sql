BEGIN
   OTH_ERRMSG_PROC_SSA('SCBRB');
END;
/


Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Temporary out of service. Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','BMB99999');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','BMB99999',null,null,null,null,null,'UNKNOWN');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please contact our Customer Service Centre at {Domestic Customer Center Number} within Seychelles or {Overseas Customer Center Number} from outside Seychelles for assistance.','MB','SCBRB','BMB90004');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','FATAL','BMB90004',null,null,null,null,null,NULL);

COMMIT;