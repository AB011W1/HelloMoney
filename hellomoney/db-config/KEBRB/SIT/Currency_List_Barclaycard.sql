
---DELETE FROM S_SYSPARAM_MST WHERE PARAM_ID='currency_list_barclaycard' AND ACTIVITY_ID='PMT_BP_BCD' AND BUSINESS_ID='KEBRB' AND SYSTEM_ID='MB';

---UPDATE S_SYSPARAM_MST SET PARAM_VALUE='AED,PKR,SGD,USD,KES'


UPDATE S_SYSPARAM_MST SET PARAM_VALUE='AED,AUD,BHD,CAD,CHF,DKK,EGP,EUR,GBP,HKD,INR,JPY,KWD,MUR,NOK,NZD,OMR,PKR,QAR,SAR,SEK,SGD,USD,ZAR,KES' WHERE PARAM_ID='PMT_FT_INTL_SUPPORT_CURRENCY' AND ACTIVITY_ID='PMT_FT_INTL_DT_ONETIME' AND  BUSINESS_ID='KEBRB' AND SYSTEM_ID='MB';
COMMIT;


