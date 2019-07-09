------------- BEM ENDPOINTS - SIT  ------------------------

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'FinancialTransactionProcessing_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'IndividualCustomerManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'ProblemManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'PinManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'HelloMoneyCustomerManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'AccountManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'AccountMandateManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'AccountReporting_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'BeneficiaryManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'CheckBookManagement_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

UPDATE S_SYSPARAM_MST SET PARAM_VALUE = 'https://gbrdsr000000192.intranet.barcapint.com:8802/bem/bxx5_gateway'
WHERE PARAM_ID = 'NotificationService_EndPoint'
AND SYSTEM_ID = 'UB'
AND BUSINESS_ID = 'ZWBRB';

COMMIT;