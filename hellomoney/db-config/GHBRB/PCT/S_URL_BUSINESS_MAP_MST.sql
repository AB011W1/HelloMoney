
-----------------------		URL INSERTION QUERY FOR GHBRB - PCT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='GHBRB' AND URL_PATTERN='https://widd.wload.global:52254/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','GHBRB','https://widd.wload.global:52254/bmg','EN');
COMMIT;