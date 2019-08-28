
-----------------------		URL INSERTION QUERY FOR EGBRB - SIT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='EGBRB' AND URL_PATTERN='https://egbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','EGBRB','https://egbir-nft.barclays.intranet/bmg','EN');
COMMIT;
