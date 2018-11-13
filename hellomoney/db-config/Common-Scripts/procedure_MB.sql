create or replace package DELIVER_DATA_MB
IS
-- deliver completely
procedure DELIVER_C_INTEREST_RATE_MST;
procedure DELIVER_S_LIMIT_GLOBAL_MST;
procedure DELIVER_C_BUSINESS_MST;
procedure DELIVER_C_PRODUCT_MST;
procedure DELIVER_C_PRODUCT_ELIGIBILITY;
procedure DELIVER_C_BIZ_LANG_MMAP;
procedure DELIVER_C_ROLE_FUNC_MMAP;
procedure DELIVER_C_ENHANCED_ENTITLE_MST;
procedure DELIVER_S_FEATURE_BLACKOUT_MST;
procedure DELIVER_S_PWD_POLICY_MST;

-- deliver partly
----procedure DELIVER_S_LIMIT_CUST_MST;
procedure DELIVER_C_LISTVALUE_MST;
procedure DELIVER_C_LISTVALUE_RES_MST;

-- execute
procedure EXECUTE_PROCEDURE;
end DELIVER_DATA_MB;
/
create or replace package body DELIVER_DATA_MB
AS
-- changed by Qingming.Liu, set the variable for system id, add the condition for every delete sql script
v_system_id varchar2(20) := 'MB';
-- deliver C_INTEREST_RATE_MST
PROCEDURE DELIVER_C_INTEREST_RATE_MST
AS
BEGIN
 delete from C_INTEREST_RATE_MST;
 insert into C_INTEREST_RATE_MST (select * from C_INTEREST_RATE_MST_ADMDB  where business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_C_INTEREST_RATE_MST;

-- deliver S_LIMIT_GLOBAL_MST
PROCEDURE DELIVER_S_LIMIT_GLOBAL_MST
AS
BEGIN
 delete from S_LIMIT_GLOBAL_MST where SYSTEM_ID=v_system_id;
 insert into S_LIMIT_GLOBAL_MST (select * from S_LIMIT_GLOBAL_MST_ADMDB  where SYSTEM_ID=v_system_id and business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_S_LIMIT_GLOBAL_MST;

-- deliver C_BUSINESS_MST
PROCEDURE DELIVER_C_BUSINESS_MST
AS
BEGIN
 delete from C_BUSINESS_MST;
 insert into C_BUSINESS_MST (select * from C_BUSINESS_MST_ADMDB  where business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_C_BUSINESS_MST;

-- deliver C_PRODUCT_MST
PROCEDURE DELIVER_C_PRODUCT_MST
AS
BEGIN
 delete from C_PRODUCT_MST;
 insert into C_PRODUCT_MST (select * from C_PRODUCT_MST_ADMDB  where business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_C_PRODUCT_MST;

-- deliver C_PRODUCT_ELIGIBILITY
PROCEDURE DELIVER_C_PRODUCT_ELIGIBILITY
AS
BEGIN
 delete from C_PRODUCT_ELIGIBILITY where SYSTEM_ID=v_system_id;
 insert into C_PRODUCT_ELIGIBILITY (select * from C_PRODUCT_ELIGIBILITY_ADMDB  where  SYSTEM_ID=v_system_id and business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_C_PRODUCT_ELIGIBILITY;

-- deliver C_BIZ_LANG_MMAP
PROCEDURE DELIVER_C_BIZ_LANG_MMAP
AS
BEGIN
 delete from C_BIZ_LANG_MMAP;
 insert into C_BIZ_LANG_MMAP (select * from C_BIZ_LANG_MMAP_ADMDB  where business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_C_BIZ_LANG_MMAP;

-- deliver C_ROLE_FUNC_MMAP
PROCEDURE DELIVER_C_ROLE_FUNC_MMAP
AS
BEGIN
 delete from C_ROLE_FUNC_MMAP where SYSTEM_ID=v_system_id;
 insert into C_ROLE_FUNC_MMAP (select * from C_ROLE_FUNC_MMAP_ADMDB where SYSTEM_ID=v_system_id);
 commit;
END DELIVER_C_ROLE_FUNC_MMAP;

-- deliver C_ENHANCED_ENTITLE_MST
PROCEDURE DELIVER_C_ENHANCED_ENTITLE_MST
AS
BEGIN
 delete from C_ENHANCED_ENTITLEMENT_MST where SYSTEM_ID=v_system_id;
 insert into C_ENHANCED_ENTITLEMENT_MST (select * from C_ENHANCED_ENTITLE_MST_ADMDB  where SYSTEM_ID=v_system_id and business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_C_ENHANCED_ENTITLE_MST;

-- deliver S_FEATURE_BLACKOUT_MST
PROCEDURE DELIVER_S_FEATURE_BLACKOUT_MST
AS
BEGIN
 delete from S_FEATURE_BLACKOUT_MST where SYSTEM_ID=v_system_id;
 insert into S_FEATURE_BLACKOUT_MST (select * from S_FEATURE_BLACKOUT_MST_ADMDB  where SYSTEM_ID=v_system_id and business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_S_FEATURE_BLACKOUT_MST;

-- deliver S_PWD_POLICY_MST
PROCEDURE DELIVER_S_PWD_POLICY_MST
AS
BEGIN
 delete from S_PWD_POLICY_MST where SYSTEM_ID=v_system_id;
 insert into S_PWD_POLICY_MST (select * from S_PWD_POLICY_MST_ADMDB  where SYSTEM_ID=v_system_id and business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id));
 commit;
END DELIVER_S_PWD_POLICY_MST;

-- below are partly deliver
 -- deliver S_LIMIT_CUST_MST
---PROCEDURE DELIVER_S_LIMIT_CUST_MST
----is
---s_row number := 0;

----------- ADDED for channel id 'MB'; Copy S_LIMIT_CUST table data from IB-BOC to MB-BIR -----------------------

---cursor cur is select s.* from S_LIMIT_CUST_MST_ADMDB s where s.SYSTEM_ID='IB';

---BEGIN
 --insert or update
   --- delete from S_LIMIT_CUST_MST where SYSTEM_ID = 'MB';

 ---for result in cur loop
  --- s_row := cur%rowcount;

  --- INSERT INTO S_LIMIT_CUST_MST (BUSINESS_ID,SYSTEM_ID,TXN_LIMIT_TYP,CUST_ID,DAILY_LIMIT_AMT,TXN_LIMIT_AMT,THRESHOLD_LIMIT_AMT,MODIFIED_BY,MODIFIED_DTM,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG) VALUES (result.business_id,'MB',result.TXN_LIMIT_TYP, result.CUST_ID,result.DAILY_LIMIT_AMT,result.TXN_LIMIT_AMT,result.THRESHOLD_LIMIT_AMT,result.MODIFIED_BY,result.MODIFIED_DTM,result.AUTHORISED_BY,result.AUTHORISED_DTM,result.DELETE_FLG);

--- end loop;

----commit;

 ------------------------- END MB SECTION to copy IB data from BOC into BIR - MB ----------------------------------

 ---if s_row > 0 then
  ---  update S_DELIVER_TIME_RECORD_MB set DELIVER_DTM = sysdate where TABLE_NAME = 'S_LIMIT_CUST_MST';
 --- end if;
 ---commit;

----END DELIVER_S_LIMIT_CUST_MST;

 -- deliver C_LISTVALUE_MST
PROCEDURE DELIVER_C_LISTVALUE_MST
is
s_row number := 0;
cursor cur is select x.* from C_LISTVALUE_MST_ADMDB x,C_LISTVALUE_MST s where x.SYSTEM_ID=v_system_id and x.authorized_dtm is not null and x.authorized_dtm > (select DELIVER_DTM from S_DELIVER_TIME_RECORD_MB where TABLE_NAME = 'C_LISTVALUE_MST') and x.business_id = s.business_id and x.system_id = s.system_id and x.GROUP_ID = s.GROUP_ID and x.LIST_VALUE_KEY = s.LIST_VALUE_KEY;
-- changed by Qingming.Liu, change the dur cursor query to improve performance
--cursor dur is select s.* from C_LISTVALUE_MST s where s.SYSTEM_ID=v_system_id and not exists (select 1 from C_LISTVALUE_MST_ADMDB x where x.business_id = s.business_id and x.system_id = s.system_id and x.GROUP_ID = s.GROUP_ID and x.LIST_VALUE_KEY = s.LIST_VALUE_KEY);
cursor dur is SELECT s.* FROM C_LISTVALUE_MST s LEFT OUTER JOIN C_LISTVALUE_MST_ADMDB x ON x.SYSTEM_ID = s.SYSTEM_ID AND x.BUSINESS_ID=s.BUSINESS_ID AND x.GROUP_ID=s.GROUP_ID AND x.LIST_VALUE_KEY = s.LIST_VALUE_KEY WHERE s.SYSTEM_ID=v_system_id AND x.business_id is null;
cursor icur is select s.* from C_LISTVALUE_MST_ADMDB s where s.SYSTEM_ID=v_system_id and s.business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id) and not exists (select 1 from C_LISTVALUE_MST x where x.business_id = s.business_id and x.system_id = s.system_id and x.GROUP_ID = s.GROUP_ID and x.LIST_VALUE_KEY = s.LIST_VALUE_KEY);
---- Added to fix defect 871 ----
cursor curCnt is select s.* from C_LISTVALUE_MST_ADMDB s where s.SYSTEM_ID='IB' AND s.LIST_VALUE_KEY='AX' AND s.GROUP_ID='CMN_COUNTRY';

BEGIN
 --insert or update
 for result in cur loop
   s_row := cur%rowcount;
   --C_LISTVALUE_MST
   delete from C_LISTVALUE_MST where business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY=result.LIST_VALUE_KEY;
   insert into C_LISTVALUE_MST select * from C_LISTVALUE_MST_ADMDB where  business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY=result.LIST_VALUE_KEY;
    --C_LISTVALUE_RES_MST
   delete from C_LISTVALUE_RES_MST where business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY=result.LIST_VALUE_KEY;
   insert into C_LISTVALUE_RES_MST select * from C_LISTVALUE_RES_MST_ADMDB where  business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY=result.LIST_VALUE_KEY;
 end loop;
 commit;
  --delete
  for result in dur loop
   s_row := dur%rowcount;
   delete from C_LISTVALUE_MST where  business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY=result.LIST_VALUE_KEY;
 end loop;
 commit;
 --insert new row
  for result in icur loop
   s_row := icur%rowcount;
   insert into C_LISTVALUE_MST select * from C_LISTVALUE_MST_ADMDB where  business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY=result.LIST_VALUE_KEY;
 end loop;

 ----- Fixed for Country Name in Country List for special character

 --insert or update
   DELETE FROM C_LISTVALUE_MST WHERE SYSTEM_ID='MB' AND GROUP_ID='CMN_COUNTRY' AND LIST_VALUE_KEY='AX';

 for result in curCnt loop

	INSERT INTO C_LISTVALUE_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,FILTER_KEY_1,FILTER_KEY_2,DEFAULT_FLG,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,EDITABLE_FLG) values ('MB',result.business_id,'CMN_COUNTRY','AX',null,null,'N',null,null,null,null,'N','Y');

 end loop;
  commit;
---------------------------

   if s_row > 0 then
    update S_DELIVER_TIME_RECORD_MB set DELIVER_DTM = sysdate where TABLE_NAME = 'C_LISTVALUE_MST';
  end if;
 commit;
END DELIVER_C_LISTVALUE_MST;

-- deliver C_LISTVALUE_RES_MST
PROCEDURE DELIVER_C_LISTVALUE_RES_MST
is
-- delete
-- changed by Qingming.Liu, change the dur cursor query to improve performance
--cursor dur is select s.* from C_LISTVALUE_RES_MST s where s.SYSTEM_ID=v_system_id and not exists (select 1 from C_LISTVALUE_RES_MST_ADMDB x where x.SYSTEM_ID = s.SYSTEM_ID and x.BUSINESS_ID = s.BUSINESS_ID and x.GROUP_ID = s.GROUP_ID  and x.LIST_VALUE_KEY = s.LIST_VALUE_KEY and x.LANGUAGE_ID = s.LANGUAGE_ID);
cursor dur is SELECT s.* FROM C_LISTVALUE_RES_MST s LEFT OUTER JOIN C_LISTVALUE_RES_MST_ADMDB x on  x.SYSTEM_ID = s.SYSTEM_ID AND x.BUSINESS_ID = s.BUSINESS_ID AND x.GROUP_ID = s.GROUP_ID AND x.LIST_VALUE_KEY = s.LIST_VALUE_KEY AND x.LANGUAGE_ID= s.LANGUAGE_ID where s.system_id=v_system_id and x.business_id is null;
-- insert
cursor icur is select s.* from C_LISTVALUE_RES_MST_ADMDB s where s.SYSTEM_ID=v_system_id and s.business_id in(select distinct(business_id) from s_function_config where system_id=v_system_id) and not exists (select 1 from C_LISTVALUE_RES_MST x where x.SYSTEM_ID = s.SYSTEM_ID and x.BUSINESS_ID = s.BUSINESS_ID and x.GROUP_ID = s.GROUP_ID  and x.LIST_VALUE_KEY = s.LIST_VALUE_KEY and x.LANGUAGE_ID = s.LANGUAGE_ID);

cursor curCnt is select s.* from C_LISTVALUE_RES_MST_ADMDB s where s.SYSTEM_ID='IB' AND s.LIST_VALUE_KEY='AX' AND s.GROUP_ID='CMN_COUNTRY';


BEGIN
  --delete
  for result in dur loop
   delete from C_LISTVALUE_RES_MST where  business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY = result.LIST_VALUE_KEY and LANGUAGE_ID = result.LANGUAGE_ID;
 end loop;
 commit;
  --insert new row
 for result in icur loop
    insert into C_LISTVALUE_RES_MST select * from C_LISTVALUE_RES_MST_ADMDB where  business_id= result.business_id and system_id = result.system_id and GROUP_ID = result.GROUP_ID and LIST_VALUE_KEY = result.LIST_VALUE_KEY and LANGUAGE_ID = result.LANGUAGE_ID;
 end loop;
 commit;

 ------------------ To fix defect 871 ---------
 DELETE FROM C_LISTVALUE_RES_MST WHERE SYSTEM_ID='MB' AND GROUP_ID='CMN_COUNTRY' AND LIST_VALUE_KEY='AX';

 for result in curCnt loop

INSERT INTO  C_LISTVALUE_RES_MST (SYSTEM_ID,BUSINESS_ID,GROUP_ID,LIST_VALUE_KEY,LANGUAGE_ID,VALUE,LIST_VALUE_ORDER) values ('MB',result.business_id,'CMN_COUNTRY','AX','EN','ALAND ISLANDS',result.LIST_VALUE_ORDER);

 end loop;
 COMMIT;

END DELIVER_C_LISTVALUE_RES_MST;

-- execute procedure
PROCEDURE EXECUTE_PROCEDURE
AS
BEGIN
--DELIVER_C_INTEREST_RATE_MST;
DELIVER_S_LIMIT_GLOBAL_MST;
--DELIVER_C_BUSINESS_MST;
--DELIVER_C_PRODUCT_MST;
DELIVER_C_PRODUCT_ELIGIBILITY;
--DELIVER_C_BIZ_LANG_MMAP;
DELIVER_C_ROLE_FUNC_MMAP;
DELIVER_C_ENHANCED_ENTITLE_MST;
DELIVER_S_FEATURE_BLACKOUT_MST;
DELIVER_S_PWD_POLICY_MST;
----DELIVER_S_LIMIT_CUST_MST;
DELIVER_C_LISTVALUE_MST;
DELIVER_C_LISTVALUE_RES_MST;
END EXECUTE_PROCEDURE;
end DELIVER_DATA_MB;
/