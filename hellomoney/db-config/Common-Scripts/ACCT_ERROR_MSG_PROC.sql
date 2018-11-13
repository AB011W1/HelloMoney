CREATE OR REPLACE
PROCEDURE ACCT_ERRMSG_PROC_SSA( var_business_id IN  VARCHAR2)
IS

BEGIN

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'ASM0%';
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ASM00200',null,null,null,null,null,null);
COMMIT;

DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'ASM0%';
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Sorry, you do not have any account available.','MB',var_business_id,'ASM00200');
COMMIT;

-----------	ACCOUNT DETAILS	-	C_MESSAGE_MST	---

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'ADT0%';

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ADT00300',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ADT00301',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ADT00302',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ADT00303',null,null,null,null,null,null);

COMMIT;

DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'ADT0%';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Account Number field is Blank, Please enter account number. ','MB',var_business_id,'ADT00300');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Account number does not exist.','MB',var_business_id,'ADT00301');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are not able to display the details requested. Please Contact Bank Customer Service For Further Assistance','MB',var_business_id,'ADT00302');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','There is no Account available to perform the selected function.','MB',var_business_id,'ADT00303');

COMMIT;
------------

----------ACCOUNT ACTIVITY---
DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'AAC00%';

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','AAC00400',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','AAC00401',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','AAC00402',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','AAC00403',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','AAC00405',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','AAC00406',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','AAC00407',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','AAC00408',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','AAC00409',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','AAC00410',null,null,null,null,null,null);
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','AAC00411',null,null,null,null,null,null);

COMMIT;
------

DELETE FROM C_MESSAGE_res_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'AAC00%';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Account Number field is Blank, Please enter account number. ','MB',var_business_id,'AAC00400');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Account number does not exist.','MB',var_business_id,'AAC00401');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Transaction Days field is Blank. Please enter number of days','MB',var_business_id,'AAC00402');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Invalid Transaction Days','MB',var_business_id,'AAC00403');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Card Number field is Blank, Please enter account number. ','MB',var_business_id,'AAC00405');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Card Number does not exist.','MB',var_business_id,'AAC00406');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are not able to display the details requested. Please Contact Bank Customer Service For Further Assistance.','MB',var_business_id,'AAC00407');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','No statemented transaction is found for this month.','MB',var_business_id,'AAC00408');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We regret to inform you that we are currently unable to display your account information. Please try again later.','MB',var_business_id,'AAC00409');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We regret to inform you that we are currently unable to display your account information. Please try again later.','MB',var_business_id,'AAC00410');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We regret to inform you that we are currently unable to display your account information. Please try again later.','MB',var_business_id,'AAC00411');

COMMIT;
-------

END;
/