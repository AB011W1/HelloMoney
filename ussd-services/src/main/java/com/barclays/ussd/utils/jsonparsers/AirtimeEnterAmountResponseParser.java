/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bem.Account.Account;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.bmg.service.impl.BillerServiceImpl;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.SrcAccount;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

/**
 * @author BTCI
 *
 */
public class AirtimeEnterAmountResponseParser implements BmgBaseJsonParser, SystemPreferenceValidator
 , ScreenSequenceCustomizer
{
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AirtimeEnterAmountResponseParser.class);

	private static final String TRANSACTION_AIRTIME_LABEL = "label.enter.the.airtime.amount";
	private static final String TRANSACTION_AIRTIME_SAVED_BENF_LABEL ="label.enter.the.airtime.amount.savedbenf";

    @Autowired
    private ListValueResServiceImpl listValueResService;

    @Autowired
    private BillerServiceImpl billerService;
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    @SuppressWarnings("unchecked")
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuffer pageBody=new StringBuffer();
	Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	//CR82
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	String mAtWt=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
    if(!mAtWt.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
    	ArrayList<Biller> ar= (ArrayList<Biller>)responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("ATT001");
     	String paramArray[]={(ar.get(Integer.parseInt(userInputMap.get("billerId"))-1)).getBillerName()};
    	String airtimeAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AIRTIME_LABEL, paramArray,
    			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
    					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
    	pageBody.append(airtimeAmountLabel);
    } else {

    	Payee payee=(Payee)ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId());


    	String paramArray[]={payee.getPayNckNam()};
    	String airtimeAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AIRTIME_SAVED_BENF_LABEL, paramArray,
    			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
    					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
    	pageBody.append(airtimeAmountLabel);

	    }

	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }

     public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		BillerServiceRequest request = new BillerServiceRequest();

		/*if(BillPaymentConstants.AT_MW_SAVED_BENEF.equalsIgnoreCase(userInputMap.get("AT_MW_SAVED_BENEF"))){
			List<Beneficiery> beneficiaryList = (List<Beneficiery>) txSessions.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getTranId());
			String selectedAirtimeTopupProvider = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getParamName());
			Beneficiery airtimeTopupProviderSelected = beneficiaryList.get(Integer.parseInt(selectedAirtimeTopupProvider) - 1);
			request.setBillerId(airtimeTopupProviderSelected.getBillerId());
		} else {*/
			List<Biller> mnoList = (List<Biller>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
			String selectedAirtimeTopupProvider = userInputMap.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName());
			Biller airtimeTopupProviderSelected = mnoList.get(Integer.parseInt(selectedAirtimeTopupProvider) - 1);
			request.setBillerId(airtimeTopupProviderSelected.getBillerId());
//		}
		request.setBusinessId(ussdSessionMgmt.getBusinessId());

		BillerServiceResponse billerServiceResponse = billerService.getActionCodeForBillerId(request);
		LOGGER.debug("billerServiceResponse response:"+ billerServiceResponse + "Biller Id:"+request.getBillerId());
		if (billerServiceResponse.getBillerCreditDTO() != null) {
			BillerCreditDTO billerCreditDTO = billerServiceResponse.getBillerCreditDTO();
			LOGGER.debug("billerCreditDTO response:"+ billerCreditDTO);
			txSessions.put("BillerCreditDTO",billerCreditDTO);
			String actionCode = billerServiceResponse.getBillerCreditDTO().getActionCode();
			if (actionCode != null && !actionCode.isEmpty()) {
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYTWO.getSequenceNo();
			}
		}

		return seqNo;
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile profile = ussdSessionMgmt.getUserProfile();
	// Code for replacing sys pref ref data code
	String minimumTopupAmt = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_TOPUP, SystemPreferenceConstants.MIN_TOPUP_AMT);
	String maximumTopupAmt = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_TOPUP, SystemPreferenceConstants.MAX_TOPUP_AMT);

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(minimumTopupAmt, maximumTopupAmt));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAmount(userInput);//CR-86

	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
		 //CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(minimumTopupAmt);
	    e.addErrorParam(maximumTopupAmt);
	    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
	    throw e;
	}
    }

    private String getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),
		listValueKey);
	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.findListValueResByGroupKey(listValReq);
	ListValueCacheDTO listValueCacheDTO = listValueByGroup.getListValueCacheDTOOneRow();
	if (listValueCacheDTO == null) {
	    LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(), USSDExceptions.USSD_SYS_PREF_MISSING
		    .getUssdErrorCode());
	}
	return listValueCacheDTO.getLabel();
    }

}
