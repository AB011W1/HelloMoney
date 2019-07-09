-----------------------		URL INSERTION QUERY FOR MUBOB - PRODUCTION   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND URL_PATTERN='https://www.muoff.secure.barclays.com/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','MUBOB','https://www.muoff.secure.barclays.com/bmg','EN');

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND URL_PATTERN='https://muoffc2bir-dr.barclays.intranet/bmg';

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND URL_PATTERN='https://muoffc2bir-live.barclays.intranet/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID) VALUES ('MB','MUBOB','https://muoffc2bir-live.barclays.intranet/bmg','EN');

COMMIT;