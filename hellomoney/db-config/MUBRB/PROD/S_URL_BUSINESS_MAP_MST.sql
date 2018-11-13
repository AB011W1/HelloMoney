-----------------------		URL INSERTION QUERY FOR MUBRB - NFT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND URL_PATTERN='https://www.muon.secure.barclays.com/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','MUBRB','https://www.muon.secure.barclays.com/bmg','EN');

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND URL_PATTERN='https://muonc2bir-dr.barclays.intranet/bmg';

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBRB' AND URL_PATTERN='https://muonc2bir-live.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','MUBRB','https://muonc2bir-live.barclays.intranet/bmg','EN');

COMMIT;