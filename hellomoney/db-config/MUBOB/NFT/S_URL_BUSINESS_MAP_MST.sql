-----------------------		URL INSERTION QUERY FOR MUBOB - SIT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND URL_PATTERN='https://muoffbir-nft.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','MUBOB','https://muoffbir-nft.barclays.intranet/bmg','EN');
COMMIT;