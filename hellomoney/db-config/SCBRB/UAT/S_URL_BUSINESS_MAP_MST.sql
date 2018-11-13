
-----------------------		URL INSERTION QUERY FOR SCBRB - UAT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='SCBRB' AND URL_PATTERN='https://cs1t.wload.barclays.co.uk:23301/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','SCBRB','https://cs1t.wload.barclays.co.uk:23301/bmg','EN');
COMMIT;
