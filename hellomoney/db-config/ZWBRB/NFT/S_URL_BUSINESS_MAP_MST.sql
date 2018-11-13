
-----------------------		URL INSERTION QUERY FOR ZWBRB - SIT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZWBRB' AND URL_PATTERN='https://zwbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','ZWBRB','https://zwbir-nft.barclays.intranet/bmg','EN');
COMMIT;
