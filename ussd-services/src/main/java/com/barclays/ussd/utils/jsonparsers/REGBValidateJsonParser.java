package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillerArea;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.regbiller.ValidateRegBillerBean;
import com.barclays.ussd.utils.jsonparsers.bean.regbiller.ValidateRegBillerPayData;

public class REGBValidateJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {

    @Autowired
	ComponentResDAOImpl componentResDAOImpl;

    private static final String CATEGORY_LABEL = "label.biller.category";
    private static final String BILLER_NAME_LABEL = "label.billername";
    private static final String BILLER_NICK_NAME_LABEL = "label.biller.nick.name";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final String AREA_NAME_LABEL = "label.area.name";
    private static final Logger LOGGER = Logger.getLogger(REGBValidateJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    ValidateRegBillerBean regBillerValidateObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ValidateRegBillerBean.class);
	    if (regBillerValidateObj != null) {
		if (regBillerValidateObj.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(regBillerValidateObj.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(regBillerValidateObj.getPayData(), responseBuilderParamsDTO);
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(regBillerValidateObj.getPayData().getOrgRefNo());
		    // set the payee accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.REG_BILLER_VALIDATE.getTranId(), txnRefNo);
		} else if (regBillerValidateObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing REGBValidateJsonParser ");
		    throw new USSDNonBlockingException(regBillerValidateObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing   REGBValidateJsonParser");
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
     * @param validateRegBillerPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ValidateRegBillerPayData validateRegBillerPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();

	if (validateRegBillerPayData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	    String billerCategoryLabel = ussdResourceBundle.getLabel(CATEGORY_LABEL, locale);
	    String billerNameLabel = ussdResourceBundle.getLabel(BILLER_NAME_LABEL, locale);

	    String billerNickNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(BILLER_NICK_NAME_LABEL, locale);

	    String customerIdLabel = null;
	    /*if(ussdSessionMgmt.getBusinessId().equals("KEBRB"))
	    customerIdLabel = componentResDAOImpl.getBillerConfirmLabelByKey(validateRegBillerPayData.getBillerDTO().getBillerId(), ussdSessionMgmt.getBusinessId(), locale.getLanguage());
	    else*/

	    // WUC change - Botswana 21/06/2017
	    String wucCustomerRefLabel = null;
	    String wucContractRefLabel = null;
	    if(validateRegBillerPayData.getBillerDTO().getBillerId() !=null && validateRegBillerPayData.getBillerDTO().getBillerId().equals("WUC-2") && ussdSessionMgmt.getBusinessId().equals("BWBRB")){
	    	wucCustomerRefLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.wuc.customer.refNum", locale);
	    	wucContractRefLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.wuc.contract.refNum", locale);
	    }else{
	    	String ubpBusinessId=componentResDAOImpl.getUBPBusinessId();
	    	if(ubpBusinessId.contains(ussdSessionMgmt.getBusinessId())){
	    		customerIdLabel = componentResDAOImpl.getBillerLabelByKey(validateRegBillerPayData.getBillerDTO().getBillerId(), ussdSessionMgmt.getBusinessId(), locale.getLanguage());
				if(customerIdLabel.startsWith("Enter"))
					customerIdLabel=Character.toUpperCase(customerIdLabel.charAt("Enter".length()+1))+customerIdLabel.substring("Enter".length()+2)+":";
				else if(customerIdLabel.startsWith("enter"))
					customerIdLabel=Character.toUpperCase(customerIdLabel.charAt("enter".length()+1))+customerIdLabel.substring("enter".length()+2)+":";
				else if(customerIdLabel.startsWith("Introduza o")  )
					customerIdLabel=Character.toUpperCase(customerIdLabel.charAt("Introduza o".length()+1))+customerIdLabel.substring("Introduza o".length()+2)+":";
				else if(customerIdLabel.startsWith("introduza o"))
					customerIdLabel=Character.toUpperCase(customerIdLabel.charAt("introduza o".length()+1))+customerIdLabel.substring("introduza o".length()+2)+":";
				else
					customerIdLabel=customerIdLabel+":";
	    	}
	    	else
		    customerIdLabel = ussdResourceBundle.getLabel(USSDUtils.getConfCustRefId(responseBuilderParamsDTO.getUssdResourceBundle(), locale,
				    ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDConstants.SELECTED_BILLER_REGB)), locale);
	    }
	    String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFIRM_LABEL, locale);

	    String areaNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AREA_NAME_LABEL, locale);

	    pageBody.append(billerCategoryLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(validateRegBillerPayData.getBillerDTO().getBillerCategoryName());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(billerNameLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(validateRegBillerPayData.getBillerDTO().getBillerName());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(billerNickNameLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(validateRegBillerPayData.getPayeNickName());

	    pageBody.append(USSDConstants.NEW_LINE);
	    // WUC change - Botswana 21/06/2017
	    if(validateRegBillerPayData.getBillerDTO().getBillerId() !=null &&
	    		validateRegBillerPayData.getBillerDTO().getBillerId().equals("WUC-2") && ussdSessionMgmt.getBusinessId().equals("BWBRB")){
	    	pageBody.append(wucCustomerRefLabel);
	    	pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		    pageBody.append(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap()
			    .get(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName()));
	    	pageBody.append(USSDConstants.NEW_LINE);
	    	pageBody.append(wucContractRefLabel);
	    	pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		    pageBody.append(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap()
			    .get(USSDInputParamsEnum.REG_WUC_BILLER_TYPE.getParamName()));
	    }else{
	    	pageBody.append(customerIdLabel);
		    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		    pageBody.append(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap()
			    .get(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName()));
	    }

	    String isFromSaveBiller = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId());
	    List<BillerArea> billerArea = StringUtils.EMPTY.equals(isFromSaveBiller)?(List<BillerArea>) ussdSessionMgmt.getTxSessions()
			    .get(USSDInputParamsEnum.REG_BILLER_AREA_CODE.getTranId()):(List<BillerArea>) ussdSessionMgmt.getTxSessions()
			    .get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId());
	    if (billerArea != null) {
		int selectedAreaInput = Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
			USSDInputParamsEnum.REG_BILLER_AREA_CODE.getParamName()));
		String selectArea = billerArea.get(selectedAreaInput - 1).getAreaName();
		if (StringUtils.isNotEmpty(selectArea)) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(areaNameLabel);
		    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		    pageBody.append(selectArea);
		}
	    }
	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }
	}

	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo());

    }

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {

		String isFromSaveBiller = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId());
		int nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo();
		/**
		 * CR 73
		 */
		if(!isFromSaveBiller.equals(StringUtils.EMPTY) && isFromSaveBiller.equals(BillPaymentConstants.ONE_TIME_BILL_PAYMENT_SAVE_BILLER)){
			nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
		}
		return nextSequence;
	}
}
