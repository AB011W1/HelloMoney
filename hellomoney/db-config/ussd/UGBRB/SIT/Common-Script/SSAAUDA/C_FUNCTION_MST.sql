------------ C_FUNCTION_MST --------

	DELETE  FROM C_FUNCTION_MST  WHERE SYSTEM_ID='UB';

	INSERT INTO C_FUNCTION_MST (SELECT ACTIVITY_ID, 'UB',FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC  FROM C_FUNCTION_MST  WHERE SYSTEM_ID = 'MB');

	-- INSERTING into C_FUNCTION_MST

DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='ACT_ACCOUNT_DETAIL_CASA_DETAIL' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='ACT_ACCOUNT_CASA_ACTIVITY' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='ACT_CHK_BOOK_REQUEST' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='ACT_OPEN_TD' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='CUS_INT_RATES_TD' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='CUS_ORDER_PAPER_STMT' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_BP_BILLPAY_ONETIME' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_BP_BILLPAY_PAYEE' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_BP_MOBILE_TOPUP_ONETIME' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_BP_MOBILE_WALLET_ONETIME' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_FT_EXTERNAL_PAYEE' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_FT_INTERNAL_ONETIME' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_FT_INTERNAL_PAYEE' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_FT_OWN' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_ADD_BP' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_ADD_EXTERNAL' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_ADD_INTERNAL' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_DELETE_BP' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_DELETE_EXTERNAL' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_DELETE_INTERNAL' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='PMT_PAYEE_LIST' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='SEC_CHG_PWD' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='SEC_LOGIN' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='SEC_ONLN_REG' AND SYSTEM_ID='UB';
DELETE  FROM C_FUNCTION_MST  WHERE ACTIVITY_ID='TXN_HISTORY' AND SYSTEM_ID='UB';

Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('ACT_ACCOUNT_DETAIL_CASA_DETAIL','UB','NO_GRP','-','EX','Balance Enquiry','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('ACT_ACCOUNT_CASA_ACTIVITY','UB','NO_GRP','-','EX','Mini Statement','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('ACT_CHK_BOOK_REQUEST','UB','ACT','-','EX','Cheque Book Request','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('ACT_OPEN_TD','UB','NO_GRP','-','EX','Apply TD','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('CUS_INT_RATES_TD','UB','CUS','-','EX','View FD Rates','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('CUS_ORDER_PAPER_STMT','UB','CUS','-','EX','Statement Request','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_BP_BILLPAY_ONETIME','UB','BP','-','EX','Pay Bill UnRegistered','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_BP_BILLPAY_PAYEE','UB','BP','-','EX','Pay Bill Registered','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_BP_MOBILE_TOPUP_ONETIME','UB','BP','-','EX','Airtime Top Up','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_BP_MOBILE_WALLET_ONETIME','UB','NO_GRP','-','EX','Mobile Wallet','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FT_EXTERNAL_PAYEE','UB','FT','-','EX','Fund Transfer DFT Registered','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FT_INTERNAL_ONETIME','UB','FT','-','EX','Fund Transfer OTH UnRegistered','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FT_INTERNAL_PAYEE','UB','FT','-','EX','Fund Transfer OTH Registered','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FT_OWN','UB','FT','-','EX','Fund Transfer OWN','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_ADD_BP','UB','PAYEE','-','EX','Register Biller','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_ADD_EXTERNAL','UB','PAYEE','-','EX','Register Beneficiary DFT','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_ADD_INTERNAL','UB','PAYEE','-','EX','Register Beneficiary OTH','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_DELETE_BP','UB','PAYEE','-','EX','Delete Biller','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_DELETE_EXTERNAL','UB','PAYEE','-','EX','Delete Beneficiary DFT','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_DELETE_INTERNAL','UB','PAYEE','-','EX','Delete Beneficiary OTH','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_PAYEE_LIST','UB','PAYEE','-','EX','View My Billers','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('SEC_CHG_PWD','UB','SEC_POST','-','EX','Change PIN','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('SEC_LOGIN','UB','SEC_PRE','-','EX','User Authentication','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('SEC_ONLN_REG','UB','SEC_PRE','-','EX','Self Registration','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('TXN_HISTORY','UB','NO_GRP','-','EX','View Last Paid Bills','Execute','N','N');
Insert into C_FUNCTION_MST (ACTIVITY_ID,SYSTEM_ID,FUNCTION_ID,FUNCTION_SUB_ID,ACTION_ID,FUNCTION_DESC,ACTION_DESC,INQUIRY_FLG,IBOC) values ('PMT_FT_CS','UB','NO_GRP','-','EX','Cash Send','Execute','N','N');


COMMIT;