
-----------------------		URL INSERTION QUERY FOR ZMBRB - SIT   --------------------------

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='ZMBRB' AND URL_PATTERN='https://zmbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','ZMBRB','https://zmbir-nft.barclays.intranet/bmg','EN');
COMMIT;
