package com.barclays.ussd.bmg.groupwallet.response;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.TransactionActivityDetails;

public class GetTransactionDetailsJsonParser implements BmgBaseJsonParser {

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuDTO = new MenuItemDTO();
		TransactionActivityDetails activityDetails=null;
		ObjectMapper mapper=new ObjectMapper();

		try {
			activityDetails = mapper.readValue(responseBuilderParamsDTO.getJsonString(), TransactionActivityDetails.class);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTransactionList(activityDetails.getPayData().getCasaAccountTransactionList());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setNextScreenSequenceNumber(menuDTO);
		return menuDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTYTHREE.getSequenceNo());
	}

}