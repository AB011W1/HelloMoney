/**
 * BillPaySubmitJsonParser.java
 */
package com.barclays.ussd.bmg.gepgbillers.response;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
import com.barclays.bmg.dao.product.impl.ListValueResDAOImpl;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPValidateAccount;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPValidateResponse;

/**
 * @author BTCI
 *
 */
public class GePGBillPayConfirmJsonParser implements BmgBaseJsonParser {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GePGBillPayConfirmJsonParser.class);

    private static final String NAVCONFIRM_LABEL = "label.confirm";
    private static final String FROMACC_LABEL = "label.fromaccount";
    private static final String BILLER_LABEL = "label.transaction.service";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90003";
    private static final String TRANSACTION_LABEL = "label.transaction.mwallet";

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;

    @Autowired
	ComponentResDAOImpl componentResDAOImpl;


    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
		    OTBPValidateResponse otbpValidateResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OTBPValidateResponse.class);
		    if (null != otbpValidateResponse) {
				if (null != otbpValidateResponse.getPayHdr()
					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(otbpValidateResponse.getPayHdr().getResCde())) {
				    menuDTO = renderMenuOnScreen(otbpValidateResponse.getPayData(), responseBuilderParamsDTO);
				    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.GePG_PAY_BILLS_SUBMIT.getParamName(),
					    otbpValidateResponse.getPayData().getTxnRefNo());
				} else if (null != otbpValidateResponse.getPayHdr()
					&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(otbpValidateResponse.getPayHdr().getResCde())) {
				    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
				} else if (null != otbpValidateResponse.getPayHdr()) {
				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				    throw new USSDNonBlockingException(otbpValidateResponse.getPayHdr().getResCde());
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
     * @param billPaySubmitData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
	private MenuItemDTO renderMenuOnScreen(OTBPValidatePayData payData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		String billerLabel = ussdResourceBundle.getLabel(BILLER_LABEL, locale);
		String fromAccLabel = ussdResourceBundle.getLabel(FROMACC_LABEL, locale);
		String confirmLabel = ussdResourceBundle.getLabel(NAVCONFIRM_LABEL, locale);

		if (null != payData) {
			String paramArray[]={payData.getAmount().getAmt(),payData.getAmount().getCurr()};
			String transactionLabel = ussdResourceBundle.getLabel(TRANSACTION_LABEL, paramArray, locale);
			pageBody.append(transactionLabel);

		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(billerLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(payData.getBillerInfo().getBillerName()).append(USSDConstants.NEW_LINE);
		    OTBPValidateAccount acctDet = payData.getFromAccount();
		    pageBody.append(fromAccLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(acctDet.getMkdActNo());

		    if (!responseBuilderParamsDTO.isErrorneousPage()) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(confirmLabel);
		    }
		}

		menuItemDTO.setPageBody(pageBody.toString());
		// insert the back and home options
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTY.getSequenceNo());
    }
}