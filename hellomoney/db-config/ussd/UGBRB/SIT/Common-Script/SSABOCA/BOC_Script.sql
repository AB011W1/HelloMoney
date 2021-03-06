---------------  C_BIZ_FUNC_MMAP  ------

	DELETE  FROM C_BIZ_FUNC_MMAP where BUSINESS_ID='UGBRB' and SYSTEM_ID='UB';

	INSERT INTO  C_BIZ_FUNC_MMAP (SELECT BUSINESS_ID, ACTIVITY_ID, 'UB', FROM_TIME,  TO_TIME, HOLIDAY_ALLOWED_FLG,  BLOCKED_FLG   FROM C_BIZ_FUNC_MMAP   WHERE SYSTEM_ID = 'MB' AND BUSINESS_ID = 'UGBRB');

	DELETE FROM C_BIZ_FUNC_MMAP where BUSINESS_ID='UGBRB' and SYSTEM_ID='UB' AND ACTIVITY_ID='PMT_FT_MRC_PAYEE';

	INSERT INTO C_BIZ_FUNC_MMAP (BUSINESS_ID,ACTIVITY_ID,SYSTEM_ID,FROM_TIME,TO_TIME,HOLIDAY_ALLOWED_FLG,BLOCKED_FLG) values ('UGBRB','PMT_FT_MRC_PAYEE','UB',null,null,'N','N');

	DELETE FROM C_BIZ_FUNC_MMAP where BUSINESS_ID='UGBRB' and SYSTEM_ID='UB' AND ACTIVITY_ID='PMT_FT_BKD_PAYEE';

	INSERT INTO C_BIZ_FUNC_MMAP (BUSINESS_ID,ACTIVITY_ID,SYSTEM_ID,FROM_TIME,TO_TIME,HOLIDAY_ALLOWED_FLG,BLOCKED_FLG) values ('UGBRB','PMT_FT_BKD_PAYEE','UB',null,null,'N','N');

---------------- C_LISTVALUE_MST ----------

	DELETE  FROM C_LISTVALUE_MST where BUSINESS_ID='UGBRB' and SYSTEM_ID='UB';

	INSERT INTO  C_LISTVALUE_MST (SELECT 'UB',BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,FILTER_KEY_1,FILTER_KEY_2,DEFAULT_FLG,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG, EDITABLE_FLG  FROM C_LISTVALUE_MST  WHERE SYSTEM_ID = 'MB'  AND  BUSINESS_ID='UGBRB');

---------------- C_LISTVALUE_RES_MST ----------

	DELETE  FROM C_LISTVALUE_RES_MST where BUSINESS_ID='UGBRB' and SYSTEM_ID='UB';

	INSERT INTO  C_LISTVALUE_RES_MST (SELECT 'UB',BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER  FROM C_LISTVALUE_RES_MST  WHERE SYSTEM_ID = 'MB'  AND  BUSINESS_ID='UGBRB');

--------------- INSERTING into C_LISTVALUE_MST - for 'FT_DELIVERY_TYPE'
	DELETE FROM C_LISTVALUE_MST where SYSTEM_ID='UB' and GROUP_ID='FT_DELIVERY_TYPE'  AND BUSINESS_ID='UGBRB';

	INSERT INTO C_LISTVALUE_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,FILTER_KEY_1,FILTER_KEY_2,DEFAULT_FLG,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,EDITABLE_FLG) values ('UB','UGBRB','FT_DELIVERY_TYPE','BEN',null,null,'N',null,null,null,null,'N','Y');
	INSERT INTO C_LISTVALUE_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,FILTER_KEY_1,FILTER_KEY_2,DEFAULT_FLG,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,EDITABLE_FLG) values ('UB','UGBRB','FT_DELIVERY_TYPE','BRN',null,null,'N',null,null,null,null,'N','Y');
	INSERT INTO C_LISTVALUE_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,FILTER_KEY_1,FILTER_KEY_2,DEFAULT_FLG,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,EDITABLE_FLG) values ('UB','UGBRB','FT_DELIVERY_TYPE','REM',null,null,'N',null,null,null,null,'N','Y');


-------------- INSERTING into C_LISTVALUE_RES_MST - for 'FT_DELIVERY_TYPE'
	DELETE FROM C_LISTVALUE_RES_MST where SYSTEM_ID='UB' and GROUP_ID='FT_DELIVERY_TYPE' AND BUSINESS_ID='UGBRB';

	INSERT INTO C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('UB','UGBRB','FT_DELIVERY_TYPE','REM','EN','Mail to Remitter',0);
	INSERT INTO C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('UB','UGBRB','FT_DELIVERY_TYPE','BEN','EN','Mail to Beneficiary',2);
	INSERT INTO C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('UB','UGBRB','FT_DELIVERY_TYPE','BRN','EN','Collect at Branch',4);

-------------- INSERTING into C_LISTVALUE_MST - for 'FT_DRAFT_TYPE'
	DELETE FROM C_LISTVALUE_MST where SYSTEM_ID='UB' and GROUP_ID='FT_DRAFT_TYPE'  AND BUSINESS_ID='UGBRB';

	INSERT INTO C_LISTVALUE_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,FILTER_KEY_1,FILTER_KEY_2,DEFAULT_FLG,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,EDITABLE_FLG) values ('UB','UGBRB','FT_DRAFT_TYPE','BD',null,null,'N',null,null,null,null,'N','Y');
	INSERT INTO C_LISTVALUE_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,FILTER_KEY_1,FILTER_KEY_2,DEFAULT_FLG,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,EDITABLE_FLG) values ('UB','UGBRB','FT_DRAFT_TYPE','MC',null,null,'N',null,null,null,null,'N','Y');

-------------- INSERTING into C_LISTVALUE_RES_MST - for 'FT_DRAFT_TYPE'
	DELETE FROM C_LISTVALUE_RES_MST where SYSTEM_ID='UB' and GROUP_ID='FT_DRAFT_TYPE'  AND BUSINESS_ID='UGBRB';

	INSERT INTO	C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('UB','UGBRB','FT_DRAFT_TYPE','BD','EN','Bank Draft',1);
	INSERT INTO C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('UB','UGBRB','FT_DRAFT_TYPE','MC','EN','Manager''s Cheque',2);

-------------- INSERTING into C_LISTVALUE_GROUP_MST
	INSERT INTO C_LISTVALUE_GROUP_MST (GROUP_ID,DESCRIPTION,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID,EDITABLE_FLG,CREATED_BY,CREATED_DTM) VALUES ('SYS_PARAM_CS','Cash Send System Params','E20042298',sysdate,'E20042298',sysdate,'N','MCFE','Y',null,null);

---------------  C_COMPONENT_MST ----------------

	DELETE  FROM C_COMPONENT_MST where business_id='UGBRB' and system_id='UB';

	INSERT INTO  C_COMPONENT_MST (SELECT SCREEN_ID,'UB',BUSINESS_ID,COMPONENT_KEY,RENDERED_FLG,REQUIRED_FLG,HAS_HELP_TEXT_FLG,FORMATTER_ID,LISTVALUE_GROUP_ID,DEFAULT_VALUE,DISP_ORDER,PARENT_COMPONENT_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,CONFIG_FLG,COMPONENT_TYPE,READONLY_FLG  FROM C_COMPONENT_MST  WHERE SYSTEM_ID = 'MB'  AND  BUSINESS_ID='UGBRB');

---------------  C_COMPONENT_RES_MST --------------

	DELETE  FROM C_COMPONENT_RES_MST where business_id='UGBRB' and system_id='UB';

	INSERT INTO  C_COMPONENT_RES_MST (SELECT LANGUAGE_ID,LABEL_VALUE,TOOL_TIP,HELP_TEXT,LENGTH,SCREEN_ID,'UB',BUSINESS_ID,COMPONENT_KEY  FROM C_COMPONENT_RES_MST  WHERE SYSTEM_ID = 'MB'  AND  BUSINESS_ID='UGBRB');

---------------- S_FUNCTION_CONFIG -----------------

	DELETE  FROM S_FUNCTION_CONFIG where business_id='UGBRB' and system_id='UB';

	INSERT INTO  S_FUNCTION_CONFIG(SELECT BUSINESS_ID,'UB',ACTIVITY_ID,TXN_LIMIT_TYP,FEATURE_BLKOUT_FLG,ADV_TXT_FLG,ADV_IMG_TOP_FLG,ADV_IMG_RIGHTNAV_FLG,MODIFIED_DTM,MODIFIED_BY  FROM S_FUNCTION_CONFIG  WHERE SYSTEM_ID = 'MB'  AND  BUSINESS_ID='UGBRB');


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

COMMIT;
