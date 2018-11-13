-- INSERTING into S_URL_BUSINESS_MAP_MST

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE BUSINESS_ID='KEBRB';

Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('KEBRB','UB',
'https://ubhm-ke.secure.barclays.com/hellomoney/cellulant','EN','MASS');

Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('KEBRB','UB',
'https://ubhm-ke-c2live.barclays.intranet/hellomoney/cellulant','EN','MASS');

COMMIT;







 