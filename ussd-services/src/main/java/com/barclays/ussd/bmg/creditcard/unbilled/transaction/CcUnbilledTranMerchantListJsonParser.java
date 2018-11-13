/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.bmg.creditcard.unbilled.transaction;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.creditcard.at.a.glance.RetrieveCreditCardListJsonParser;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

/**
 * @author BTCI
 * 
 */
public class CcUnbilledTranMerchantListJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RetrieveCreditCardListJsonParser.class);

    private static final String LABEL_BARCLAY_CARD = "label.barclay.card";

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    CreditCardUnbilledTranData creditCardUnbilledTranObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    CreditCardUnbilledTranData.class);
	    if (creditCardUnbilledTranObj != null) {
		if (creditCardUnbilledTranObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(creditCardUnbilledTranObj.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, creditCardUnbilledTranObj);
		} else if (creditCardUnbilledTranObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(creditCardUnbilledTranObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    } else {
		LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
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
     * @param responseBuilderParamsDTO
     * @param creditCardUnbilledTranObj
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, CreditCardUnbilledTranData creditCardUnbilledTranObj)
	    throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;
	CreditCardDetails crdDetls = creditCardUnbilledTranObj.getPayData().getCrdDetls();
	if (crdDetls != null) {
	    menuItemDTO = new MenuItemDTO();
	    int index = 1;
	    StringBuilder pageBody = new StringBuilder();

	    List<CreditCardActivity> actActvLst = crdDetls.getActActvLst();
	    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    txSessions.put(USSDInputParamsEnum.CR_CARD_UNBILLED_TRAN_MERCHANT_LIST.getTranId(), actActvLst);
	    txSessions.put(USSDInputParamsEnum.CR_CARD_UNBILLED_DETAILS.getTranId(), crdDetls);
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);

	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    String language = ussdSessionMgmt.getUserProfile().getLanguage();
	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();

	    String barclaysCardLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_BARCLAY_CARD,
		    new Locale(language, countryCode));
	    pageBody.append(barclaysCardLabel);

	    pageBody.append(crdDetls.getMkdCrdNo());

	    for (CreditCardActivity cardDetail : actActvLst) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(cardDetail.getMerchantName());
		pageBody.append(USSDConstants.COMMA_SEPERATOR);
		pageBody.append(cardDetail.getDrCrFlg());
		pageBody.append(USSDConstants.COMMA_SEPERATOR);
		pageBody.append(cardDetail.getAmt().getCurr());
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(cardDetail.getAmt().getAmt());
		index++;
	    }
	    menuItemDTO.setPageBody(pageBody.toString());
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    // menuItemDTO.setPageFooter(warningMsg);
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    setNextScreenSequenceNumber(menuItemDTO);
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_STATEMENT_FOUND.getBmgCode());
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }
}
