
-----------------------		URL INSERTION QUERY FOR KEBRB - PRODUCTION   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND URL_PATTERN='https://www.ke.secure.barclays.com/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','KEBRB','https://www.ke.secure.barclays.com/bmg','EN');

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND URL_PATTERN='https://kec2bir-live.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','KEBRB','https://kec2bir-live.barclays.intranet/bmg','EN');


COMMIT;