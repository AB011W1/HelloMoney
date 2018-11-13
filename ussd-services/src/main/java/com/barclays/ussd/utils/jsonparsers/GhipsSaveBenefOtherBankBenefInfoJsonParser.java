package com.barclays.ussd.utils.jsonparsers;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayInfo;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeInfo;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeInfoData;

public class GhipsSaveBenefOtherBankBenefInfoJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(GhipsSaveBenefOtherBankBenefInfoJsonParser.class);

	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)throws USSDNonBlockingException {
		MenuItemDTO menuDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			PayeeInfo payeeInfo = mapper.readValue(responseBuilderParamsDTO.getJsonString(), PayeeInfo.class);
			if (payeeInfo != null) {
				PayeeInfoData payeeInfoData = payeeInfo.getPayData();
				if (payeeInfoData != null) {
					PayInfo payInfo=payeeInfoData.getPayInfo();
				   responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().
				    put(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_BENEF_INFO.getTranId(), payInfo.getBnkCode()+"-"+payInfo.getActNo());
				   responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().
				    put("benefFullName", payInfo.getBeneNam());
				}
			}
		} catch (JsonParseException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (JsonMappingException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}

		setNextScreenSequenceNumber(menuDTO);
		return menuDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
	}
}
