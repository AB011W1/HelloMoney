----- INCIDENT - INC0004331343 ------


UPDATE C_LISTVALUE_MST SET list_value_key='ZMK' WHERE group_id='CURRENCY'  AND  system_id='MB'  AND list_value_key='ZMW' AND BUSINESS_ID = 'ZMBRB';

UPDATE C_LISTVALUE_RES_MST set list_value_key='ZMK', value='ZMK' where group_id='CURRENCY' and  system_id='MB' and list_value_key='ZMW' AND BUSINESS_ID = 'ZMBRB';

COMMIT;
