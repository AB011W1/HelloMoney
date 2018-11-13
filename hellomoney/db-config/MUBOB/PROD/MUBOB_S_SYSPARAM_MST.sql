------------- TERMS OF USE -------------------
DELETE  FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND PARAM_ID='TERMS_OF_USE_VERSION_NO';

INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,ACTIVITY_ID,SYSTEM_MAINTAIN) VALUES ('MUBOB','MB','TERMS_OF_USE_VERSION_NO','1.0','COMMON','Y');

------------- GECODING ------------------------
DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND PARAM_ID='GECODING' AND ACTIVITY_ID='GECODING';

INSERT INTO  S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM) VALUES ('MUBOB','MB','GECODING','20','','GECODING','Y','','','','','','','','','');

------------- Bank Draft ------------------------
DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND PARAM_ID='FT_DELIVERY_TYPE_LIST' AND ACTIVITY_ID='PMT_FT_BKD_PAYEE';

INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBOB','MB','FT_DELIVERY_TYPE_LIST','BRN',null,'PMT_FT_BKD_PAYEE','Y','INPUT_TEXT',null,null,null,null,null,'Delivery type for bank draft',null,to_timestamp('09-MAY-12 19.03.58.000000000','DD-MON-RR HH24.MI.SS.FF'));

DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND PARAM_ID='FT_DELIVERY_TYPE_LIST' AND ACTIVITY_ID='PMT_FT_MRC_PAYEE';

INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBOB','MB','FT_DELIVERY_TYPE_LIST','BRN',null,'PMT_FT_MRC_PAYEE','Y','INPUT_TEXT',null,null,null,null,null,'Delivery type for bank draft',null,to_timestamp('09-MAY-12 19.03.58.000000000','DD-MON-RR HH24.MI.SS.FF'));


------------ Purchase Manager Cheque -----------------//TODO: COMMENT on UAT or above env, if it's configured for IB
DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND PARAM_ID='PMT_FT_BKD_SUPPORT_CURRENCY' AND ACTIVITY_ID='PMT_FT_BKD_ONETIME';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBOB','MB','PMT_FT_BKD_SUPPORT_CURRENCY','USD,GBP,EUR,AUD',null,'PMT_FT_BKD_ONETIME','Y','DROP_DOWN',null,null,null,null,null,'Supported currency for bank draft request',null,to_timestamp('03-APR-12 13.46.26.000000000','DD-MON-RR HH24.MI.SS.FF'));

DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND PARAM_ID='PMT_FT_BKD_SUPPORT_CURRENCY' AND ACTIVITY_ID='PMT_FT_MRC_ONETIME';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBOB','MB','PMT_FT_BKD_SUPPORT_CURRENCY','MUR',null,'PMT_FT_MRC_ONETIME','Y','DROP_DOWN',null,null,null,null,null,'Supported currency for bank draft request',null,to_timestamp('07-MAY-12 14.29.13.000000000','DD-MON-RR HH24.MI.SS.FF'));

----------- Jail Broken
DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND PARAM_ID='JAIL_BREAK_ALLOWD_FLAG' AND ACTIVITY_ID='COMMON';
INSERT INTO S_SYSPARAM_MST (BUSINESS_ID,SYSTEM_ID,PARAM_ID,PARAM_VALUE,PARAM_COMPONENT_KEY,ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM) values ('MUBOB','MB','JAIL_BREAK_ALLOWD_FLAG','N','NoLength','COMMON','N','INPUT_TEXT',null,null,null,null,null,'Application Integrity Flag',null,to_timestamp('07-MAY-12 14.29.13.000000000','DD-MON-RR HH24.MI.SS.FF'));

------------- BEM ENDPOINTS - PRODUCTION  ------------------------

---https://35.98.146.130/bem/bxx5_gateway
----------------------------------------------------------------------------------------------------------------

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'AccountMandateManagement_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'AccountReporting_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'AlertManagement_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'BeneficiaryManagement_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'CheckBookManagement_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'CheckTransactionManagement_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'FinancialTransactionProcessing_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'FinancialTransactionCardManagment_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'IndividualCustomerManagement_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.98.146.130/bem/bxx5_gateway'
WHERE PARAM_ID = 'AccountManagement_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.49.26.70/authService/services/AuthenticationServiceSOAP'
WHERE PARAM_ID = 'AuthenticationServiceSSC_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.49.26.70/OTPAuthentication/services/AuthenticationService'
WHERE PARAM_ID = 'AuthenticationService_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://35.49.26.70/OTPAuthentication/services/AuthenticationMgmtService'
WHERE PARAM_ID = 'AuthenticationMgmtService_EndPoint'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = '5.1.0'
WHERE PARAM_ID = 'SERVICE_HEADER_SERVICE_VERSION_NO'
AND SYSTEM_ID = 'MB'
AND BUSINESS_ID = 'MUBOB';

COMMIT;


