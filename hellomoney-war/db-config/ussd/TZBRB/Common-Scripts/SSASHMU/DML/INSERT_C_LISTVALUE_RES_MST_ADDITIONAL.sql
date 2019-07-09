delete from C_LISTVALUE_RES_MST where business_id = 'TZBRB' and system_id = 'UB' and group_id = 'SYS_PARAM_CURRENT' and list_value_key = 'PRODUCT_CODES' and language_id = 'EN';
delete from C_LISTVALUE_RES_MST where business_id = 'TZBRB' and system_id = 'UB' and group_id = 'SYS_PARAM_CURRENT' and list_value_key = 'PRODUCT_CODES' and language_id = 'SW';

insert into C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('UB','TZBRB','SYS_PARAM_CURRENT','PRODUCT_CODES','EN','0,3,45,54,62,64,65,66,68,70,73,75,79,89,93',1);
insert into C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('UB','TZBRB','SYS_PARAM_CURRENT','PRODUCT_CODES','SW','0,3,45,54,62,64,65,66,68,70,73,75,79,89,93',1);

commit;