CREATE OR REPLACE PROCEDURE
C_PROD_ELIG_Procedure(var_business_id IN  VARCHAR2 )

IS
      cursor c_prod_elig_cursor  is select * from C_PRODUCT_ELIGIBILITY WHERE SYSTEM_ID='IB'  AND  BUSINESS_ID=var_business_id  AND ROLE_CATEGORY_CD='IB';
      r_prod c_prod_elig_cursor%ROWTYPE;

      cursor c_prod_elig_cursor_02  is select * from C_PRODUCT_ELIGIBILITY WHERE SYSTEM_ID='IB'  AND  BUSINESS_ID=var_business_id  AND ROLE_CATEGORY_CD!='IB'  AND ROLE_CATEGORY_CD!='MB';
      r_prod_02 c_prod_elig_cursor_02%ROWTYPE;

    begin

		DELETE FROM C_PRODUCT_ELIGIBILITY WHERE SYSTEM_ID='MB' AND  BUSINESS_ID=var_business_id;

		-------c_prod_elig_cursor_02-------

        open c_prod_elig_cursor;
      	loop
            fetch c_prod_elig_cursor into r_prod;
            exit when c_prod_elig_cursor%NOTFOUND;

			INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,business_id, activity_id, role_category_cd, prd_crdr_ind, prd_code, prd_category, inc_or_exc,
			account_status,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2, card_type, card_status, CARD_BLOCK_CODE,system_id,EXCEPTION) VALUES (c_product_eligibility_SEQUENCE.NEXTVAL,r_prod.BUSINESS_ID,r_prod.ACTIVITY_ID,'MB',
			r_prod.PRD_CRDR_IND,r_prod.PRD_CODE,r_prod.PRD_CATEGORY,r_prod.INC_OR_EXC, r_prod.ACCOUNT_STATUS,r_prod.ACCOUNT_BLOCK_CODE1,r_prod.ACCOUNT_BLOCK_CODE2,
			r_prod.CARD_TYPE,r_prod.CARD_STATUS,r_prod.CARD_BLOCK_CODE,'MB', r_prod.EXCEPTION);

    	end loop;
     	commit;
       	close c_prod_elig_cursor;

       	-------c_prod_elig_cursor_02-------

       	open c_prod_elig_cursor_02;
        loop
            fetch c_prod_elig_cursor_02 into r_prod_02;
            exit when c_prod_elig_cursor_02%NOTFOUND;

			INSERT INTO C_PRODUCT_ELIGIBILITY (SERIAL_NUMBER,business_id, activity_id, role_category_cd, prd_crdr_ind, prd_code, prd_category, inc_or_exc,
			account_status,ACCOUNT_BLOCK_CODE1,ACCOUNT_BLOCK_CODE2, card_type, card_status, CARD_BLOCK_CODE,system_id,EXCEPTION) VALUES (c_product_eligibility_SEQUENCE.NEXTVAL,r_prod_02.BUSINESS_ID,r_prod_02.ACTIVITY_ID,r_prod_02.ROLE_CATEGORY_CD,
			r_prod_02.PRD_CRDR_IND,r_prod_02.PRD_CODE,r_prod_02.PRD_CATEGORY,r_prod_02.INC_OR_EXC, r_prod_02.ACCOUNT_STATUS,r_prod_02.ACCOUNT_BLOCK_CODE1,r_prod_02.ACCOUNT_BLOCK_CODE2,
			r_prod_02.CARD_TYPE,r_prod_02.CARD_STATUS,r_prod_02.CARD_BLOCK_CODE,'MB', r_prod_02.EXCEPTION);

    	end loop;
     	commit;
       	close c_prod_elig_cursor_02;

 END;
 /

