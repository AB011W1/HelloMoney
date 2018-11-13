SET DEFINE OFF;
SET ECHO ON;

spool ssaboca_exe.log;
show user;

@@ DML/BOC_Script.sql

COMMIT;

spool off;
