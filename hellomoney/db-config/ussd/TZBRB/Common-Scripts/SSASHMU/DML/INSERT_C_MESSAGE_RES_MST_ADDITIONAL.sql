delete from C_MESSAGE_RES_MST where business_id = 'TZBRB' and system_id = 'UB' and message_key = 'REG01171' and language_id = 'EN';
delete from C_MESSAGE_RES_MST where business_id = 'TZBRB' and system_id = 'UB' and message_key = 'REG01171' and language_id = 'SW';

insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to register you. Please call us on +255 (0) 774 700 703/708 for assistance.','UB','TZBRB','REG01171');
insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('SW','We are unable to register you. Please call us on +255 (0) 774 700 703/708 for assistance.','UB','TZBRB','REG01171');

commit;