alter table C_BANKROUTING_MST DROP PARTITION p_kebrb ;
alter table C_BILLER_MST DROP PARTITION p_kebrb ;
alter table C_BUSINESS_MST DROP PARTITION p_kebrb ;
alter table C_COMPONENT_MST DROP PARTITION p_kebrb ;
alter table C_COMPONENT_RES_MST DROP PARTITION p_kebrb ;
alter table C_FORMATTER_MST DROP PARTITION p_kebrb ;
alter table C_INTEREST_RATE_MST DROP PARTITION p_kebrb ;
alter table C_LISTVALUE_MST DROP PARTITION p_kebrb ;
alter table C_LISTVALUE_RES_MST DROP PARTITION p_kebrb ;
alter table C_MESSAGE_MST DROP PARTITION p_kebrb ;
alter table C_MESSAGE_RES_MST DROP PARTITION p_kebrb ;
alter table C_PRODUCT_MST DROP PARTITION p_kebrb ;
alter table S_FUNCTION_CONFIG DROP PARTITION p_kebrb ;
alter table S_LIMIT_CUST_TOTAL DROP PARTITION p_kebrb ;
alter table S_SES_SUMMARY_HST DROP PARTITION p_kebrb ;
alter table S_SYSPARAM_MST DROP PARTITION p_kebrb ;
alter table S_TXN_CUT_OFF_MST DROP PARTITION p_kebrb ;
alter table S_URL_BUSINESS_MAP_MST DROP PARTITION p_kebrb ;

commit;