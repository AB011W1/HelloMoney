----- SCRIPT QUERY FOR OTP -----------

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND CATEGORY_ID='OTP' AND SCRIPT_KEY='otpHeaderLine1';
COMMIT;

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG, AUTHORIZED_INDICATOR) VALUES ('MB','MUBOB','OTP','otpHeaderLine1','','','','','','','','');
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND CATEGORY_ID='OTP' AND SCRIPT_KEY='otpHeaderLine2';
COMMIT;

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG, AUTHORIZED_INDICATOR) VALUES ('MB','MUBOB','OTP','otpHeaderLine2','','','','','','','','');
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND CATEGORY_ID='OTP' AND SCRIPT_KEY='otpFooter';
COMMIT;

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG, AUTHORIZED_INDICATOR) VALUES ('MB','MUBOB','OTP','otpFooter','','','','','','','','');
COMMIT;

----- SCRIPT QUERY FOR LOGOUT SUMMERY ------
DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='BP';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO','BP',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='OWN';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','OWN',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='CCP';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','CCP',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='IT';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','IT',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='DT';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','DT',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='INTL';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','INTL',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='MTP';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','MTP',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='BCD';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','BCD',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='PBC';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','PBC',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='CHECK_BOOK_REQUEST';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','CHECK_BOOK_REQUEST',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='PMC';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','MUBOB','PAGEINFO ','PMC',null,null,null,null,null,null,null,null);
COMMIT;

-------------------------------------------------- C_SCRIPT_RES_MST For OTP MUBOB -----------------------------

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='otpHeaderLine1';
COMMIT;

INSERT INTO  C_SCRIPT_RES_MST (LANGUAGE_ID, SEQUENCE_NO, SCRIPT_VALUE, MAKER_ID, CHECKER_ID, STATUS, LAST_MODIFIED, LAST_AUTHORIZED, ACTION, DELETED_FLAG, AUTHORIZED_INDICATOR, SCRIPT_KEY, SYSTEM_ID, BUSINESS_ID) VALUES ('EN','1','One Time Password (OTP) has been sent to your mobile phone ({0}).','','','','','','','','','otpHeaderLine1','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='otpHeaderLine2';
COMMIT;

INSERT INTO  C_SCRIPT_RES_MST (LANGUAGE_ID, SEQUENCE_NO, SCRIPT_VALUE, MAKER_ID, CHECKER_ID, STATUS, LAST_MODIFIED, LAST_AUTHORIZED, ACTION, DELETED_FLAG, AUTHORIZED_INDICATOR, SCRIPT_KEY, SYSTEM_ID, BUSINESS_ID) VALUES ('EN','1','If the above number is not your current phone number, please terminate the operation instantly and contact our Customer Service Centre at 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','','','','','','','','','otpHeaderLine2','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='otpFooter';
COMMIT;

INSERT INTO  C_SCRIPT_RES_MST (LANGUAGE_ID, SEQUENCE_NO, SCRIPT_VALUE, MAKER_ID, CHECKER_ID, STATUS, LAST_MODIFIED, LAST_AUTHORIZED, ACTION, DELETED_FLAG, AUTHORIZED_INDICATOR,SCRIPT_KEY, SYSTEM_ID, BUSINESS_ID) VALUES ('EN','1','If you don''t get the SMS within the next 3 minutes, please click ''Cancel'' and try login again.','','','','','','','','','otpFooter','MB','MUBOB');
COMMIT;

------------------------------------------------------- Update Message for otpExpired key --------------------------------------------------------

UPDATE C_SCRIPT_RES_MST SET SCRIPT_VALUE='OTP you entered has expired. Please login again' WHERE  BUSINESS_ID = 'MUBOB'  AND SYSTEM_ID='MB' AND LANGUAGE_ID='EN' AND SCRIPT_KEY ='otpExpired';
COMMIT;

-----  C_SCRIPT_RES_MST For LOGOUT SUMMERY MUBOB ------

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='BP';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Pay Bill',null,null,null,null,null,null,null,null,'BP','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='OWN';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Fund transfer to Own account',null,null,null,null,null,null,null,null,'OWN','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='CCP';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Payment to Third Party Barclaycard',null,null,null,null,null,null,null,null,'CCP','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='IT';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Fund Transfer to Domestic Barclays account',null,null,null,null,null,null,null,null,'IT','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='DT';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Fund Transfer to Domestic Other Bank Account',null,null,null,null,null,null,null,null,'DT','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='INTL';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'International '|| '&' || ' Urgent Fund Transfer',null,null,null,null,null,null,null,null,'INTL','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='MTP';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Prepaid Mobile Recharge',null,null,null,null,null,null,null,null,'MTP','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='BCD';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Payment to Own Barclaycard',null,null,null,null,null,null,null,null,'BCD','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='PBC';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Purchase Bank Draft',null,null,null,null,null,null,null,null,'PBC','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='CHECK_BOOK_REQUEST';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Cheque Book Request',null,null,null,null,null,null,null,null,'CHECK_BOOK_REQUEST','MB','MUBOB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB'  AND SCRIPT_KEY='PMC';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Purchase Manager''s Cheque',null,null,null,null,null,null,null,null,'PMC','MB','MUBOB');
COMMIT;


