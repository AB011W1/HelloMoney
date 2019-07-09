----- INCIDENT - INC0004331343 ------

UPDATE C_LISTVALUE_MST SET list_value_key='ZMW' WHERE group_id='CURRENCY'  AND  system_id='MB'  AND list_value_key='ZMK' AND BUSINESS_ID='ZMBRB';

UPDATE C_LISTVALUE_RES_MST set list_value_key='ZMW', value='ZMW' where group_id='CURRENCY' and  system_id='MB' and list_value_key='ZMK' AND BUSINESS_ID='ZMBRB';

COMMIT;
