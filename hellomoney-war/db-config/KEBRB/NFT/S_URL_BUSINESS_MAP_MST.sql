
-----------------------		URL INSERTION QUERY FOR KEBRB - SIT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='KEBRB' AND URL_PATTERN='https://kebir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','KEBRB','https://kebir-nft.barclays.intranet/bmg','EN');
COMMIT;