-- INSERTING into S_URL_BUSINESS_MAP_MST

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE BUSINESS_ID='TZBRB';

Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('TZBRB','UB','https://ubhm-tznft.barclays.intranet/hellomoney','EN','MASS');

COMMIT;