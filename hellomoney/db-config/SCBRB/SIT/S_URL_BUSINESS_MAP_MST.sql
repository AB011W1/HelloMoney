
-----------------------		URL INSERTION QUERY FOR SCBRB - SIT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='SCBRB' AND URL_PATTERN='https://s04t.wload.global:33100/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','SCBRB','https://s04t.wload.global:33100/bmg','EN');
COMMIT;
