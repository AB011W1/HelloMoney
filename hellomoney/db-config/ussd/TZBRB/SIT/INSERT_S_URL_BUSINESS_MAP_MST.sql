-- INSERTING into S_URL_BUSINESS_MAP_MST

DELETE FROM S_URL_BUSINESS_MAP_MST WHERE BUSINESS_ID='TZBRB';

Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('TZBRB','UB','https://cs8t.wload.barclays.co.uk/hellomoney','EN','MASS');
Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('TZBRB','UB','https://cs8t.wload.barclays.co.uk/hellomoney1','EN','MASS');
Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('TZBRB','UB','https://cs8t.wload.barclays.co.uk:45032/bmg','EN','MASS');
Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('TZBRB','UB','https://ubhmuat.tz.barclays.intranet/slcm','EN','MASS');
Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('TZBRB','UB','https://cs8t.wload.barclays.co.uk:45032/bmg/dataserv','EN','MASS');
Insert into S_URL_BUSINESS_MAP_MST (BUSINESS_ID,SYSTEM_ID,URL_PATTERN,LANGUAGE_ID,SEGMENT_ID) values ('TZBRB','UB','https://localhost:45032/bmg/dataserv','EN','MASS');

COMMIT;