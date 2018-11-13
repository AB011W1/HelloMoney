BEGIN
   C_PROD_ELIG_Procedure('UGBRB');
END;
/


---- Fund Transfer between My Accounts - PMT_FT_OWN --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_OWN' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';

INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_FT_OWN','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_FT_OWN','MB','CR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);

---- Barclaycard Payment - PMT_BP_BCD_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_BP_BCD_PAYEE' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';

INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_BP_BCD_PAYEE','MB','CR',null,'CCD','INC','REJA,BKT6,REJC,COFF,VCLN,CPBD,BLCK,CLBN,COBL,HRD1,UPGD,XDDQ,CLSC,NORM,VIP,SKIP,XPDQ,SETL,BKTX,PDD,HRDQ',null,null,null,'SFRD,WARN,EDEL,I,DNBA,EOUT,DLNQ,DESD,MGLD,UDLP,CFRD,PPIC,FRUD,UNDL,DECD,LOST,EXPD,BKT2,OVLM,BKT4,REJA,BKT6,REJC,COFF,NREN,VCLN,CPBD,BLCK,CLBN,COBL,RJFE,HRD1,UPGD,CLSC,NORM,VIP,SKIP,SETL,BKTX,LDEL,PICK,VCLA,LOUT,STLN,VCLC,BKT3,BKT5,REJF,WROF,NOAU,HGLD,CLSB,SKP1,DELD,CLSD,REPY,REFR,NEW,NRJF',null,'MB',null);

INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_BP_BCD_PAYEE','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);



---- Fund Transfer To Domestic Barclays accounts with payee - PMT_FT_INTERNAL_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_INTERNAL_PAYEE' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_FT_INTERNAL_PAYEE','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);

---- Fund Transfer To Domestic Non Barclays accounts with payee - PMT_FT_EXTERNAL_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_EXTERNAL_PAYEE' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_FT_EXTERNAL_PAYEE','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);

---- Fund Transfer - international with payee - PMT_FT_INTL_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_INTL_PAYEE' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_FT_INTL_PAYEE','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);

---- Bill Payment with payee - PMT_BP_BILLPAY_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_BP_BILLPAY_PAYEE' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_BP_BILLPAY_PAYEE','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);

---NEED TO PUT ENTRY FOR CREDIT CARD IN BILLPAY PAYEE

---- Purchase Bank Draft with payee - PMT_FT_BKD_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_BKD_PAYEE' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_FT_BKD_PAYEE','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'IB',null);

---- Purchase Manager Cheque with payee - PMT_FT_MRC_PAYEE --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='PMT_FT_MRC_PAYEE' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','PMT_FT_MRC_PAYEE','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);

---- Cheque Book Request - ACT_CHK_BOOK_REQUEST --------
DELETE FROM C_PRODUCT_ELIGIBILITY WHERE ACTIVITY_ID='ACT_CHK_BOOK_REQUEST' AND BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB';
INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,BUSINESS_ID,ACTIVITY_ID,ROLE_CATEGORY_CD,PRD_CRDR_IND,PRD_CODE,PRD_CATEGORY,INC_OR_EXC,ACCOUNT_STATUS,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2,CARD_TYPE,CARD_STATUS,CARD_BLOCK_CODE,SYSTEM_ID,EXCEPTION) values (c_product_eligibility_SEQUENCE.NEXTVAL,'UGBRB','ACT_CHK_BOOK_REQUEST','MB','DR',null,'CASA','INC','CH-6,CH-8',null,null,null,null,null,'MB',null);


COMMIT;