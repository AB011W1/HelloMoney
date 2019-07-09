delete from C_MESSAGE_RES_MST where business_id = 'GHBRB' and system_id = 'UB' and message_key = 'REG01171' and language_id = 'EN';
delete from C_MESSAGE_RES_MST where business_id = 'GHBRB' and system_id = 'UB' and message_key = 'REG01171' and language_id = 'SW';

insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to register you. Please call us on +(233)  (0) 302 429 150,(0)800 101 17 for assistance.','UB','GHBRB','REG01171');
insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('SW','We are unable to register you. Please call us on +(233)  (0) 302 429 150,(0)800 101 17 for assistance.','UB','GHBRB','REG01171');

commit;