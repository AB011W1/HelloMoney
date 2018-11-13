
-----------------------		URL INSERTION QUERY FOR BWBRB - NFT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='BWBRB' AND URL_PATTERN='https://bwbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','BWBRB','https://bwbir-nft.barclays.intranet/bmg','EN');
COMMIT;