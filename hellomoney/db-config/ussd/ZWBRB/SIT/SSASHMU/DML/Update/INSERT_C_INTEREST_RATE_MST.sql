DELETE FROM C_INTEREST_RATE_MST WHERE BUSINESS_ID='ZWBRB';
COMMIT;

-- insert script for table C_INTEREST_RATE_MST

INSERT INTO C_INTEREST_RATE_MST(BUSINESS_ID,PRODUCT_CD,EFFECTIVE_DTM,CCY,CHANNEL,CUSTOMER_SEGMENT,TENURE_MONTH,TENURE_DAY,AMT_SLAB_FROM,AMT_SLAB_TO,INTEREST_RATE,INTEREST_VARIANCE,PENALTY_RATE,PENALTY_VARIANCE,TENURE_TYPE) VALUES ('ZWBRB','100',SYSDATE,'USD','A','MASS',0,30,1000,9999999999999.99,3.00,null,null,null,null);

INSERT INTO C_INTEREST_RATE_MST (BUSINESS_ID,PRODUCT_CD,EFFECTIVE_DTM,CCY,CHANNEL,CUSTOMER_SEGMENT,TENURE_MONTH,TENURE_DAY,AMT_SLAB_FROM,AMT_SLAB_TO,INTEREST_RATE,INTEREST_VARIANCE,PENALTY_RATE,PENALTY_VARIANCE,TENURE_TYPE) VALUES ('ZWBRB','100',SYSDATE,'USD','A','MASS',0,60,1000,9999999999999.99,3.00,null,null,null,null);

INSERT INTO C_INTEREST_RATE_MST (BUSINESS_ID,PRODUCT_CD,EFFECTIVE_DTM,CCY,CHANNEL,CUSTOMER_SEGMENT,TENURE_MONTH,TENURE_DAY,AMT_SLAB_FROM,AMT_SLAB_TO,INTEREST_RATE,INTEREST_VARIANCE,PENALTY_RATE,PENALTY_VARIANCE,TENURE_TYPE) VALUES ('ZWBRB','100',SYSDATE,'USD','A','MASS',0,90,1000,9999999999999.99,4.00,null,null,null,null);



COMMIT;