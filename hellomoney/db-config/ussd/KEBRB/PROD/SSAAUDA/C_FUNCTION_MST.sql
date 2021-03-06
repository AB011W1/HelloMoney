------------ C_FUNCTION_MST --------
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_FX_RATE' AND SYSTEM_ID='UB';
--Edit Beneficiary
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_UPD_EXTERNAL' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_UPD_INTERNAL' AND SYSTEM_ID='UB';
--Credit Card
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='ACT_CCD_UNBILLED_TRANS' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='ACT_CCD_STATEMENT_TRANS' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='ACT_CREDIT_CARD_DETAIL' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_FT_CARD_PAYMENT_ONETIME' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_FT_CARD_PAYMENT_OWN' AND SYSTEM_ID='UB';
--Cash send
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_CS' AND SYSTEM_ID='UB';

--FX rate
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FX_RATE','UB','CUS','-','EX','View FX Rates','Execute','N','N');
--Edit Beneficiary
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_UPD_EXTERNAL','UB','PAYEE','-','EX','Edit Beneficiary DFT','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_UPD_INTERNAL','UB','PAYEE','-','EX','Edit Beneficiary OTH','Execute','N','N');
--Credit card
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('ACT_CCD_UNBILLED_TRANS','UB','CRD','-','EX','Credit Card Unbilled Transactions','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('ACT_CCD_STATEMENT_TRANS','UB','CRD','-','EX','Credit Card Statement','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('ACT_CREDIT_CARD_DETAIL','UB','CRD','-','EX','Credit Card at a glance','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FT_CARD_PAYMENT_ONETIME','UB','CRD','-','EX','Credit Card 3rd Party Onetime Payment','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FT_CARD_PAYMENT_OWN','UB','CRD','-','EX','Credit Card Own Barclays Payment','Execute','N','N');
--Cash send
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_CS','UB','NO_GRP','-','EX','Cash Send','Execute','N','N');


COMMIT;