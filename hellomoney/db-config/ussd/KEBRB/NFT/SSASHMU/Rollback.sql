DELETE FROM C_BANKROUTING_MST WHERE BUSINESS_ID = 'KEBRB';
DELETE FROM C_BILLER_MST WHERE BUSINESS_ID = 'KEBRB';
DELETE FROM C_BUSINESS_MST WHERE BUSINESS_ID = 'KEBRB';
DELETE FROM C_COMPONENT_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM C_COMPONENT_RES_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM C_FORMATTER_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM C_LISTVALUE_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM C_LISTVALUE_RES_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM S_FUNCTION_CONFIG WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM S_LIMIT_CUST_TOTAL WHERE BUSINESS_ID = 'KEBRB';
DELETE FROM S_SES_SUMMARY_HST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM S_SYSPARAM_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM S_TXN_CUT_OFF_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID = 'UB' and BUSINESS_ID = 'KEBRB';
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE BUSINESS_ID='KEBRB';

 COMMIT;
 
