
CREATE OR REPLACE
PROCEDURE OTH_ERRMSG_PROC_SSA( var_business_id IN  VARCHAR2)
IS

BEGIN


DELETE FROM C_MESSAGE_RES_MST  WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'BMB%';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The transfer amount must be less than or equal to your transaction limit {0} {1}. Please enter correct amount.','MB',var_business_id,'BMB90002');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The transfer amount must be less than or equal to your Daily transaction limit {0} {1}. Please enter correct amount.','MB',var_business_id,'BMB90003');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Transaction is currently not available due to maintenance. Kindly try after sometime apologies for the inconvenience.','MB',var_business_id,'BMB90005');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','No branches found for search criteria.','MB',var_business_id,'BMB09901');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please provide a city name to search.','MB',var_business_id,'BMB09902');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please provide a branch name to search.','MB',var_business_id,'BMB09903');

----jail broken
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','This application  version is no longer supported on your platform, please check your application and OS version setting or contact bank''s call center.','MB',var_business_id,'BMB09910');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','You are not able to use application. Device is Jail Broken or Rooted.','MB',var_business_id,'BMB09911');


COMMIT;

--------

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'BMB%';

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','BMB90002',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','BMB90003',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','BMB90005',null,null,null,null,null,NULL);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','BMB09901',null,null,null,null,null,'BMB');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','BMB09902',null,null,null,null,null,'BMB');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','BMB09903',null,null,null,null,null,'BMB');

----jail broken
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','BMB09910',null,null,null,null,null,'BMB');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','BMB09911',null,null,null,null,null,'BMB');

COMMIT;


-----------GEO CODING---

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'GAL0%';

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','GAL0801',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','GAL0802',null,null,null,null,null,null);
COMMIT;

DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'GAL0%';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter Latitude. ','MB',var_business_id,'GAL0801');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter Longitude.','MB',var_business_id,'GAL0802');

COMMIT;


DELETE FROM C_MESSAGE_RES_MST  WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY  LIKE 'CHQ0%';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter account number.','MB',var_business_id,'CHQ00900');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter cheque book type.','MB',var_business_id,'CHQ00901');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter cheque book size.','MB',var_business_id,'CHQ00902');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter transaction reference number.','MB',var_business_id,'CHQ00903');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Cheque book type is invalid.','MB',var_business_id,'CHQ00904');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Cheque book size is invalid.','MB',var_business_id,'CHQ00905');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Trasaction reference number is invalid.','MB',var_business_id,'CHQ00906');

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'CHQ0%';

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','CHQ00900',null,null,null,null,null,'CHEQUE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','CHQ00901',null,null,null,null,null,'CHEQUE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','CHQ00902',null,null,null,null,null,'CHEQUE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','CHQ00903',null,null,null,null,null,'CHEQUE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','CHQ00904',null,null,null,null,null,'CHEQUE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','CHQ00905',null,null,null,null,null,'CHEQUE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','CHQ00906',null,null,null,null,null,'CHEQUE');

------------ SCRIPT TABLE FOR OTP ERROR MESSAGE - REL-2 -------------
DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id  AND SCRIPT_KEY='otpFooter_REL2';

INSERT INTO  C_SCRIPT_RES_MST (LANGUAGE_ID, SEQUENCE_NO, SCRIPT_VALUE, MAKER_ID, CHECKER_ID, STATUS, LAST_MODIFIED, LAST_AUTHORIZED, ACTION, DELETED_FLAG, AUTHORIZED_INDICATOR,SCRIPT_KEY, SYSTEM_ID, BUSINESS_ID) VALUES ('EN','1','If you don''t get the SMS within the next 3 minutes, please click ''Resend OTP''.','','','','','','','','','otpFooter_REL2','MB',var_business_id);


DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND CATEGORY_ID='OTP' AND SCRIPT_KEY='otpFooter_REL2';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG, AUTHORIZED_INDICATOR) VALUES ('MB',var_business_id,'OTP','otpFooter_REL2','','','','','','','','');


COMMIT;



---------

END;
/