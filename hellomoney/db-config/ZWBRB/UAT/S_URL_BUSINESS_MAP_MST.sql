
-----------------------		URL INSERTION QUERY FOR ZWBRB - UAT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZWBRB' AND URL_PATTERN='https://cs1t.wload.barclays.co.uk:22901/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','ZWBRB','https://cs1t.wload.barclays.co.uk:22901/bmg','EN');
COMMIT;
