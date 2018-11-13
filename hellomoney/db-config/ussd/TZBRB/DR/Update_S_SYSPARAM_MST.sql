------------- BEM ENDPOINTS - PROD  ------------------------


UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'FinancialTransactionProcessing_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'IndividualCustomerManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'ProblemManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'PinManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'HelloMoneyCustomerManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'AccountManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'AccountMandateManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'AccountReporting_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'BeneficiaryManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'CheckBookManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://22.126.66.47:443/tcvm'
WHERE PARAM_ID = 'NotificationService_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'TZBRB';

------------- BUSINESS_DATE not needed in PROD  ------------------------
delete from S_SYSPARAM_MST where business_id = 'TZBRB' and system_id = 'UB' and param_id = 'BUSINESS_DATE' and activity_id = 'COMMON';

COMMIT;