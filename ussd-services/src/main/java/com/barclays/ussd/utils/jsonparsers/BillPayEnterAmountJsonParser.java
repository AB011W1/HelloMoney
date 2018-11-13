/**
 * BillPayEnterAmountJsonParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.bmg.service.impl.BillerServiceImpl;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.bmg.paybills.request.PayBillSelFrmAcnt;
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
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

/**
 * @author BTCI
 *
 */
public class BillPayEnterAmountJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator
	,ScreenSequenceCustomizer {
    private static final Logger LOGGER = Logger.getLogger(BillPayEnterAmountJsonParser.class);

    @Autowired
    private ListValueResServiceImpl listValueResService;

    @Autowired
    private BillerServiceImpl billerService;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	String minBillPayAmt = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_BP,
		SystemPreferenceConstants.BILLPAY_MIN_AMT_SNDR);
	String maxBillPayAmt = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_BP,
		SystemPreferenceConstants.BILLPAY_MAX_AMT_SNDR);

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(minBillPayAmt, maxBillPayAmt));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAmount(userInput);//CR-86
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
	    //CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(minBillPayAmt);
	    e.addErrorParam(maxBillPayAmt);
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

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();

		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

		List<Beneficiery> catzedPayLst = (List<Beneficiery>) ussdSessionMgmt.getTxSessions().get(
				    USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getTranId());
		String userSelection = userInputMap.get(USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getParamName());

		Beneficiery billPaySelectedBiller = catzedPayLst.get(Integer.parseInt(userSelection) - 1);

		BillerServiceRequest request = new BillerServiceRequest();
		request.setBusinessId(ussdSessionMgmt.getBusinessId());
		request.setBillerId(billPaySelectedBiller.getBillerId());

		BillerServiceResponse billerServiceResponse = billerService.getActionCodeForBillerId(request);

		if (billerServiceResponse.getBillerCreditDTO() != null) {
				BillerCreditDTO billerCreditDTO = billerServiceResponse.getBillerCreditDTO();
				txSessions.put("BillerCreditDTO", billerCreditDTO);
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();

			}
		return seqNo;
	 }

}
