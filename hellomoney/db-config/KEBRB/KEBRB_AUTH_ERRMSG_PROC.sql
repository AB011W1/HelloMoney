BEGIN
   AUTH_ERRMSG_PROC_SSA('KEBRB');
END;
/
------------ CR to update error messages ------------------
DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00114';
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','FATAL','ATH00114',null,null,null,null,null,'AUTH');

DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00114';
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Second authentication type - SQA is invalid','MB','KEBRB','ATH00114');

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00115';
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','FATAL','ATH00115',null,null,null,null,null,'AUTH');

DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00115';
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Session expired,please login again','MB','KEBRB','ATH00115');

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00117';
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','ERROR','ATH00117',null,null,null,null,null,'AUTH');

DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00117';
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter your Username.','MB','KEBRB','ATH00117');

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00118';
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','ERROR','ATH00118',null,null,null,null,null,'AUTH');

DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND MESSAGE_KEY='ATH00118';
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Please enter your Password.','MB','KEBRB','ATH00118');

------------------------------------------------------------
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','FATAL','ATH00119',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your Mobile banking access has been blocked. Please contact our Contact Centre on +254 20 3900000/ +254 722 130120, +254 733 130120 for assistance to activate your account.','MB','KEBRB','ATH00119');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','FATAL','ATH00121',null,null,null,null,null,'AUTH');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Your account has not been activated. Please contact our Contact Centre on +254 20 3900000/ +254 722 130120, +254 733 130120 for assistance to activate your account.','MB','KEBRB','ATH00121');

COMMIT;