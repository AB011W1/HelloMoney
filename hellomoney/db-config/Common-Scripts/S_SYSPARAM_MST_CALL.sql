
--------- S_SYSPARAM_MST --------------

	DELETE  FROM S_SYSPARAM_MST  where business_id='BWBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='BWBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='EGBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='EGBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='GHBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='GHBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='KEBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='KEBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='MUBOB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='MUBOB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='MUBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='MUBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='SCBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='SCBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='TZBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='TZBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='ZMBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='ZMBRB');


	DELETE  FROM S_SYSPARAM_MST  where business_id='ZWBRB' and system_id='MB';

	INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  WHERE SYSTEM_ID = 'IB'  AND  BUSINESS_ID='ZWBRB');


--Added for account type

insert into S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM)
values ('TZBRB', 'UB', 'SELFREG_ACCOUNTTYPE_SOLO', 'S', 'selfregAccTypeSolo', 'COMMON', 'N', 'AccType', null, null, null, null, null, 'Self registration allowed account type solo', null, to_timestamp_tz('12-04-03 08:16:28+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'))



insert into S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM)
values ('UGBRB', 'UB', 'SELFREG_ACCOUNTTYPE_SOLO', 'S', 'selfregAccTypeSolo', 'COMMON', 'N', 'AccType', null, null, null, null, null, 'Self registration allowed account type solo', null, to_timestamp_tz('12-04-03 08:16:28+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'))


insert into S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM)
values ('TZBRB', 'UB', 'SELFREG_ACCOUNTTYPE_JOINTOR', 'O', 'selfregAccTypeJointOr', 'COMMON', 'N', 'AccType', null, null, null, null, null, 'Self registration allowed account type Joint Or', null, to_timestamp_tz('12-04-03 08:16:28+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'))

insert into S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM)
values ('UGBRB', 'UB', 'SELFREG_ACCOUNTTYPE_JOINTOR', 'O', 'selfregAccTypeJointOr', 'COMMON', 'N', 'AccType', null, null, null, null, null, 'Self registration allowed account type Joint Or', null, to_timestamp_tz('12-04-03 08:16:28+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'))



insert into S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM)
values ('TZBRB', 'UB', 'SELFREG_ACCOUNTTYPE_MANYOR', 'T', 'selfregAccTypeManyOr', 'COMMON', 'N', 'AccType', null, null, null, null, null, 'Self registration allowed account type Many OR', null, to_timestamp_tz('12-04-03 08:16:28+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'))

insert into S_SYSPARAM_MST (BUSINESS_ID, SYSTEM_ID, PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID, SYSTEM_MAINTAIN, DISP_TYPE, MODIFIED_DTM, MODIFIED_BY, AUTHORISED_BY, AUTHORISED_DTM, DELETE_FLG, PARAM_DESC, CREATED_BY, CREATED_DTM)
values ('UGBRB', 'UB', 'SELFREG_ACCOUNTTYPE_MANYOR', 'T', 'selfregAccTypeManyOr', 'COMMON', 'N', 'AccType', null, null, null, null, null, 'Self registration allowed account type Many Or', null, to_timestamp_tz('12-04-03 08:16:28+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'))




	COMMIT;






