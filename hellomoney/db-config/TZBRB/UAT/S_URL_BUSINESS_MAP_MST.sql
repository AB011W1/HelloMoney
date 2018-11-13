
-----------------------		URL INSERTION QUERY FOR TZBRB - UAT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST  WHERE SYSTEM_ID='MB' AND BUSINESS_ID='TZBRB' AND URL_PATTERN='https://cs1t.wload.barclays.co.uk:23201/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','TZBRB','https://cs1t.wload.barclays.co.uk:23201/bmg','EN');
COMMIT;
