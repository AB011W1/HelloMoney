package com.barclays.ussd.bmg.airtime.request;

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
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.SrcAccount;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;

public class AirtimeTopUpSavBnfValidateReqBuilder implements BmgBaseRequestBuilder {
    private static final String PAY_GRP = "payGrp";
    private static final String BILLER_TYPE = "billerType";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AirtimeTopUpSavBnfValidateReqBuilder.class);
    @Autowired
    private ListValueResServiceImpl listValueResService;

    @Autowired
    private IBillersLstService billersLstService;

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	UserProfile userProfile = requestBuilderParamsDTO.getUssdSessionMgmt().getUserProfile();
	String payNickNameMaxLength = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_BNF,
		SystemPreferenceConstants.PAYEE_NICKNAME_LENGTH_MAX);

	String billerUsrInput = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_NEW_BENE_SRC_MON.getParamName());
	if(billerUsrInput==null){
		billerUsrInput=userInputMap.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName());
	}
	String billerId = StringUtils.EMPTY;
	String billerType = StringUtils.EMPTY;
	List<Biller> mnoList = (List<Biller>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
	/*String countryCode = requestBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode();*/
	if (StringUtils.isNotEmpty(billerUsrInput) && StringUtils.isNumeric(billerUsrInput) && mnoList!=null && !mnoList.isEmpty()) {
	    billerId=mnoList.get(Integer.parseInt(billerUsrInput)-1).getBillerId();
	    billerType=mnoList.get(Integer.parseInt(billerUsrInput)-1).getBillerCatId();
	}


	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDLengthValidator(payNickNameMaxLength));
	try {
	    validator.validate(requestBuilderParamsDTO.getUserInput());
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode(), e);
	    e.addErrorParam(payNickNameMaxLength);
	    e.setErrorCode(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode());
	    throw e;
	}


	 /*private String billerId;
	    private String billerNickName;
	    private String billerReferenceNum;//AIRTIME_SUBMIT
	    private String billerType;
	    private String billerAccNum;
	    private String areaCode;*/

	requestParamMap.put(USSDInputParamsEnum.AIRTIME_TOPUP_SAVE_BEN_NICK_NAME.getParamName(),requestBuilderParamsDTO.getUserInput());
	requestParamMap.put(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName(), userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName()));
	requestParamMap.put(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName(), billerId);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(BILLER_TYPE, billerType);

	requestParamMap.put(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName(),userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName()));

	//DataBundle Change
	if(billerType.toUpperCase().equals("DATABUNDLE"))
		requestParamMap.put(PAY_GRP, "DB");
	else
		requestParamMap.put(PAY_GRP, "AT");
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
