/**
 * DelBillrValParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.delbillers.DelBillersValidate;
import com.barclays.ussd.utils.jsonparsers.bean.delbillers.DelBlrsValData;

/**
 * @author BTCI
 *
 */
public class AirtimeTopupDelBillrValParser implements BmgBaseJsonParser {

    private static final String BILLERID_LABEL = "label.airtime.biller.id";
   // private static final String BILLERTYPE_LABEL = "label.biller.type";
    private static final String BILLER_NICK_NAME_LABEL = "label.airtime.biller.nick.name";
    private static final String AIRTIME_TOPUP_EDIT_BNF_CONFIRM_MSG = "airtimetopup.edit.bnf.confirm.msg";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final Logger LOGGER = Logger.getLogger(AirtimeTopupDelBillrValParser.class);

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.utils.BmgBaseJsonParser#parseJsonIntoJava(com.barclays .ussd.bmg.dto.ResponseBuilderParamsDTO)
     */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	DelBillersValidate delBillrVal = null;

	try {
	    delBillrVal = mapper.readValue(jsonString, DelBillersValidate.class);

	    if (delBillrVal != null) {
		if (delBillrVal.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(delBillrVal.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(delBillrVal.getPayData(), responseBuilderParamsDTO);
		} else if (delBillrVal.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(delBillrVal.getPayHdr().getResCde());
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
	return menuDTO;

    }

    /**
     * @param delBlrsValData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(DelBlrsValData delBlrsValData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	Payee pay = delBlrsValData.getPay();
	if (pay != null) {
			USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
					.getUssdSessionMgmt();
			Map<String, String> userInputMap = ussdSessionMgmt
					.getUserTransactionDetails().getUserInputMap();
			String countryCode = "";
			String lang = "";
			if (ussdSessionMgmt.getUserProfile() != null) {
				countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
				lang = ussdSessionMgmt.getUserProfile().getLanguage();
			}
			String paymentTypeInput = userInputMap
					.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYMENT_TYPE
							.getParamName());
			if (paymentTypeInput.equals("4")) {
				List<String> params = new ArrayList<String>(1);
				params.add(pay.getPayNckNam());
				params.add((String) userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_NEW_BENE_MOB_NUM.getParamName()));
				String billerUsrInput = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_NEW_BENE_SRC_MON.getParamName());

				String billerName = StringUtils.EMPTY;
				List<Biller> mnoList = (List<Biller>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_NEW_BENE_SRC_MON.getTranId());
				if (StringUtils.isNotEmpty(billerUsrInput) && StringUtils.isNumeric(billerUsrInput) && mnoList!=null && !mnoList.isEmpty()) {
					billerName=mnoList.get(Integer.parseInt(billerUsrInput)-1).getBillerName();
				}
				params.add(billerName);
				String[] paramArray = params.toArray(new String[params.size()]);
				String editConfirmScreenLabel = StringUtils.EMPTY;

				editConfirmScreenLabel = responseBuilderParamsDTO
						.getUssdResourceBundle().getLabel(
								AIRTIME_TOPUP_EDIT_BNF_CONFIRM_MSG,
								paramArray,
								new Locale(lang, ussdSessionMgmt
										.getUserProfile().getCountryCode()));
				pageBody.append(editConfirmScreenLabel);
				menuItemDTO.setPageHeader(USSDConstants.LBL_BLANK_PAGE_HEADER);
			} else {

				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(responseBuilderParamsDTO
						.getUssdResourceBundle().getLabel(
								BILLER_NICK_NAME_LABEL,
								new Locale(lang, countryCode)));
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(pay.getPayNckNam());

				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(responseBuilderParamsDTO
						.getUssdResourceBundle().getLabel(BILLERID_LABEL,
								new Locale(lang, countryCode)));
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(pay.getRefNo().getPhNo());

				/*
				 * pageBody.append(USSDConstants.NEW_LINE);
				 * pageBody.append(responseBuilderParamsDTO
				 * .getUssdResourceBundle().getLabel(BILLERTYPE_LABEL, new
				 * Locale(lang, countryCode)));
				 * pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				 * pageBody.append(pay.getBilrTyp());
				 */
				menuItemDTO.setPageHeader(responseBuilderParamsDTO
						.getHeaderId());
			}
			if (!responseBuilderParamsDTO.isErrorneousPage()) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(responseBuilderParamsDTO
						.getUssdResourceBundle().getLabel(CONFIRM_LABEL,
								new Locale(lang, countryCode)));
			}
			menuItemDTO.setPageBody(pageBody.toString());
		}
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHTTEEN.getSequenceNo());

    }
}
