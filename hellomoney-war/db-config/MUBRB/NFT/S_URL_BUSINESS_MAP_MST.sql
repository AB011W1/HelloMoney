-----------------------		URL INSERTION QUERY FOR MUBRB - NFT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND URL_PATTERN='https://muonbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','MUBRB','https://muonbir-nft.barclays.intranet/bmg','EN');
COMMIT;