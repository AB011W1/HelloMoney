/**
 *
 */
package com.barclays.ussd.bmg.gepgbillers.response;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetailsForControlNumber;

/**
 *
 * @author G01156975
 * The class handles the output to be displayed on the user screen.
 *
 */
public class GePGBillDetailsJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {

	private static final Logger LOGGER = Logger.getLogger(GePGBillDetailsJsonParser.class);

	/**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();

		BillDetailsForControlNumber billDetailsForControlNumber;
		try {
			billDetailsForControlNumber = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillDetailsForControlNumber.class);

			String controlNumber = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.GePG_CONTROL_NUMBER.getParamName());
			if(null != billDetailsForControlNumber && null != billDetailsForControlNumber.getPayHdr()
					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(billDetailsForControlNumber.getPayHdr().getResCde())){
				validateResponse(billDetailsForControlNumber.getPayData(), controlNumber);
				if(null != ussdSessionMgmt.getTxSessions()){
					ussdSessionMgmt.getTxSessions().put(USSDConstants.GePG_BILL_DETAIL, billDetailsForControlNumber.getPayData());
				}else {
					Map<String, Object> txSessions = new HashMap<String, Object>();
					txSessions.put(USSDConstants.GePG_BILL_DETAIL, billDetailsForControlNumber.getPayData());
					ussdSessionMgmt.setTxSessions(txSessions);
				}
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_BILL_DETAILS.getUssdErrorCode());
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
		return menuItemDTO;
    }

    /**
     * @param MenuItemDTO
     */
    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYSIX.getSequenceNo());
    }

    /**
     * @param userInput
     * @param ussdSessionMgmt
     * @return screenNumber
     *
     */
	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int screenNumber = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();

		if(null != ussdSessionMgmt.getTxSessions()){
			BillDetails billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get(USSDConstants.GePG_BILL_DETAIL);
			if(USSDConstants.GePG_BILL_PAYMENT_TYPE.equalsIgnoreCase(billDetails.getPaymentType())){
				screenNumber = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYSIX.getSequenceNo();
			} else {
				screenNumber = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYSEVEN.getSequenceNo();
			}
		}

		return screenNumber;
	}

	/**
	 * @param billDetails
	 * @throws DateParseException
	 * @throws USSDNonBlockingException
	 * @throws ParseException
	 *
	 */
	public void validateResponse(BillDetails billDetails, String controlNumber) throws DateParseException, USSDNonBlockingException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null == billDetails.getBillDueDate() || "".equalsIgnoreCase(billDetails.getBillDueDate())){
			throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_BILL_DETAILS.getUssdErrorCode());
		}

		Date responseDate = sdf.parse(billDetails.getBillDueDate());
		if(new Date().compareTo(responseDate) > 0){
			throw new USSDNonBlockingException(USSDExceptions.USSD_DATE_EXPIRED.getUssdErrorCode());
		} else if(!("TZS".equalsIgnoreCase(billDetails.getFeeAmount().getCurrency()))){
			throw new USSDNonBlockingException(USSDExceptions.USSD_INVALID_CURRENCY.getUssdErrorCode());
		}
	}
}