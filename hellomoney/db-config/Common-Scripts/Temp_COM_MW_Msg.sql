
	DELETE  FROM C_MESSAGE_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06129';

	INSERT INTO  C_MESSAGE_MST (SELECT 'MB',BUSINESS_ID, CATEGORY, MESSAGE_KEY, MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG, SOURCE_SYSTEM_ID  FROM C_MESSAGE_MST   WHERE SYSTEM_ID = 'IB' AND MESSAGE_KEY='COM_MW_06129');

	DELETE  FROM C_MESSAGE_RES_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06129';

	INSERT INTO  C_MESSAGE_RES_MST (SELECT LANGUAGE_ID, MESSAGE_VALUE, 'MB', BUSINESS_ID, MESSAGE_KEY  FROM C_MESSAGE_RES_MST   WHERE SYSTEM_ID = 'IB' AND MESSAGE_KEY='COM_MW_06129');



	DELETE  FROM C_MESSAGE_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06130';

	INSERT INTO  C_MESSAGE_MST (SELECT 'MB',BUSINESS_ID, CATEGORY, MESSAGE_KEY, MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG, SOURCE_SYSTEM_ID  FROM C_MESSAGE_MST   WHERE SYSTEM_ID = 'IB' AND MESSAGE_KEY='COM_MW_06130');

	DELETE  FROM C_MESSAGE_RES_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06130';

	INSERT INTO  C_MESSAGE_RES_MST (SELECT LANGUAGE_ID, MESSAGE_VALUE, 'MB', BUSINESS_ID, MESSAGE_KEY  FROM C_MESSAGE_RES_MST   WHERE SYSTEM_ID = 'IB' AND MESSAGE_KEY='COM_MW_06130');



------------------------


-- INSERTING into C_MESSAGE_MST - COM_MW_06131

DELETE  FROM C_MESSAGE_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06131';

Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','BWBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','EGBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBOB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','TZBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZWBRB','ERROR','COM_MW_06131',null,null,null,null,null,'MCFE');



-- INSERTING into C_MESSAGE_RES_MST - COM_MW_06131
DELETE  FROM C_MESSAGE_RES_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06131';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0800 600 444 within Botswana or (+267) 315 9575 from outside Botswana for assistance.','MB','BWBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 16222 (Retail Customer), 16122 (Premier Customer) within Egypt or (+202) 16222 (Retail Customer) from outside Egypt for assistance.','MB','EGBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on {Domestic Customer Center Number} within {Country Name} or {Overseas Customer Center Number} from outside {Country Name} for assistance.','MB','KEBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBOB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 248 383939 within Seychelles or 248 383939 from outside Seychelles for assistance.','MB','SCBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0774 700 703 or 0774 700 708 within Tanzania or (+255) 774 700 703 or (+255) 774 700 708 from outside Tanzania for assistance.','MB','TZBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.','MB','ZMBRB','COM_MW_06131');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 250 579 within Zimbabwe or 00263 4 250 579 from outside Zimbabwe for assistance.','MB','ZWBRB','COM_MW_06131');


-- INSERTING into C_MESSAGE_MST - COM_MW_06132

DELETE  FROM C_MESSAGE_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06132';
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','BWBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','EGBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBOB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','TZBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZWBRB','ERROR','COM_MW_06132',null,null,null,null,null,'MCFE');



-- INSERTING into C_MESSAGE_RES_MST - COM_MW_06132
DELETE  FROM C_MESSAGE_RES_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06132';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0800 600 444 within Botswana or (+267) 315 9575 from outside Botswana for assistance.','MB','BWBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 16222 (Retail Customer), 16122 (Premier Customer) within Egypt or (+202) 16222 (Retail Customer) from outside Egypt for assistance.','MB','EGBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on {Domestic Customer Center Number} within {Country Name} or {Overseas Customer Center Number} from outside {Country Name} for assistance.','MB','KEBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBOB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 248 383939 within Seychelles or 248 383939 from outside Seychelles for assistance.','MB','SCBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0774 700 703 or 0774 700 708 within Tanzania or (+255) 774 700 703 or (+255) 774 700 708 from outside Tanzania for assistance.','MB','TZBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.','MB','ZMBRB','COM_MW_06132');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 250 579 within Zimbabwe or 00263 4 250 579 from outside Zimbabwe for assistance.','MB','ZWBRB','COM_MW_06132');


-- INSERTING into C_MESSAGE_MST - COM_MW_06133

DELETE  FROM C_MESSAGE_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06133';
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','BWBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','EGBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','GHBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','KEBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBOB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','MUBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','SCBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','TZBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZMBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');
Insert into C_MESSAGE_MST (SYSTEM_ID,BUSINESS_ID,CATEGORY,MESSAGE_KEY,MODIFIED_BY,MODIFIED_DTM,AUTHORIZED_BY,AUTHORIZED_DTM,DELETE_FLG,SOURCE_SYSTEM_ID) values ('MB','ZWBRB','ERROR','COM_MW_06133',null,null,null,null,null,'MCFE');



-- INSERTING into C_MESSAGE_RES_MST - COM_MW_06133
DELETE  FROM C_MESSAGE_RES_MST  where system_id='MB' AND MESSAGE_KEY='COM_MW_06133';

Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0800 600 444 within Botswana or (+267) 315 9575 from outside Botswana for assistance.','MB','BWBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 16222 (Retail Customer), 16122 (Premier Customer) within Egypt or (+202) 16222 (Retail Customer) from outside Egypt for assistance.','MB','EGBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0302429150 within Ghana or (+233) 302429150 from outside Ghana for assistance.','MB','GHBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on {Domestic Customer Center Number} within {Country Name} or {Overseas Customer Center Number} from outside {Country Name} for assistance.','MB','KEBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBOB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 402 1000 within Mauritius or +230 402 1000 from outside Mauritius for assistance.','MB','MUBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 248 383939 within Seychelles or 248 383939 from outside Seychelles for assistance.','MB','SCBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 0774 700 703 or 0774 700 708 within Tanzania or (+255) 774 700 703 or (+255) 774 700 708 from outside Tanzania for assistance.','MB','TZBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 5950 within Zambia or +260 211 366100 from outside Zambia for assistance.','MB','ZMBRB','COM_MW_06133');
Insert into C_MESSAGE_RES_MST (LANGUAGE_ID,MESSAGE_VALUE,SYSTEM_ID,BUSINESS_ID,MESSAGE_KEY) values ('EN','We are unable to process your request temporarily. Please contact our Customer Service Centre on 250 579 within Zimbabwe or 00263 4 250 579 from outside Zimbabwe for assistance.','MB','ZWBRB','COM_MW_06133');






