------------------------------------------------------- Update Message_value for transferAmountError key ----------------------------------------------
UPDATE C_MESSAGE_RES_MST SET MESSAGE_VALUE ='The number entered is invalid. Please enter valid number.' WHERE  BUSINESS_ID='ZMBRB' AND SYSTEM_ID='MB' AND MESSAGE_KEY='transferAmountError';
COMMIT;

------------------------------------------------------- Update Message_value for Mobile Banking -------------------------------------------------
UPDATE C_MESSAGE_RES_MST SET message_value='Your Mobile banking access has been blocked. Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.' WHERE business_id='ZMBRB' AND system_id='MB' AND message_key = 'SEC_CUST_LOCKED';
COMMIT;
UPDATE C_MESSAGE_RES_MST SET message_value='Your Mobile banking access has been locked.  Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.'  WHERE BUSINESS_ID='ZMBRB' AND system_id='MB' AND MESSAGE_KEY='LOGIN_DORMANT';
COMMIT;

---------------------------------------------- Update Message_value for Not Registered User ----------------------------------------------
UPDATE C_MESSAGE_RES_MST SET message_value='You have not been registered for mobile banking. Please contact the branch.' WHERE BUSINESS_ID='ZMBRB'  AND system_id='MB' AND message_key = 'SEC_NOT_BEEN_REGISTERED_FOR_INTERNET';
COMMIT;
---------------------------------------------- Insert Values for DATE IS NOT VALID for MSG3 key ----------------------------------------------
DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB' AND MESSAGE_KEY='MSG3';

INSERT INTO   C_MESSAGE_RES_MST (LANGUAGE_ID, MESSAGE_VALUE, SYSTEM_ID, BUSINESS_ID, MESSAGE_KEY) VALUES('EN','The Entered Date is not in valid format (mm-dd-yyyy)','MB','ZMBRB','MSG3');
COMMIT;
---------------------------------------------- Update Message_Value For Message_Key 'LOCK_MSG' ----------------------------------------------

UPDATE C_MESSAGE_RES_MST SET message_value='Your Mobile banking access has been blocked because of {0} failed attempts.   Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance. ' WHERE BUSINESS_ID = 'ZMBRB' AND system_id='MB' AND message_key = 'LOCK_MSG';
COMMIT;
---------------------------------------------- Update Message_Value For Message_Key 'LOGIN_LOCK' ----------------------------------------------

UPDATE C_MESSAGE_RES_MST SET message_value='Your Mobile banking access has been blocked.   Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.' WHERE BUSINESS_ID = 'ZMBRB' AND system_id='MB' AND message_key = 'LOGIN_LOCK';
COMMIT;
---------------------------------------------- Update Message_Value For Message_Key 'PERM_LOCK_MSG' ----------------------------------------------

UPDATE C_MESSAGE_RES_MST SET message_value='Your Mobile banking access has been permanently locked.  Please contact our Customer Service Centre at 5950 within Zambia or +260 211 366100 from outside Zambia for assistance. ' WHERE BUSINESS_ID = 'ZMBRB' AND system_id='MB' AND message_key = 'PERM_LOCK_MSG';
COMMIT;
---------------------------------------------- Update Category as WARN for SEC_LOGIN_LGN_WARNING key ----------------------------------------
UPDATE C_MESSAGE_MST SET CATEGORY='WARN' WHERE BUSINESS_ID='ZMBRB' AND SYSTEM_ID='MB' AND MESSAGE_KEY='SEC_LOGIN_LGN_WARNING';
COMMIT;
---------------------------------------------------------------------------  END  --------------------------------------------
--------------------------------------------------------------------- Added on 08-Sep-2011 -----------------------------------
UPDATE C_MESSAGE_RES_MST SET MESSAGE_VALUE ='The transaction amount exceeds your daily transaction limit of {0} {1}. Please enter the correct amount.' WHERE  BUSINESS_ID='ZMBRB' AND SYSTEM_ID='MB' AND MESSAGE_KEY='PMT_BP_LIMIT_DAILY';
COMMIT;
-------------------------------------------------------------------- Added on 08-Sep-2011 ----------------------------------------
UPDATE C_MESSAGE_RES_MST SET MESSAGE_VALUE='Your login password will expire in {0} days, Please change it using our website.' WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB' AND MESSAGE_KEY='SEC_LOGIN_LGN_WARNING';
COMMIT;

UPDATE C_MESSAGE_RES_MST SET message_value='User is not registered yet.' WHERE BUSINESS_ID='ZMBRB'  AND system_id='MB' AND message_key='SEC00015';
COMMIT;

---------------------------------------------- Insert Values for DATE IS NOT VALID for MSG3 key ----------------------------------------------
DELETE FROM C_MESSAGE_RES_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB' AND MESSAGE_KEY='MSG3';

INSERT INTO   C_MESSAGE_RES_MST (LANGUAGE_ID, MESSAGE_VALUE, SYSTEM_ID, BUSINESS_ID, MESSAGE_KEY) VALUES('EN','The Entered Date is not in valid format (mm-dd-yyyy)','MB','ZMBRB','MSG3');
COMMIT;

DELETE FROM C_MESSAGE_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB' AND MESSAGE_KEY='MSG3';

INSERT INTO   C_MESSAGE_MST (SYSTEM_ID, BUSINESS_ID, CATEGORY, MESSAGE_KEY, MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG, SOURCE_SYSTEM_ID) VALUES ('MB','ZMBRB','ERROR','MSG3','','','','','','MCFE');
COMMIT;

------------------------------------------------Insert Values for key COM_IB_SESSION_EXPIRED----------------------------------------------------
DELETE FROM C_MESSAGE_MST where CATEGORY='FATAL' AND MESSAGE_KEY='COM_IB_SESSION_EXPIRED' AND SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB';

INSERT INTO   C_MESSAGE_MST (SYSTEM_ID, BUSINESS_ID, CATEGORY, MESSAGE_KEY, MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG, SOURCE_SYSTEM_ID) VALUES ('MB','ZMBRB','FATAL','COM_IB_SESSION_EXPIRED','','','','','','MCFE');

COMMIT;

DELETE FROM C_MESSAGE_RES_MST where MESSAGE_KEY='COM_IB_SESSION_EXPIRED' AND SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB';

INSERT INTO   C_MESSAGE_RES_MST (LANGUAGE_ID, MESSAGE_VALUE, SYSTEM_ID, BUSINESS_ID, MESSAGE_KEY) VALUES('EN','Your session has expired. Please Re-login.','MB','ZMBRB','COM_IB_SESSION_EXPIRED');

COMMIT;

-------------------------------------------- ZMBRB - Adding Error Message for MB in place of 'Beneficiary does not exist' = 'Outstanding Bill for the Biller' --------------------

DELETE FROM C_MESSAGE_MST WHERE CATEGORY='FATAL' AND MESSAGE_KEY='payment.invalid.beneficiary' AND SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB';
COMMIT;

INSERT INTO   C_MESSAGE_MST (SYSTEM_ID, BUSINESS_ID, CATEGORY, MESSAGE_KEY, MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG, SOURCE_SYSTEM_ID) VALUES ('MB','ZMBRB','FATAL','payment.invalid.beneficiary','','','','','','MCFE');
COMMIT;


DELETE FROM C_MESSAGE_RES_MST WHERE  MESSAGE_KEY='payment.invalid.beneficiary' AND SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB';
COMMIT;

INSERT INTO   C_MESSAGE_RES_MST (LANGUAGE_ID, MESSAGE_VALUE, SYSTEM_ID, BUSINESS_ID, MESSAGE_KEY) VALUES('EN','No Outstanding Bill for the Biller.','MB','ZMBRB','payment.invalid.beneficiary');
COMMIT;

--------------------------- ZMBRB - Update Message for Payee in case of null. Message key - PMT_PAYEE_NO_PAYEE --------------------------------

UPDATE C_MESSAGE_RES_MST SET  MESSAGE_VALUE='Sorry, you do not have any registered payee. Please register a Payee through Internet channel to perform the Bill Payment' WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB' AND MESSAGE_KEY='PMT_PAYEE_NO_PAYEE';
COMMIT;
