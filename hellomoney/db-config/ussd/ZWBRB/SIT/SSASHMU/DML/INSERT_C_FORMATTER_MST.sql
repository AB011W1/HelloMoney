-- INSERTING into C_FORMATTER_MST

DELETE FROM C_FORMATTER_MST WHERE BUSINESS_ID='ZWBRB';

INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (25,'[0-9]{10}','UB','ZWBRB',null,null,null,null,null,null,null,null,'BP_REF_NO_VALIDATION_1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (26,'[a-zA-Z0-9]{8}','UB','ZWBRB',null,null,null,null,null,null,null,null,'BP_REF_NO_VALIDATION_1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (27,'[a-zA-Z0-9]{5,10}','UB','ZWBRB',null,null,null,null,null,null,null,null,'BP_REF_NO_VALIDATION_1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (28,'[0-9]{8}|[0-9]{11}','UB','ZWBRB',null,null,null,null,null,null,null,null,'INVALID_ACCT_SMARTCARD');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (29,'[0-9]{10}|[0-9]{11}','UB','ZWBRB',null,null,null,null,null,null,null,null,'INVALID_ACCT_SMARTCARD2');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (30,'[0-9]{11}','UB','ZWBRB',null,null,null,null,null,null,null,null,'BP_REF_NO_VALIDATION_1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (31,'[a-zA-Z0-9]{0,15}','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputStringError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (32,'([./:'',a-zA-Z0-9\?\(\)\+\s]{1}[./:'',a-zA-Z0-9\?\(\)\+\s\-]*){0,1}','UB','ZWBRB',null,null,null,null,null,null,null,null,'INTL_FT_INPUT_INVALID');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (1,'dd/MM/yyyy','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG3');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (2,'###,##0.00','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (3,'dd/MM/yyyy HH:mm:ss','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG3');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (4,'#0.00','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (5,'#0.0000','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (6,'#0.0000000','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG1');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (7,'[\d]+','UB','ZWBRB',null,null,null,null,null,null,null,null,'DIGITAL_ONE_PLUS');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (8,'MM-dd-yyyy','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG3');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (9,'mm-dd-yy','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG3');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (10,'[\d]{6}','UB','ZWBRB',null,null,null,null,null,null,null,null,'DIGITAL_ONE_PLUS');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (11,'\w+(\.\w+)*@\w+(\.\w+)+','UB','ZWBRB',null,null,null,null,null,null,null,null,'mailFormatError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (12,'[\d]*[\.]{0,1}[\d]{1,2}','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputAmount');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (13,'MM/dd/yyyy','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG3');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (14,'MM/yyyy','UB','ZWBRB',null,null,null,null,null,null,null,null,'MSG3');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (15,'([a-z]|[A-Z]|[0-9]){6,10}','UB','ZWBRB',null,null,null,null,null,null,null,null,'USERNAME_FORMAT');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (16,'[\d]*[\.]{0,1}[\d]{1,2}','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputAmountError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (17,'[a-zA-Z0-9]*','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputStringError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (18,'[\d]{1,12}([\.][\d]{1,2})?','UB','ZWBRB',null,null,null,null,null,null,null,null,'transferAmountError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (19,'[a-zA-Z0-9\s]*','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputPayeeNameError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (20,'[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputSwiftCodeError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (21,'[(\w)*[\s'',\?\.\/_:\-\+\r\n]]*','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputPayeeAddressError');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (22,'[1-9]{1}[\d]*','UB','ZWBRB',null,null,null,null,null,null,null,null,'INTEGER_ONE_PLUS');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (23,'[\w]{1,33}[\s]{1,33}[\w]{1,33}','UB','ZWBRB',null,null,null,null,null,null,null,null,'BENE_NAME_VALIDATION');
INSERT into C_FORMATTER_MST (FORMATTER_ID,FORMATTER_STRING,SYSTEM_ID,BUSINESS_ID,MAKER_ID,CHECKER_ID,STATUS,LAST_MODIFIED,LAST_AUTHORIZED,ACTION,DELETED_FLAG,AUTHORIZED_INDICATOR,MESSAGE_KEY) values (24,'[a-zA-Z0-9-_\s]*','UB','ZWBRB',null,null,null,null,null,null,null,null,'inputPayeeNameError');

commit;