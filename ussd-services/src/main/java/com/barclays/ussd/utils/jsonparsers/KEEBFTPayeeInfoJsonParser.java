package com.barclays.ussd.utils.jsonparsers;

import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeInfo;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeInfoData;

public class KEEBFTPayeeInfoJsonParser implements BmgBaseJsonParser {

	   private static final Logger LOGGER = Logger.getLogger(EBFTPayeeInfoJsonParser.class);

	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
		    PayeeInfo payeeInfo = mapper.readValue(responseBuilderParamsDTO.getJsonString(), PayeeInfo.class);
		    if (payeeInfo != null) {
			if (payeeInfo.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payeeInfo.getPayHdr().getResCde())) {
			    setOutputInSession(responseBuilderParamsDTO, payeeInfo);

			} else if (payeeInfo.getPayHdr() != null) {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(payeeInfo.getPayHdr().getResCde());
			} else {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
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

	    /**
	     * @param responseBuilderParamsDTO
	     * @param payeeInfo
	     */
	    private void setOutputInSession(ResponseBuilderParamsDTO responseBuilderParamsDTO, PayeeInfo payeeInfo) {
		PayeeInfoData payeeInfoData = payeeInfo.getPayData();
		if (payeeInfoData != null) {
		    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		    txSessions.put(USSDInputParamsEnum.KE_EXT_BANK_FT_PAYEE_INFO.getTranId(), payeeInfoData.getTxnLmt().getCurr());
		}
	    }

	    @Override
	    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());

	    }
}
