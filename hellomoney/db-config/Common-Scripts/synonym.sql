CREATE OR REPLACE SYNONYM C_INTEREST_RATE_MST_ADMDB FOR C_INTEREST_RATE_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_PRODUCT_ELIGIBILITY_ADMDB FOR C_PRODUCT_ELIGIBILITY@BIR_LINK;
CREATE OR REPLACE SYNONYM S_LIMIT_GLOBAL_MST_ADMDB FOR S_LIMIT_GLOBAL_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM S_LIMIT_CUST_MST_ADMDB FOR S_LIMIT_CUST_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_COMPONENT_MST_ADMDB FOR C_COMPONENT_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_COMPONENT_RES_MST_ADMDB FOR C_COMPONENT_RES_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_FORMATTER_MST_ADMDB FOR C_FORMATTER_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_BUSINESS_MST_ADMDB FOR C_BUSINESS_MST@BIR_LINK;
--CREATE OR REPLACE SYNONYM S_PRODUCT_CATEGORY_MST_ADMDB FOR S_PRODUCT_CATEGORY_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_PRODUCT_MST_ADMDB FOR C_PRODUCT_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_LISTVALUE_MST_ADMDB FOR C_LISTVALUE_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_LISTVALUE_RES_MST_ADMDB FOR C_LISTVALUE_RES_MST@BIR_LINK;

CREATE OR REPLACE SYNONYM C_MESSAGE_MST_ADMDB FOR C_MESSAGE_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_BIZ_LANG_MMAP_ADMDB FOR C_BIZ_LANG_MMAP@BIR_LINK;
CREATE OR REPLACE SYNONYM C_ROLE_FUNC_MMAP_ADMDB FOR C_ROLE_FUNC_MMAP@BIR_LINK;

CREATE OR REPLACE SYNONYM S_FEATURE_BLACKOUT_MST_ADMDB FOR S_FEATURE_BLACKOUT_MST@BIR_LINK;
CREATE OR REPLACE SYNONYM C_ENHANCED_ENTITLE_MST_ADMDB FOR C_ENHANCED_ENTITLEMENT_MST@BIR_LINK;

CREATE OR REPLACE SYNONYM S_PWD_POLICY_MST_ADMDB FOR S_PWD_POLICY_MST@BIR_LINK;

CREATE OR REPLACE SYNONYM C_MESSAGE_RES_MST_ADMDB FOR C_MESSAGE_RES_MST@BIR_LINK;

COMMIT;
