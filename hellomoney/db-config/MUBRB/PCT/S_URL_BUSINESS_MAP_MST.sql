-----------------------		URL INSERTION QUERY FOR MUBRB - PCT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND URL_PATTERN='https://widd.wload.global:52251/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','MUBRB','https://widd.wload.global:52251/bmg','EN');
COMMIT;