
-----------------------		URL INSERTION QUERY FOR GHBRB - UAT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='GHBRB' AND URL_PATTERN='https://cs1t.wload.barclays.co.uk:22701/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','GHBRB','https://cs1t.wload.barclays.co.uk:22701/bmg','EN');
COMMIT;