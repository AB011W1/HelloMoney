DELETE  FROM C_BIZ_FUNC_MMAP where BUSINESS_ID='TZBRB' and SYSTEM_ID='UB';
DELETE  FROM C_LISTVALUE_MST where BUSINESS_ID='TZBRB' and SYSTEM_ID='UB';
DELETE  FROM C_LISTVALUE_RES_MST where BUSINESS_ID='TZBRB' and SYSTEM_ID='UB';
DELETE  FROM C_COMPONENT_MST where business_id='TZBRB' and system_id='UB';
DELETE  FROM C_COMPONENT_RES_MST where business_id='TZBRB' and system_id='UB';
DELETE  FROM S_FUNCTION_CONFIG where business_id='TZBRB' and system_id='UB';
DELETE  FROM C_FUNCTION_MST  WHERE SYSTEM_ID='UB';

COMMIT;
