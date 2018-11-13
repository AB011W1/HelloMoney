
CREATE OR REPLACE
PROCEDURE TXN_HIST_ERRMSG_PROC_SSA( var_business_id IN  VARCHAR2,var_system_id IN VARCHAR3)
IS

BEGIN


-- C_MESSAGE_MST --

delete from C_MESSAGE_MST where MESSAGE_KEY like ('TH00%') and SYSTEM_ID=var_system_id and BUSINESS_ID=var_business_id;

INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','TH00640',null,null,null,null,null,null);
INSERT INTO C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values (var_system_id,var_business_id,'ERROR','TH00641',null,null,null,null,null,null);

COMMIT;
-------------C_MESSAGE_RES_MST----------------

delete from C_MESSAGE_RES_MST where MESSAGE_KEY like ('TH00%') and SYSTEM_ID=var_system_id and BUSINESS_ID=var_business_id;

INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Transaction Type is Required.',var_system_id,var_business_id,'TH00640');
INSERT INTO C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','Transaction Reference Number is Empty.',var_system_id,var_business_id,'TH00641');

COMMIT;
--------






END;
/