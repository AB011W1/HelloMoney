DELETE FROM C_PRODUCT_MST WHERE BUSINESS_ID='ZWBRB';
COMMIT;
-- insert script for table C_PRODUCT_MST

INSERT INTO C_PRODUCT_MST (PRODUCT_CD,BUSINESS_ID,PRODUCT_DESC,PRODUCT_GROUP,PRODUCT_SUBGROUP,SOURCE_SYSTEM_ID,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,CURRENCY_CD,TENURE_TYP,MIN_AMOUNT,MAX_AMOUNT,ESAVER_IND,INTERNET_ENABLED,INT_PAYOUT_FRQ,INT_COMPD_FRQ,SUPPORT_CHEQUE) VALUES ('100','ZWBRB','Classic Fixed Deposits','TMD',null,null,'BIRBatch',SYSDATE,'BIRBatch',SYSDATE,'N','USD',null,null,null,'Y','N',null,null,'N');

COMMIT;