
CREATE OR REPLACE
PROCEDURE BENEFICIARY_ERRMSG_PROC_SSA( var_business_id IN  VARCHAR2,var_system_id IN VARCHAR3)
IS

BEGIN


-- C_MESSAGE_MST --

delete from C_MESSAGE_MST where MESSAGE_KEY like ('BNF00%') and SYSTEM_ID=var_system_id and BUSINESS_ID=var_business_id;

INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00640',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00641',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00642',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00643',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00644',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00645',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00646',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00647',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00648',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00649',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00650',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00651',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','BNF00652',null,null,null,null,null,null);

COMMIT;
-------------C_MESSAGE_RES_MST----------------

delete from C_MESSAGE_RES_MST where MESSAGE_KEY like ('BNF00%') and SYSTEM_ID=var_system_id and BUSINESS_ID=var_business_id;

INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Beneficiary Id is Required.',var_system_id,var_business_id,'BNF00640');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Account Number is Required.',var_system_id,var_business_id,'BNF00641');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','No Payee Registered.',var_system_id,var_business_id,'BNF00642');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Payee is Already Registered.',var_system_id,var_business_id,'BNF00643');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Payee Type is Required.',var_system_id,var_business_id,'BNF00644');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Bank Code is Required.',var_system_id,var_business_id,'BNF00645');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Branch Code is Required.',var_system_id,var_business_id,'BNF00646');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Invalid Account.',var_system_id,var_business_id,'BNF00647');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Beneficiary Name is Required.',var_system_id,var_business_id,'BNF00648');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Beneficiary Nickname is Required.',var_system_id,var_business_id,'BNF00649');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Transaction Key is Empty.',var_system_id,var_business_id,'BNF00650');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Invalid Beneficiary.',var_system_id,var_business_id,'BNF00651');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Add Beneficiary could not be Initiated.',var_system_id,var_business_id,'BNF00652');

COMMIT;
--------






END;
/