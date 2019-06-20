package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SearchIndividualCustNQResponse;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.login.SearchIndividualCustNQPayData;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

public class MobileWalletConfirmBeneficiaryResponseParser implements BmgBaseJsonParser, SystemPreferenceValidator
,ScreenSequenceCustomizer {


	@Autowired
	private ListValueResServiceImpl listValueResService;

	@Autowired
	private BillerServiceImpl billerService;

	private static final String TRANSACTION_MWALLET_LABEL = "label.confirm.beneficiary";
	private static final String TRANSACTION_MWALLET_SAVED_BENF_LABEL ="label.enter.mwallet.amount.savedbenf";
	private static final Logger LOGGER = Logger.getLogger(MobileWalletConfirmBeneficiaryResponseParser.class);


	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException{
		return renderMenuOnScreen(responseBuilderParamsDTO);
	}

	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDNonBlockingException, USSDNonBlockingException, USSDBlockingException{

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		StringBuilder pageBody = new StringBuilder();
		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
			txSessions = new HashMap<String, Object>(8);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		menuItemDTO.setPageHeader("LBL9999");
		String customerName=getCustomerName(responseBuilderParamsDTO,menuItemDTO);
		String name = "";
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		if(customerName!=null && !customerName.equals("")){

			if(customerName.length()>50)
			{
				name = customerName.substring(0, 49);
			}
			else
			{
				name = customerName;
			}
			//amount
			String mAtWtSavedBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
			if(mAtWtSavedBenf.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
				Payee payee=(Payee)ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.MOBILE_WALLET_BENF_DTlS.getTranId());
				List<String> params = new ArrayList<String>(1);
				params.add(payee.getPayNckNam());
				params.add(name);

				String paramArray[]=params.toArray(new String[params.size()]);
				String mwalleteAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_MWALLET_SAVED_BENF_LABEL, paramArray,
						new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
								responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
				menuItemDTO.setPageBody(mwalleteAmountLabel);
				pageBody.append(mwalleteAmountLabel);

			} else {
				ArrayList<MobileWalletProvider> ar= (ArrayList<MobileWalletProvider>)responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("MWTU001");
				List<String> params = new ArrayList<String>(1);
				//			String paramArray1[]={(ar.get(Integer.parseInt(userInputMap.get("billerId"))-1)).getBillerName()};
				String[] billerName={(ar.get(Integer.parseInt(userInputMap.get("billerId"))-1)).getBillerName()};
				params.add(billerName[0]);
				params.add(name);
				String paramArray[]=params.toArray(new String[params.size()]);
				String mwalleteAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_MWALLET_LABEL, paramArray,
						new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
								responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
				menuItemDTO.setPageBody(mwalleteAmountLabel);
				pageBody.append(mwalleteAmountLabel);

			}

			if(ussdSessionMgmt.getBusinessId().equals("GHBRB") && ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID)){
				responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setBackOptionReq("false");
				responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("false");
			}

			txSessions.put(USSDConstants.GHIPPS_CUSTOMER_NAME,customerName);
			USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			menuItemDTO.setPageBody(pageBody.toString());
			menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
			menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			menuItemDTO.setPaginationType(PaginationEnum.SPACED);
			menuItemDTO.setPageBody(pageBody.toString());
			setNextScreenSequenceNumber(menuItemDTO);
		}
		return menuItemDTO;

	}

	private String getCustomerName(ResponseBuilderParamsDTO responseBuilderParamsDTO,MenuItemDTO menuItemDTO) throws USSDNonBlockingException, USSDBlockingException{
		String customeName="";
		try{
			ObjectMapper mapper = new ObjectMapper();
			UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
			SearchIndividualCustNQResponse searchIndividualCustNQResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
					SearchIndividualCustNQResponse.class);
			if (searchIndividualCustNQResponse != null && searchIndividualCustNQResponse.getPayHdr() != null) {
				String resCode=searchIndividualCustNQResponse.getPayHdr().getResCde();
				USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
				String countryCode="",lang="";
				if (ussdSessionMgmt.getUserProfile() != null) {
					countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
					lang = ussdSessionMgmt.getUserProfile().getLanguage();
				}
				Locale locale = new Locale(lang, countryCode);
				if (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(resCode)) {
					SearchIndividualCustNQPayData searchIndividualCustNQPayData=searchIndividualCustNQResponse.getPayData();
					customeName=searchIndividualCustNQPayData.getFullName();
				}else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09113.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09123.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09126.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09127.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09128.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09129.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09131.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else {
					LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else if (e instanceof USSDBlockingException) {
				throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		return customeName;
	}


	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

	}
	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		UserProfile profile = ussdSessionMgmt.getUserProfile();
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
