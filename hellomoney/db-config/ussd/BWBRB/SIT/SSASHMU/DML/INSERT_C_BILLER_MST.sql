DELETE FROM C_BILLER_MST WHERE BUSINESS_ID='BWBRB';

INSERT INTO C_BILLER_MST (BUSINESS_ID,BILLER_ID,BILLER_NM,BILLER_CATEGORY_ID,BILLER_CATEGORY_NM,PRESENTMENT_FLG,REF_NO_TEXT_1,REF_NO_TEXT_2,TRANSACTION_FEE,BILL_AGGREGATOR_ID,CURRENCY,SUPPORT_CREDITCARD_FLG,ONLINE_BILLER_FLG,PAYEE_REF_FIELDS,MOBILE_DENOMINATION,SERVICE_TYPE,EXTERNAL_BILLER_ID,MIN_PAYMENT_AMOUNT,BRANCH_CODE,BILLER_ACCOUNT_NUMBER,REF_NO_KEY_1,REF_NO_KEY_2,MAX_PAYMENT_AMOUNT,BILLHOLDER_REQUIRED,PAYEE_SUPPORT,BILLER_ATTRIBUTES) VALUES ('BWBRB','ORANGETOPUPBW-2','Orange Airtime','Telephone','Telephone','N','OrangeAirtime_PhoneNumber',null,null,null,'BWP','N','Y',null,null,'TOPUP','1',1,'78','9033234','OrangeAirtime',null,99999999,'N','Y',null);

INSERT INTO C_BILLER_MST (BUSINESS_ID,BILLER_ID,BILLER_NM,BILLER_CATEGORY_ID,BILLER_CATEGORY_NM,PRESENTMENT_FLG,REF_NO_TEXT_1,REF_NO_TEXT_2,TRANSACTION_FEE,BILL_AGGREGATOR_ID,CURRENCY,SUPPORT_CREDITCARD_FLG,ONLINE_BILLER_FLG,PAYEE_REF_FIELDS,MOBILE_DENOMINATION,SERVICE_TYPE,EXTERNAL_BILLER_ID,MIN_PAYMENT_AMOUNT,BRANCH_CODE,BILLER_ACCOUNT_NUMBER,REF_NO_KEY_1,REF_NO_KEY_2,MAX_PAYMENT_AMOUNT,BILLHOLDER_REQUIRED,PAYEE_SUPPORT,BILLER_ATTRIBUTES) VALUES ('BWBRB','MASCOMTOPUPBW-2','Mascom Airtime','Telephone','Telephone','N','MascomAirtime_PhoneNumber',null,null,null,'BWP','N','Y',null,null,'TOPUP','1',1,'78','9032688','MascomAirtime',null,99999999,'N','Y',null);

INSERT INTO C_BILLER_MST (BUSINESS_ID,BILLER_ID,BILLER_NM,BILLER_CATEGORY_ID,BILLER_CATEGORY_NM,PRESENTMENT_FLG,REF_NO_TEXT_1,REF_NO_TEXT_2,TRANSACTION_FEE,BILL_AGGREGATOR_ID,CURRENCY,SUPPORT_CREDITCARD_FLG,ONLINE_BILLER_FLG,PAYEE_REF_FIELDS,MOBILE_DENOMINATION,SERVICE_TYPE,EXTERNAL_BILLER_ID,MIN_PAYMENT_AMOUNT,BRANCH_CODE,BILLER_ACCOUNT_NUMBER,REF_NO_KEY_1,REF_NO_KEY_2,MAX_PAYMENT_AMOUNT,BILLHOLDER_REQUIRED,PAYEE_SUPPORT,BILLER_ATTRIBUTES) VALUES ('BWBRB','BBBW223349-2','MultiChoice (DSTV)','Television Subscription','Television Subscription','N','Smart Card Number',null,null,null,'BWP','N','Y',null,null,null,'1',1,'01','2772939','MultiChoice(DSTV)',null,99999999,'N','Y',null);

COMMIT;