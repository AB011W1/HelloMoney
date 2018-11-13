BEGIN
  C_PROD_ELIG_Procedure('MUBRB');
END;
/



---- Fund Transfer between My Accounts - PMT_FT_OWN --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_OWN' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';

INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_OWN','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_OWN','MB','CR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);


---- Barclaycard Payment - PMT_BP_BCD_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_BP_BCD_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_BP_BCD_PAYEE','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);


---- Fund Transfer To Domestic Barclays accounts with payee - PMT_FT_INTERNAL_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_INTERNAL_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_INTERNAL_PAYEE','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);

---- Fund Transfer To Domestic Non Barclays accounts with payee - PMT_FT_EXTERNAL_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_EXTERNAL_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_EXTERNAL_PAYEE','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);

---- Fund Transfer - international with payee - PMT_FT_INTL_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_INTL_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_INTL_PAYEE','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);

---- Bill Payment with payee - PMT_BP_BILLPAY_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_BP_BILLPAY_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_BP_BILLPAY_PAYEE','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);


---- Third Party Barclay Card - PMT_BP_BCD_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_BP_BCD_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';

INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_BP_BCD_PAYEE','MB','CR',null,'CCD','INC','CLSB,UPGD,CLSC,DELD,XDDQ,CLSD,REPY,NORM,REFR,SKIP,NEW,A,XPDQ,I,SETL,ACCO,PDD,DESD,HRDQ,FRUD,BCT1,BCT2,BCT3,PICK,BCT4,BCT5,VCLA,DECD,CLFD,OVLM,REJA,REJC,MLDQ,VCLN,REJF,CPBD,BLCK,WROF,CLBN,NOAU,COBL',null,null,null,'DESD,CCRD,CCLA,CFRD,STLC,FRUD,CNCU,BCT1,BCT2,BCT3,BCT4,PICK,UNDL,VCLA,BCT5,STLN,LOST,VCLC,EXPC,EXPD,REJA,CULD,REJC,CCUB,NREN,REJF,CPBD,BLCK,WROF,NOAU,CLBN,COBL,RJFE,CLSB,UPGD,CLSC,DELD,CLSD,REPY,NORM,REFR,SKIP,NEW,CVSD,CDCF,SFRD,SETL',null,'MB',null);
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_BP_BCD_PAYEE','MB','DR',null,'CASA','INC','CASA-0,CASA-1,CASA-2,CASA-3,CASA-4,CASA-5',null,null,null,'NORM',null,'MB',null);

---- Own Barclay Card - PMT_FT_CARD_PAYMENT_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_CARD_PAYMENT_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';

INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_CARD_PAYMENT_PAYEE','MB','CR',null,'CCD','INC','CLSB,UPGD,CLSC,DELD,XDDQ,CLSD,REPY,NORM,REFR,SKIP,NEW,A,XPDQ,I,SETL,ACCO,PDD,DESD,HRDQ,FRUD,BCT1,BCT2,BCT3,PICK,BCT4,BCT5,VCLA,DECD,CLFD,OVLM,REJA,REJC,MLDQ,VCLN,REJF,CPBD,BLCK,WROF,CLBN,NOAU,COBL',null,null,null,'DESD,CFRD,STLC,FRUD,BCT1,BCT2,BCT3,BCT4,PICK,UNDL,VCLA,BCT5,STLN,LOST,VCLC,EXPC,EXPD,CULD,CCUB,NREN,REJF,CPBD,BLCK,WROF,NOAU,COBL,RJFE,UPGD,DELD,CLSD,REPY,NORM,REFR,SKIP,NEW,CVSD,CDCF,SFRD,SETL',null,'MB',null);
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_CARD_PAYMENT_PAYEE','MB','DR',null,'CASA','INC','CASA-0,CASA-1,CASA-2,CASA-3,CASA-4,CASA-5',null,null,null,'NORM',null,'MB',null);


---- Purchase Bank Draft with payee - PMT_FT_BKD_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_BKD_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_BKD_PAYEE','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);

---- Purchase Manager Cheque with payee - PMT_FT_MRC_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_MRC_PAYEE' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','PMT_FT_MRC_PAYEE','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);

---- Cheque Book Request - ACT_CHK_BOOK_REQUEST --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='ACT_CHK_BOOK_REQUEST' AND BUSINESS_ID='MUBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'MUBRB','ACT_CHK_BOOK_REQUEST','MB','DR',null,'CASA','INC','CASA-0',null,null,null,null,null,'MB',null);

COMMIT;

