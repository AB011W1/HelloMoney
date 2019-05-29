
------- AUTH END POINT FOR STUB ---
DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'AuthService_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBRB','MB','AuthService_EndPoint','http://localhost:8080/bemstub/resources/AuthenticationChallenge.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'TransService_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBRB','MB','TransService_EndPoint','http://localhost:8080/bemstub/resources/TransmissionAuth.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'PostService_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBRB','MB','PostService_EndPoint','http://localhost:8080/bemstub/resources/PostServiceAuth.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

------- For own ft, card ft, bill payment --------
DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'FinancialTransProcess_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('ZMBRB','MB','FinancialTransProcess_EndPoint','http://localhost:8080/bemstub/resources/FinancialTransProcess.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

------------ At a glance ---------------
DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'RetrieveIndividualCustAcctList_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBRB','MB','RetrieveIndividualCustAcctList_EndPoint','http://localhost:8080/bemstub/resources/RetrieveIndividualCustAcctList.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

------------Casa details ---------------
DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'CasaDetails_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBRB','MB','CasaDetails_EndPoint','http://localhost:8080/bemstub/resources/CasaDetails.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

------------ Casa Activity ---------------
DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'CasaAccountActivity_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBRB','MB','CasaAccountActivity_EndPoint','http://localhost:8080/bemstub/resources/CasaAccountActivity.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

---------- Bill payment---
DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'BeneficiaryMgmt_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('KEBRB','MB','BeneficiaryMgmt_EndPoint','http://localhost:8080/bemstub/resources/RetrieveIndividualCustBeneficiaryList.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'BeneficiaryMgmtDetails_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('KEBRB','MB','BeneficiaryMgmtDetails_EndPoint','http://localhost:8080/bemstub/resources/RetrieveIndividualBeneficiaryDetails.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

------ CREDIT CARD DETAILS ----

DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'CreditCardDetails_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MZBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MZBRB','MB','CreditCardDetails_EndPoint','http://localhost:8080/bemstub/resources/RetrieveCardDetails.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID = 'IndividualCustomerManagement_EndPoint' AND SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBRB','MB','IndividualCustomerManagement_EndPoint','http://localhost:8080/bemstub/resources/RetrieveIndividualCustByCCNumberRes.txt','Acct_Mgmt_EP','COMMON','Y','INPUT_TEXT',null,null,null,null,null,'MW service end point',null,null);

---<MobilePhone>123-54804410</MobilePhone>  for the format i.e. ^(((\d{1,3})-)(\d{5,11}))$

---- Update Mobile Format ----
DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND PARAM_ID='OTP_MOBILE_FORMAT';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM) values ('MUBRB', 'MB', 'OTP_MOBILE_FORMAT', '^(((\d{1,11})-)(\d{1,11}))$', 'OTP_MOBILE_FORMAT', 'COMMON', 'N', 'INPUT_TEXT', null, null, 'BIR', to_timestamp_tz('11-10-25 04:33:44+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'), null, 'Used to validate mobile number for SMS OTP', null, null);

COMMIT;

COMMIT;
