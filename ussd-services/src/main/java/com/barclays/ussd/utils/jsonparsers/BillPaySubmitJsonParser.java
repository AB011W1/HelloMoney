/**
 * BillPaySubmitJsonParser.java
 */
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

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
import com.barclays.bmg.dao.product.impl.ListValueResDAOImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillPaySubmit;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillPaySubmitData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 *
 */
public class BillPaySubmitJsonParser implements BmgBaseJsonParser {

    private static final String LABEL_CONFIRM_REFERENCE_NUMBER = "label.confirm.reference.number";
    private static final String BILLER_NICK_NAME_LABEL = "label.biller.nick.name";
    private static final String BILLER_NAME_LABEL = "label.biller.name";
    private static final String AMOUNT_LABEL = "label.amount";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final String BMB90003 = "BMB90003";
    private static final String AREA_NAME_LABEL = "label.area.name";
    // WUC Botswana Biller change - 28/06/2017
    private static final String LABEL_CUSTOMER_REFERENCE_NUMBER = "label.wuc.customer.refNum";
    private static final String LABEL_CONTRACT_REFERENCE_NUMBER = "label.wuc.contract.refNum";

    private static final String TRANSACTION_FEE_LABEL = "label.transactionfee.msg";
    private String pilotValue = null;
    private static final String XCELERATE_OFFLINE_LABEL = "label.xcelerate.offline.acceptance";

    @Autowired
    ListValueResDAOImpl listValueResDAOImpl;

    @Autowired
	ComponentResDAOImpl componentResDAOImpl;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(BillPaySubmitJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    BillPaySubmit billPaySubmit = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillPaySubmit.class);
	    if (billPaySubmit != null) {
		PayHdr payHdr = billPaySubmit.getPayHdr();
		if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		    menuDTO = renderMenuOnScreen(billPaySubmit, responseBuilderParamsDTO);
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(billPaySubmit.getPayData().getTxnRefNo());
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.PAY_BILLS_SUBMIT.getTranId(), txnRefNo);
		} else if (payHdr != null && TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(payHdr.getResCde())) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
		}else if (payHdr != null && BMB90003.equalsIgnoreCase(payHdr.getResCde())) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_BILL_PAY_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
		}
		else if (payHdr != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(payHdr.getResCde());
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
    private MenuItemDTO renderMenuOnScreen(BillPaySubmit billPaySubmit, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	BillPaySubmitData payData = billPaySubmit.getPayData();

	if (payData != null) {
	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	    Locale locale = new Locale(userProfile.getLanguage(), userProfile.getCountryCode());
	    String billerLabel = ussdResourceBundle.getLabel(BILLER_NAME_LABEL, locale);
	    String billerNickNameLabel = ussdResourceBundle.getLabel(BILLER_NICK_NAME_LABEL, locale);
	    String amountLabel = ussdResourceBundle.getLabel(AMOUNT_LABEL, locale);
	    String confirmLabel = ussdResourceBundle.getLabel(CONFIRM_LABEL, locale);

	    //CR#61 Changes starts
	    String billerID= payData.getPaymentDetails().getBilrId();
	    String custRefID = null;
	    String wucCustomerRefID = null;
	    String wucContractRefID = null;
	    String ubpBusinessId=componentResDAOImpl.getUBPBusinessId();
    	if(ubpBusinessId.contains(ussdSessionMgmt.getBusinessId()) && !billerID.equals("WUC-2")){
			custRefID = componentResDAOImpl.getBillerLabelByKey(billerID, ussdSessionMgmt.getBusinessId(), locale.getLanguage());
			if(custRefID.startsWith("Enter")  )
				custRefID=Character.toUpperCase(custRefID.charAt("Enter".length()+1))+custRefID.substring("Enter".length()+2)+":";
			else if(custRefID.startsWith("enter"))
				custRefID=Character.toUpperCase(custRefID.charAt("enter".length()+1))+custRefID.substring("enter".length()+2)+":";
			else if(custRefID.startsWith("Introduza o")  )
				custRefID=Character.toUpperCase(custRefID.charAt("Introduza o".length()+1))+custRefID.substring("Introduza o".length()+2)+":";
			else if(custRefID.startsWith("introduza o"))
				custRefID=Character.toUpperCase(custRefID.charAt("introduza o".length()+1))+custRefID.substring("introduza o".length()+2)+":";
			else
				custRefID=custRefID+":";
    	}
		else{
			custRefID = ussdResourceBundle.getLabel(LABEL_CONFIRM_REFERENCE_NUMBER, locale);

		String billerIDtoCheck = new StringBuilder(USSDConstants.CONF_CR_ID).append(billerID).toString();
		if (!StringUtils.equalsIgnoreCase(ussdResourceBundle.getLabel(billerIDtoCheck, locale), USSDConstants.UNKNOWN_LABEL) && !billerID.equals("WUC-2") ) {
		    custRefID = ussdResourceBundle.getLabel(billerIDtoCheck, locale);
		}
		}
		String refNoLabel = custRefID;
        //CR#61 Changes ends

	    String areaNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AREA_NAME_LABEL, locale);

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(billerNickNameLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(payData.getPaymentDetails().getPayNckNam());

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(billerLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(payData.getPaymentDetails().getBilrNam());

	    // WUC Botswana Biller change - 28/06/2017
	    if(ussdSessionMgmt.getBusinessId().equals("BWBRB") && billerID.equals("WUC-2")){
	    	 wucCustomerRefID = ussdResourceBundle.getLabel(LABEL_CUSTOMER_REFERENCE_NUMBER, locale);
    		 wucContractRefID = ussdResourceBundle.getLabel(LABEL_CONTRACT_REFERENCE_NUMBER, locale);

	    	pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(wucCustomerRefID);
		    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		    pageBody.append(payData.getPaymentDetails().getRefNo().getRefNo());

		    pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(wucContractRefID);
		    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		    pageBody.append(payData.getPaymentDetails().getWucContractRefNo().getRefNo());
	    }else{
	    	pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(refNoLabel);
		    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		    pageBody.append(payData.getPaymentDetails().getRefNo().getRefNo());
	    }

	    if(!billerID.equals("WUC-2")){
		    List<Beneficiery> lstBenef = (List<Beneficiery>) ussdSessionMgmt
							.getTxSessions().get(
									USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST
											.getTranId());
					if (!lstBenef.isEmpty()) {
						Beneficiery bene = lstBenef
								.get(Integer
										.parseInt(ussdSessionMgmt
												.getUserTransactionDetails()
												.getUserInputMap()
												.get(
														USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST
																.getParamName())) - 1);
						if (StringUtils.isNotEmpty(bene.getAreaCode())) {
							pageBody.append(USSDConstants.NEW_LINE);
							pageBody.append(areaNameLabel);
							pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
							pageBody.append(bene.getAreaCode());
						}
				}
		}

	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(amountLabel);
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	     if(amountLabel.equals("Montante:")){
	    	pageBody.append(payData.getPaymentDetails().getTransactionAmtDetails().getAmt());
	    	pageBody.append("MT");
	    }else{
	    	pageBody.append(payData.getPaymentDetails().getTransactionAmtDetails().getAmt());
	    }

	  // Pilot Migration check CBP  22/08
		/*ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(BMBContextHolder.getContext());
		listValueResServiceRequest.setListValueKey("PayBillsBarclayAccnt");
		ListValueResByGroupServiceResponse listResp = listValueResDAOImpl.findListValueResByKey(listValueResServiceRequest);
		pilotValue = listResp.getListValueCahceDTO().get(0).getLabel();*/

	    // Transaction fee only for CASA - CPB change 25/05/2017
		// Removing PilotValue check(pilotValue !=null && pilotValue.equalsIgnoreCase("Y")) its not going with Regulatory 6.0.0 changes - 02/11/2017
	   // CBP change : Kenya & Uganda country
	     //CBP Change
	    BMGGlobalContext logContext = BMGContextHolder.getLogContext();
	   if(payData.getFrActNo().getMkdActNo() != null && ((null!=logContext  && logContext.getContextMap().get("isCBPFLAG").equals("Y"))|| ((null!=logContext && logContext.getBusinessId().equals("KEBRB"))))){
				if(((null!=logContext && logContext.getContextMap().get("isCBPFLAG").equals("Y")) || (null!=logContext && logContext.getBusinessId().equals("KEBRB"))) && payData.getTxnChargeAmt().getAmt()!=null){

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

					// CPB change fields set for MakeBillPayemntRequest 09/05/2017
					ussdSessionMgmt.getTxSessions().put("CpbMakeBillPaymentFields", "CpbMakeBillPaymentFields");
					ussdSessionMgmt.getTxSessions().put("CpbChargeAmount", payData.getTxnChargeAmt());
					ussdSessionMgmt.getTxSessions().put("CpbFeeGLAccount", payData.getFeeGLAccount());
					ussdSessionMgmt.getTxSessions().put("CpbTaxAmount", payData.getTaxAmount());
					ussdSessionMgmt.getTxSessions().put("CpbTaxGLAccount", payData.getTaxGLAccount());
					ussdSessionMgmt.getTxSessions().put("CpbChargeNarration", payData.getChargeNarration());
					ussdSessionMgmt.getTxSessions().put("CpbExciseDutyNarration", payData.getExciseDutyNarration());
					ussdSessionMgmt.getTxSessions().put("CpbtypeCode", payData.getTypeCode());
					ussdSessionMgmt.getTxSessions().put("CpbValue", payData.getValue());
	    	}else{
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
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(confirmLabel);
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }
}
