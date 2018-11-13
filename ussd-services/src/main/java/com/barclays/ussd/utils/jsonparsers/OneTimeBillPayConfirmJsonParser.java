package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dao.product.impl.ListValueResDAOImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillerArea;
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
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPValidateAccount;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPValidateResponse;

public class OneTimeBillPayConfirmJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(OneTimeBillPayConfirmJsonParser.class);

    private static final String NAVCONFIRM_LABEL = "label.confirm";
    private static final String AMOUNT_LABEL = "label.amount";
    private static final String FROMACC_LABEL = "label.fromaccount";
    private static final String BILLER_LABEL = "label.biller";
    private static final String CREDIT_CARD_LABEL=  "label.from.card.account";
    // private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90003";
    private static final String AREA_NAME_LABEL = "label.area.name";
    private static final String CREDIT_CARD_FLAG_VALUE = "CreditCardOT";
    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";
    private String pilotValue = null;

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    OTBPValidateResponse otbpValidateResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OTBPValidateResponse.class);
	    if (otbpValidateResponse != null) {
		if (otbpValidateResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(otbpValidateResponse.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(otbpValidateResponse.getPayData(), responseBuilderParamsDTO);
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getParamName(),
			    otbpValidateResponse.getPayData().getTxnRefNo());
		} else if (otbpValidateResponse.getPayHdr() != null
			&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(otbpValidateResponse.getPayHdr().getResCde())) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
		} else if (otbpValidateResponse.getPayHdr() != null) {
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
     * @param payData
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
	String amountLabel = ussdResourceBundle.getLabel(AMOUNT_LABEL, locale);
	String confirmLabel = ussdResourceBundle.getLabel(NAVCONFIRM_LABEL, locale);
	String areaNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AREA_NAME_LABEL, locale);
	String creditCardLabel=responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CREDIT_CARD_LABEL, locale);

	if (payData != null) {
		Map<String, Object> txSessions =responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(billerLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(payData.getBillerInfo().getBillerName()).append(
		    USSDConstants.NEW_LINE);
	    if(amountLabel.equals("Montante:")){
	    	pageBody.append(amountLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(payData.getAmount().getAmt()).append("MT").append(USSDConstants.NEW_LINE);
	    }else{
	    	pageBody.append(amountLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(payData.getAmount().getAmt()).append(USSDConstants.NEW_LINE);
	    }
	    if(CREDIT_CARD_FLAG_VALUE.equals(txSessions.get("CreditCardOT")))
	    {
	    	CustomerMobileRegAcct cusstAccount=   (CustomerMobileRegAcct)payData.getCreditcardJsonModel();
	    	pageBody.append(creditCardLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(cusstAccount.getMkdCrdNo());

	    }
	    else
	    {
	    	OTBPValidateAccount acctDet = payData.getFromAccount();
	    	  pageBody.append(fromAccLabel).append(USSDConstants.SINGLE_WHITE_SPACE).append(acctDet.getMkdActNo());
	    }




	/*    String areaSearchChar = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
		    USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName());
	    if (StringUtils.isNotEmpty(areaSearchChar)) {*/
	    List<BillerArea> billerArea = (List<BillerArea>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId());
	    if(billerArea != null)
	    {
			int selectedAreaInput = Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
					USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName()));

			String selectArea = billerArea.get(selectedAreaInput - 1).getAreaName();
		    if (StringUtils.isNotEmpty(selectArea)) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(areaNameLabel);
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(selectArea);
		    }
	    }
	//    }

	    // Pilot Migration check CBP  22/08
		/*ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(BMBContextHolder.getContext());
		listValueResServiceRequest.setListValueKey("PayBillsBarclayAccnt");
		ListValueResByGroupServiceResponse listResp = listValueResDAOImpl.findListValueResByKey(listValueResServiceRequest);
		pilotValue = listResp.getListValueCahceDTO().get(0).getLabel();*/

	    // Transaction fee only for CASA - CPB change 25/05/2017
		// Removing PilotValue check(pilotValue !=null && pilotValue.equalsIgnoreCase("Y")) its not going with Regulatory 6.0.0 changes - 02/11/2017
		if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB")
				&& payData.getTxnChargeAmt()!=null){
		    if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("KEBRB") && payData.getCreditcardJsonModel() == null &&
		    		payData.getTxnChargeAmt().getAmt()!=null){

		    	Double accumulatedCharge = 0.0;
		    	Double roundedAccumulatedVal = 0.0;
	    		Double tranFee = Double.valueOf(payData.getTxnChargeAmt().getAmt());
				Double taxAmt = payData.getTaxAmount();
				if(tranFee!=null && taxAmt!=null){
					accumulatedCharge = tranFee + taxAmt;
					roundedAccumulatedVal =(double) Math.round(accumulatedCharge * 100) / 100;
				}

			    String transactionFeeLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_FEE_LABEL,
					    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
			    pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(transactionFeeLabel).append(USSDConstants.SINGLE_WHITE_SPACE).
	    		append(payData.getTxnChargeAmt().getCurr()).append(USSDConstants.SINGLE_WHITE_SPACE).append(roundedAccumulatedVal);

				ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "CpbMakeBillPaymentFields");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", payData.getTxnChargeAmt());
				ussdSessionMgmt.getTxSessions().put("CpbFeeGLAccount", payData.getFeeGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", payData.getTaxAmount());
				ussdSessionMgmt.getTxSessions().put("CpbTaxGLAccount", payData.getTaxGLAccount());
				ussdSessionMgmt.getTxSessions().put("CpbChargeNarration", payData.getChargeNarration());
				ussdSessionMgmt.getTxSessions().put("CpbExciseDutyNarration", payData.getExciseDutyNarration());
				ussdSessionMgmt.getTxSessions().put("CpbtypeCode", payData.getTypeCode());
				ussdSessionMgmt.getTxSessions().put("CpbValue", payData.getValue());
		    }else {
				String xelerateOfflineLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(XCELERATE_OFFLINE_LABEL,
					    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		    	pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(xelerateOfflineLabel);

				// Xelerate Offline scenario - fields required by BEM
				ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "xelerateOffline");
				ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", payData.getTxnChargeAmt());
				ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", payData.getTaxAmount());
		    }

		}
	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }
	}

	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo());

    }

}
