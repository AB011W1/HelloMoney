
-----------------------		URL INSERTION QUERY FOR SCBRB - NFT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='SCBRB' AND URL_PATTERN='https://scbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','SCBRB','https://scbir-nft.barclays.intranet/bmg','EN');
COMMIT;
