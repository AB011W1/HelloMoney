DELETE  FROM S_SYSPARAM_MST
where business_id = 'KEBRB'
and system_id = 'MB'
and param_id in ('accountMasking_maskRequired', 'accountNumberMaskPattern', 'MASK_ACCOUNT_SPLIT');

INSERT INTO  S_SYSPARAM_MST (SELECT BUSINESS_ID,'MB',PARAM_ID, PARAM_VALUE, PARAM_COMPONENT_KEY, ACTIVITY_ID,SYSTEM_MAINTAIN,DISP_TYPE,MODIFIED_DTM,MODIFIED_BY,AUTHORISED_BY,AUTHORISED_DTM,DELETE_FLG,PARAM_DESC,CREATED_BY,CREATED_DTM  FROM S_SYSPARAM_MST  where business_id = 'KEBRB'
and system_id = 'IB'
and param_id in ('accountMasking_maskRequired', 'accountNumberMaskPattern', 'MASK_ACCOUNT_SPLIT'));

commit;
