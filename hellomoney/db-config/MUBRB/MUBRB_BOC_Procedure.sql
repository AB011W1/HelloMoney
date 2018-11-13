BEGIN
  BOC_Procedure_SSA('MUBRB');
END;
/

DELETE FROM C_BIZ_FUNC_MMAP WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND ACTIVITY_ID='PMT_FT_CARD_PAYMENT_PAYEE';
INSERT INTO C_BIZ_FUNC_MMAP (BUSINESS_ID,ACTIVITY_ID,SYSTEM_ID,FROM_TIME,TO_TIME,HOLIDAY_ALLOWED_FLG,BLOCKED_FLG) values ('MUBRB','PMT_FT_CARD_PAYMENT_PAYEE','MB',null,null,'N','N');

DELETE FROM C_BIZ_FUNC_MMAP WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND ACTIVITY_ID='PMT_BP_BCD_PAYEE';
INSERT INTO C_BIZ_FUNC_MMAP (BUSINESS_ID,ACTIVITY_ID,SYSTEM_ID,FROM_TIME,TO_TIME,HOLIDAY_ALLOWED_FLG,BLOCKED_FLG) values ('MUBRB','PMT_BP_BCD_PAYEE','MB',null,null,'N','N');

COMMIT;