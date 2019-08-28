create or replace package DELIVER_DATA_UB
IS
-- deliver completely
procedure DELIVER_C_LISTVALUE_MST;


-- execute
procedure EXECUTE_PROCEDURE;
end DELIVER_DATA_UB;
/
create or replace package body DELIVER_DATA_UB
AS

v_system_id varchar2(20) := 'UB';

 -- deliver C_LISTVALUE_MST
PROCEDURE DELIVER_C_LISTVALUE_MST
is
s_row number := 0;
cursor cur is select x.* from C_LISTVALUE_MST_ADMDB x,C_LISTVALUE_MST s where x.SYSTEM_ID=v_system_id and x.authorized_dtm is not null and  x.business_id = s.business_id and x.system_id = s.system_id and x.GROUP_ID = s.GROUP_ID and x.LIST_VALUE_KEY = s.LIST_VALUE_KEY;

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

END DELIVER_C_LISTVALUE_MST;


-- execute procedure
PROCEDURE EXECUTE_PROCEDURE
AS
BEGIN

DELIVER_C_LISTVALUE_MST;

END EXECUTE_PROCEDURE;
end DELIVER_DATA_UB;
/