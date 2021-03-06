----- SCRIPT QUERY FOR OTP -----------

-----DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB' AND CATEGORY_ID='OTP' AND SCRIPT_KEY='otpHeaderLine1';


-----INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG, AUTHORIZED_INDICATOR) VALUES ('MB','UGBRB','OTP','otpHeaderLine1','','','','','','','','');


-----DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB' AND CATEGORY_ID='OTP' AND SCRIPT_KEY='otpHeaderLine2';


-----INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG, AUTHORIZED_INDICATOR) VALUES ('MB','UGBRB','OTP','otpHeaderLine2','','','','','','','','');


-----DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB' AND CATEGORY_ID='OTP' AND SCRIPT_KEY='otpFooter';


-----INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG, AUTHORIZED_INDICATOR) VALUES ('MB','UGBRB','OTP','otpFooter','','','','','','','','');


----- SCRIPT QUERY FOR LOGOUT SUMMERY ------
DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='BP';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO','BP',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='OWN';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','OWN',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='CCP';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','CCP',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='IT';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','IT',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='DT';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','DT',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='INTL';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','INTL',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='MTP';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','MTP',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='BCD';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','BCD',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='PBC';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','PBC',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='CHECK_BOOK_REQUEST';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','CHECK_BOOK_REQUEST',null,null,null,null,null,null,null,null);
COMMIT;

DELETE FROM C_SCRIPT_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='PMC';

INSERT INTO C_SCRIPT_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY_ID,SCRIPT_KEY,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR) values ('MB','UGBRB','PAGEINFO ','PMC',null,null,null,null,null,null,null,null);
COMMIT;

-------------------------------------------------- C_SCRIPT_RES_MST For OTP UGBRB -----------------------------

-----DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='otpHeaderLine1';


-----INSERT INTO  C_SCRIPT_RES_MST (LANGUAGE_ID, SEQUENCE_NO, SCRIPT_VALUE, MAKER_ID, CHECKER_ID, STATUS, LAST_MODIFIED, LAST_AUTHORIZED, ACTION, DELETED_FLAG, AUTHORIZED_INDICATOR, SCRIPT_KEY, SYSTEM_ID, BUSINESS_ID) VALUES ('EN','1','One Time Password (OTP) has been sent to your mobile phone ({0}).','','','','','','','','','otpHeaderLine1','MB','UGBRB');


-----DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='otpHeaderLine2';


-----INSERT INTO  C_SCRIPT_RES_MST (LANGUAGE_ID, SEQUENCE_NO, SCRIPT_VALUE, MAKER_ID, CHECKER_ID, STATUS, LAST_MODIFIED, LAST_AUTHORIZED, ACTION, DELETED_FLAG, AUTHORIZED_INDICATOR, SCRIPT_KEY, SYSTEM_ID, BUSINESS_ID) VALUES ('EN','1','If the above number is not your current phone number, please terminate the operation instantly and contact our Customer Service Centre on 020 3900000,722 130120,732 130120 within Kenya or +254 020 3900000,+254 722 130120,+254 732 130120 from outside Kenya for assistance.','','','','','','','','','otpHeaderLine2','MB','UGBRB');


-----DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='otpFooter';


-----INSERT INTO  C_SCRIPT_RES_MST (LANGUAGE_ID, SEQUENCE_NO, SCRIPT_VALUE, MAKER_ID, CHECKER_ID, STATUS, LAST_MODIFIED, LAST_AUTHORIZED, ACTION, DELETED_FLAG, AUTHORIZED_INDICATOR,SCRIPT_KEY, SYSTEM_ID, BUSINESS_ID) VALUES ('EN','1','If you don''t get the SMS within the next 3 minutes, please click ''Cancel'' and try login again.','','','','','','','','','otpFooter','MB','UGBRB');


------------------------------------------------------- Update Message for otpExpired key --------------------------------------------------------
-----UPDATE C_SCRIPT_RES_MST SET SCRIPT_VALUE='OTP you entered has expired. Please login again' WHERE BUSINESS_ID='UGBRB' AND SYSTEM_ID='MB' AND LANGUAGE_ID='EN' AND SCRIPT_KEY ='otpExpired';
-----COMMIT;

-----  C_SCRIPT_RES_MST For LOGOUT SUMMERY UGBRB ------

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='BP';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Pay Bill',null,null,null,null,null,null,null,null,'BP','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='OWN';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Fund transfer to Own account',null,null,null,null,null,null,null,null,'OWN','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='CCP';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Payment to Third Party Barclaycard',null,null,null,null,null,null,null,null,'CCP','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='IT';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Fund Transfer to Domestic Barclays account',null,null,null,null,null,null,null,null,'IT','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='DT';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Fund Transfer to Domestic Other Bank Account',null,null,null,null,null,null,null,null,'DT','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='INTL';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'International '|| '&' || ' Urgent Fund Transfer',null,null,null,null,null,null,null,null,'INTL','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='MTP';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Prepaid Mobile Recharge',null,null,null,null,null,null,null,null,'MTP','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='BCD';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Payment to Own Barclaycard',null,null,null,null,null,null,null,null,'BCD','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='PBC';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Purchase Bank Draft',null,null,null,null,null,null,null,null,'PBC','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='CHECK_BOOK_REQUEST';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Cheque Book Request',null,null,null,null,null,null,null,null,'CHECK_BOOK_REQUEST','MB','UGBRB');
COMMIT;

DELETE FROM C_SCRIPT_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='UGBRB'  AND SCRIPT_KEY='PMC';

INSERT INTO C_SCRIPT_RES_MST  (LANGUAGE_ID,SEQUENCE_NO,SCRIPT_VALUE,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,SCRIPT_KEY,SYSTEM_ID,BUSINESS_ID) values ('EN',null,'Purchase Manager''s Cheque',null,null,null,null,null,null,null,null,'PMC','MB','UGBRB');
COMMIT;

