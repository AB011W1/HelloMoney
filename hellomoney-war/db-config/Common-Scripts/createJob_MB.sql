BEGIN
DBMS_SCHEDULER.DROP_JOB ('data_transfer_job_MB');
END;
/

BEGIN
DBMS_SCHEDULER.CREATE_JOB (
   job_name           =>  'data_transfer_job_MB',
   job_type           =>  'STORED_PROCEDURE',
   job_action         =>  'DELIVER_DATA_MB.EXECUTE_PROCEDURE',
   start_date         =>   SYSTIMESTAMP,
   enabled              => true,
   repeat_interval    =>  'FREQ=MINUTELY; INTERVAL=30', /* every 30mins */
   comments           =>  'data_transfer');
END;
/

COMMIT;