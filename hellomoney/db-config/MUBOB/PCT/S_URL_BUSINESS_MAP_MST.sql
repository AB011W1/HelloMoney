
-----------------------		URL INSERTION QUERY FOR MUBOB - PCT   --------------------------
DELETE FROM S_URL_BUSINESS_MAP_MST WHERE SYSTEM_ID='MB' AND BUSINESS_ID='MUBOB' AND URL_PATTERN='https://widd.wload.global:52252/bmg';

INSERT INTO S_URL_BUSINESS_MAP_MST (SYSTEM_ID, BUSINESS_ID, URL_PATTERN, LANGUAGE_ID, SEGMENT_ID) VALUES ('MB','MUBOB','https://widd.wload.global:52252/bmg','EN','MASS');
COMMIT;