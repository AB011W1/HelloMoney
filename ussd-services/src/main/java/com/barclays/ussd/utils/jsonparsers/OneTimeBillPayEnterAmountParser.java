/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.bmg.service.impl.BillerServiceImpl;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.common.services.IBillersLstService;
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
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitAccount;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitResponse;
import com.barclays.ussd.validation.USSDBackFlowValidator;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;

/**
 * @author BTCI
 *
 */
public class OneTimeBillPayEnterAmountParser implements BmgBaseJsonParser, SystemPreferenceValidator
 , ScreenSequenceCustomizer
{
    private static final Logger LOGGER = Logger.getLogger(OneTimeBillPayEnterAmountParser.class);

    @Autowired
    ListValueResServiceImpl listValueResService;
    @Autowired
    private BillerServiceImpl billerService;

    @Autowired
	private IBillersLstService billersLstService;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    OTBPInitResponse otbpInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OTBPInitResponse.class);
	    if (otbpInitResponse != null) {
		if (otbpInitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(otbpInitResponse.getPayHdr().getResCde())) {
		    Collections.sort(otbpInitResponse.getPayData().getFromAcctList(), new OneTimeBillPayAcctNumComparator());
		    // menuDTO = renderMenuOnScreen(otbpInitResponse.getPayData(), responseBuilderParamsDTO);
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		    // set the from accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			    .put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getTranId(), otbpInitResponse.getPayData().getFromAcctList());
		} else if (otbpInitResponse.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(otbpInitResponse.getPayHdr().getResCde());
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

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile profile = ussdSessionMgmt.getUserProfile();
	// Code for replacing sys pref ref data code
	String billPayMinAmt = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_BP,
		SystemPreferenceConstants.BILLPAY_MIN_AMT_SNDR);
	String billPayMaxAmt = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_BP,
		SystemPreferenceConstants.BILLPAY_MAX_AMT_SNDR);

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(billPayMinAmt, billPayMaxAmt));
	USSDBackFlowValidator backFlowValidator = new USSDBackFlowValidator();//CR-86
	try {
		backFlowValidator.validateAmount(userInput);//CR-86
	    validator.validate(userInput);
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
	    //CR-86
	    e.setBackFlow(true);
	    e.addErrorParam(userInput);
	    e.addErrorParam(billPayMinAmt);
	    e.addErrorParam(billPayMaxAmt);
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
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(),
		    USSDExceptions.USSD_SYS_PREF_MISSING.getUssdErrorCode());
	}
	return listValueCacheDTO.getLabel();
    }

    @SuppressWarnings("unchecked")
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	//masterpass QR
    String business_id=ussdSessionMgmt.getBusinessId();
    String tranid=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId();
    List<BillersListDO> blrsLstDO = new ArrayList<BillersListDO>();
    BillersListDO OneTimePaymentSelected = new BillersListDO();
    if((business_id!=null && "TZBRB".equals(business_id))  &&  (tranid!=null && "TZ_MASTERPASS_QR_BILLER".equals(tranid))){
		try {
			BillersListDO biller = this.billersLstService.getBillerInfo(ussdSessionMgmt
						.getCountryCode(), "MPQR-3", ussdSessionMgmt
						.getMsisdnNumber(), ussdSessionMgmt.getBusinessId());
			blrsLstDO.add(biller);
			OneTimePaymentSelected = blrsLstDO.get(0);
		} catch (USSDNonBlockingException e) {
			e.printStackTrace();
		}
    }else{
	blrsLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
	String selectedOneTimePaymentProvider=	userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName());
	OneTimePaymentSelected = blrsLstDO.get(Integer.parseInt(selectedOneTimePaymentProvider) - 1);
    }

	BillerServiceRequest request = new BillerServiceRequest();
	request.setBusinessId(ussdSessionMgmt.getBusinessId());
	request.setBillerId(OneTimePaymentSelected.getBillerId());


	BillerServiceResponse billerServiceResponse = billerService.getActionCodeForBillerId(request);

	if (billerServiceResponse.getBillerCreditDTO() != null) {
		BillerCreditDTO billerCreditDTO = billerServiceResponse.getBillerCreditDTO();
		txSessions.put("BillerCreditDTO",billerCreditDTO);
		String actionCode = billerServiceResponse.getBillerCreditDTO().getActionCode();
		if (actionCode != null && !actionCode.isEmpty()) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOURTEEN.getSequenceNo();
		}
	}
	return seqNo;
    }
}

class OneTimeBillPayAcctNumComparator implements Comparator<OTBPInitAccount>, Serializable {
    /**
		 *
		 */
    private static final long serialVersionUID = 1L;

    public int compare(final OTBPInitAccount accountDetail1, final OTBPInitAccount accountDetail2) {
	int retVal = 0;
	if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail1.getPriInd())) {
	    retVal = -1;
	} else if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail2.getPriInd())) {
	    retVal = 1;
	} else {
	    retVal = 0;
	}
	return retVal;
    }
}