package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletTxValidate;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

public class MobileWalletTopupAmountJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator
 ,ScreenSequenceCustomizer
{
    @Autowired
    private ListValueResServiceImpl listValueResService;

    @Autowired
    private BillerServiceImpl billerService;

    private static final String TRANSACTION_MWALLETE_LABEL = "label.enter.the.amount";
    private static final String TRANSACTION_MWALLETE_SAVED_BENF_LABEL ="label.enter.the.mwallet.amount.savedbenf";
    private static final String TRANSACTION_MWALLETE_MOBILEVALIDATION_LABEL = "label.mwallet.mobilevalidation";

    private static final Logger LOGGER = Logger.getLogger(MobileWalletTopupAmountJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    @SuppressWarnings("unchecked")
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	MenuItemDTO menuItemDTO = new MenuItemDTO();
    	Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
    	
    	int userInput = 0;
    	String provider = null;
    	if(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails() != null && responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap() != null && responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().containsKey("billerId"))
    	userInput  = Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("billerId"));
    	if(responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() != null && responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().containsKey("MWTU001") && userInput != 0 && !((ArrayList) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("MWTU001")).isEmpty() )
    	provider = ((MobileWalletProvider) ((ArrayList) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("MWTU001")).get(userInput - 1)).getBillerId();

    	//CR82
    	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
    	String mAtWtSavedBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
       	if(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("UGBRB")  && provider.contains(responseBuilderParamsDTO.getUssdSessionMgmt().getMobileValidationBiller()) && responseBuilderParamsDTO.getUssdSessionMgmt().isMobileValidation()){
			if(responseBuilderParamsDTO.getBmgOpCode().equalsIgnoreCase("OP0675")
					&& responseBuilderParamsDTO.getTranDataId().equalsIgnoreCase("MWTU045")) {
				
				ObjectMapper mapper = new ObjectMapper();
				MobileWalletTxValidate accList = null;
				
				menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
				if (responseBuilderParamsDTO.getJsonString() != null
						&& responseBuilderParamsDTO.getJsonString().contains("resCde")) {
					try {
						accList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), MobileWalletTxValidate.class);
						
						if (accList.getPayHdr().getResCde().equalsIgnoreCase("00000")) {						
							
							
							USSDSessionManagement ussdSession=responseBuilderParamsDTO.getUssdSessionMgmt();
							Map<String, Object> txSessions = ussdSession.getTxSessions();
							txSessions.put(responseBuilderParamsDTO.getTranDataId(),accList.getPayData().getPayeeName());
							ussdSession.setTxSessions(txSessions);
							responseBuilderParamsDTO.setUssdSessionMgmt(ussdSession);					
							
							
						} else if (accList.getPayHdr().getResCde().equalsIgnoreCase("500")) {
							
							throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ACCNT.getBmgCode());
						}
						else {
							throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
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
				}				
			}		
			else if (responseBuilderParamsDTO.getTranDataId().equalsIgnoreCase("MWTU003")) {
				String mwalleteAmountLabel = null;
				setNextScreenSequenceNumber(menuItemDTO);
				
							ArrayList<MobileWalletProvider> ar = (ArrayList<MobileWalletProvider>) responseBuilderParamsDTO
									.getUssdSessionMgmt().getTxSessions().get("MWTU001");
							List<String> params = new ArrayList<String>(1);
							
							String[] billerName = {
									(ar.get(Integer.parseInt(userInputMap.get("billerId")) - 1)).getBillerName() };
							params.add(billerName[0]);
							String name = (String)responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("MWTU045");
							params.add(name);
							String paramArray[] = params.toArray(new String[params.size()]);
							mwalleteAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(
									TRANSACTION_MWALLETE_MOBILEVALIDATION_LABEL, paramArray,
									new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
											responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile()
													.getCountryCode()));
										menuItemDTO.setPageBody(mwalleteAmountLabel);
			} 			
		}
       	else if(mAtWtSavedBenf.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
         	Payee payee=(Payee)ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.MOBILE_WALLET_BENF_DTlS.getTranId());


         	String paramArray[]={payee.getPayNckNam()};
         	String mwalleteAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_MWALLETE_SAVED_BENF_LABEL, paramArray,
         			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
         					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
         	menuItemDTO.setPageBody(mwalleteAmountLabel);
         	setNextScreenSequenceNumber(menuItemDTO);
         }	
        else {
        	ArrayList<MobileWalletProvider> ar= (ArrayList<MobileWalletProvider>)responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("MWTU001");

         	String paramArray[]={(ar.get(Integer.parseInt(userInputMap.get("billerId"))-1)).getBillerName()};

        	String mwalleteAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_MWALLETE_LABEL, paramArray,
        			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
        					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
         	menuItemDTO.setPageBody(mwalleteAmountLabel);
         	setNextScreenSequenceNumber(menuItemDTO);
        }

	if(ussdSessionMgmt.getBusinessId().equals("GHBRB") && ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID)){
		responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setBackOptionReq("false");
		responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("false");
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);	
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile profile = ussdSessionMgmt.getUserProfile();
	/*if(ussdSessionMgmt.getBusinessId().equals("GHBRB") && ussdSessionMgmt.isFreeDialUssdFlow()==true){
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setBackOptionReq("false");
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("false");
	}*/

	int userInputNumber = 0;
	String provider = null;
	if(ussdSessionMgmt.getUserTransactionDetails() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().containsKey("billerId"))
		userInputNumber  = Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("billerId"));
	if(ussdSessionMgmt.getTxSessions() != null && ussdSessionMgmt.getTxSessions().containsKey("MWTU001") && userInputNumber != 0 && !((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).isEmpty() )
	provider = ((MobileWalletProvider) ((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).get(userInputNumber - 1)).getBillerId();

	if(ussdSessionMgmt.getBusinessId().equals("UGBRB")){
		if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equals("MWTU004") && provider.contains(ussdSessionMgmt.getMobileValidationBiller()) && ussdSessionMgmt.isMobileValidation()) {
	if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID) && userInput.length()>10){
		userInput="";
	}
	String mininimumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_MWALLET,
		SystemPreferenceConstants.MWALLET_MIN_AMT_SNDR);

	String maximumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_MWALLET,
		SystemPreferenceConstants.MWALLET_MAX_AMT_SNDR);

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(mininimumAmtSndr, maximumAmtSndr));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAmount(userInput);//CR-86
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
	    //CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(mininimumAmtSndr);
	    e.addErrorParam(maximumAmtSndr);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
	    throw e;
	}
	}
	}else {
		if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID) && userInput.length()>10){
			userInput="";
		}
		String mininimumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_MWALLET,
			SystemPreferenceConstants.MWALLET_MIN_AMT_SNDR);

		String maximumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_MWALLET,
			SystemPreferenceConstants.MWALLET_MAX_AMT_SNDR);

		USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(mininimumAmtSndr, maximumAmtSndr));
		USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
		try {
			backFlowValidator.validateAmount(userInput);//CR-86
		    validator.validate(userInput);
		} catch (USSDNonBlockingException e) {
		    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
		    //CR-86 Back flow changes
			e.setBackFlow(true);
			e.addErrorParam(userInput);
		    e.addErrorParam(mininimumAmtSndr);
		    e.addErrorParam(maximumAmtSndr);
		    e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
		    throw e;
		}

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

    @SuppressWarnings("unchecked")
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
    	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo();
    	/*int userInputNumber = 0;
    	String provider = null;
    	if(ussdSessionMgmt.getUserTransactionDetails() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap() != null && ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().containsKey("billerId"))
    		userInputNumber  = Integer.parseInt(ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("billerId"));
    	if(ussdSessionMgmt.getTxSessions() != null && ussdSessionMgmt.getTxSessions().containsKey("MWTU001") && userInputNumber != 0 && !((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).isEmpty() )
    	provider = ((MobileWalletProvider) ((ArrayList) ussdSessionMgmt.getTxSessions().get("MWTU001")).get(userInputNumber - 1)).getBillerId();

    	if(ussdSessionMgmt.getBusinessId().equals("UGBRB")) {
    		if(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getBmgOpCode().equals("OP0675") && provider.contains(ussdSessionMgmt.getMobileValidationBiller()) && ussdSessionMgmt.isMobileValidation())
    			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
    		else
    			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo();
    		}*/

		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		BillerServiceRequest request = new BillerServiceRequest();

		if(BillPaymentConstants.AT_MW_SAVED_BENEF.equalsIgnoreCase(userInputMap.get("AT_MW_SAVED_BENEF"))) {
			List<Beneficiery> beneficieryList = (List<Beneficiery>) txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId());
			String selectedBeneficiery = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getParamName());
			Beneficiery beneficierySelected = beneficieryList.get(Integer.parseInt(selectedBeneficiery) - 1);
			request.setBillerId(beneficierySelected.getBillerId());
		}
		else {
			List<MobileWalletProvider> mobileWalletProvider = (List<MobileWalletProvider>) txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getTranId());
			String selectedMobileWalletProvider = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName());
			MobileWalletProvider mobileWalletProviderSelected = mobileWalletProvider.get(Integer.parseInt(selectedMobileWalletProvider) - 1);
			request.setBillerId(mobileWalletProviderSelected.getBillerId());
		}
		request.setBusinessId(ussdSessionMgmt.getBusinessId());

		BillerServiceResponse billerServiceResponse = billerService.getActionCodeForBillerId(request);

		if (billerServiceResponse.getBillerCreditDTO() != null) {
			BillerCreditDTO billerCreditDTO = billerServiceResponse.getBillerCreditDTO();
			txSessions.put("BillerCreditDTO",billerCreditDTO);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYTWO.getSequenceNo();
			}
		return seqNo;
    }

	public BillerServiceImpl getBillerService() {
		return billerService;
	}

	public void setBillerService(BillerServiceImpl billerService) {
		this.billerService = billerService;
	}

}
