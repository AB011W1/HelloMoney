
-----------------------		URL INSERTION QUERY FOR TZBRB - NFT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST  WHERE SYSTEM_ID='MB' AND BUSINESS_ID='TZBRB' AND URL_PATTERN='https://tzbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','TZBRB','https://tzbir-nft.barclays.intranet/bmg','EN');
COMMIT;
