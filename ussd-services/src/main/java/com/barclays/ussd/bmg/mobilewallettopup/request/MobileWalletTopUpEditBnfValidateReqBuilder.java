package com.barclays.ussd.bmg.mobilewallettopup.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.BillerArea;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.common.services.IBillersLstService;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.SrcAccount;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;

public class MobileWalletTopUpEditBnfValidateReqBuilder implements BmgBaseRequestBuilder {
    private static final String PAY_GRP = "payGrp";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MobileWalletTopUpEditBnfValidateReqBuilder.class);
    @Autowired
    private ListValueResServiceImpl listValueResService;

    @Autowired
    private IBillersLstService billersLstService;

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	UserProfile userProfile = requestBuilderParamsDTO.getUssdSessionMgmt().getUserProfile();
/*	String payNickNameMaxLength = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_BNF,
		SystemPreferenceConstants.PAYEE_NICKNAME_LENGTH_MAX);
*/
	String billerUsrInput = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_NEW_BENE_SRC_MON.getParamName());

	String billerId = StringUtils.EMPTY;
	List<MobileWalletProvider> mnoList = (List<MobileWalletProvider>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getTranId());
	/*String countryCode = requestBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode();*/
	if (StringUtils.isNotEmpty(billerUsrInput) && StringUtils.isNumeric(billerUsrInput) && mnoList!=null && !mnoList.isEmpty()) {
	    billerId=mnoList.get(Integer.parseInt(billerUsrInput)-1).getBillerId();
	}


	/*USSDCompositeValidator validator = new USSDCompositeValidator(new USSDLengthValidator(payNickNameMaxLength));
	try {
	    validator.validate(requestBuilderParamsDTO.getUserInput());
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode(), e);
	    e.addErrorParam(payNickNameMaxLength);
	    e.setErrorCode(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode());
	    throw e;
	}*/


	 /*private String billerId;
	    private String billerNickName;
	    private String billerReferenceNum;//AIRTIME_SUBMIT
	    private String billerType;
	    private String billerAccNum;
	    private String areaCode;*/
	String payeeId = "";
	Beneficiery bene =null;
	if (requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() != null
		&& requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId()) != null) {
	    List<Beneficiery> lstBenef = (List<Beneficiery>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId());
	    if (!lstBenef.isEmpty()) {
	    	bene= lstBenef.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getParamName())) - 1);
		payeeId = bene.getPayId();
	    }
	}
	requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_SAVE_BEN_NICK_NAME.getParamName(),bene.getDisLbl());
	requestParamMap.put(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName(), userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName()));
	requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName(), billerId);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);


	requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName()));

	requestParamMap.put(PAY_GRP, "WT");
	request.setRequestParamMap(requestParamMap);

	return request;
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
}
