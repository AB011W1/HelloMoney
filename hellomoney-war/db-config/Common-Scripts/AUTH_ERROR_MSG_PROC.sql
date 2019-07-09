CREATE OR REPLACE
PROCEDURE AUTH_ERRMSG_PROC_SSA( var_business_id IN  VARCHAR2)
IS

BEGIN

---------------- AUTHENTICATION  - C_MESSAGE_MST ---
DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY LIKE 'ATH0%';

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00102',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00104',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00108',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00109',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00110',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ATH00111',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ATH00111_REL1',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00112',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00113',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ATH00114',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ATH00115',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00117',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'ERROR','ATH00118',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ATH00120',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ATH00122',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'WARN','ATH00123',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB',var_business_id,'FATAL','ATH00124',null,null,null,null,null,'AUTH');

COMMIT;
---------------- AUTHENTICATION  - C_MESSAGE_RES_MST ---

DELETE FROM C_MESSAGE_RES_MST  WHERE SYSTEM_ID='MB' AND BUSINESS_ID=var_business_id AND MESSAGE_KEY  LIKE 'ATH0%';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Incorrect security answer, please check and try again.','MB',var_business_id,'ATH00102');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Wrong Authentication Code, You Have Only  {0} times to try, Otherwise, You Will be Locked and Logged out, Please Try Again Carefully','MB',var_business_id,'ATH00104');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your registered mobile number is not valid, you cannot use mobile banking.','MB',var_business_id,'ATH00108');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter your One Time Password','MB',var_business_id,'ATH00109');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter your security answer','MB',var_business_id,'ATH00110');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','OTP you entered expired, request to send new OTP','MB',var_business_id,'ATH00111');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','OTP you entered has expired. Please login again','MB',var_business_id,'ATH00111_REL1');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The Username / Password that you have entered is not valid. Please check and try again','MB',var_business_id,'ATH00112');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','The Username / Password that you have entered is not valid. Please check and try again','MB',var_business_id,'ATH00113');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Second authentication type invalid or not specified','MB',var_business_id,'ATH00114');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Session expired, other session has been opened with same login. Maximum one active session per user is allowed','MB',var_business_id,'ATH00115');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter login id','MB',var_business_id,'ATH00117');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter password','MB',var_business_id,'ATH00118');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','You do not have a mobile number registered, you cannot use mobile banking.','MB',var_business_id,'ATH00120');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Customer number does not exist.','MB',var_business_id,'ATH00122');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your login password will expire in {0} days, Please change it using our website.','MB',var_business_id,'ATH00123');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your login password has expired. Please change it using our web-site.','MB',var_business_id,'ATH00124');

COMMIT;

END;
/