package com.barclays.ussd.bmg.regbillers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.BillerArea;
import com.barclays.ussd.bean.BillersListDO;
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
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;

public class REGBValidateReqBuilder implements BmgBaseRequestBuilder {
    private static final String PAY_GRP = "payGrp";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(REGBValidateReqBuilder.class);

	private static final String BILLER_TYPE = "billerType";
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

	USSDCompositeValidator validator = new USSDCompositeValidator(new USSDLengthValidator(payNickNameMaxLength));
	try {
	    validator.validate(requestBuilderParamsDTO.getUserInput());
	} catch (USSDNonBlockingException e) {
	    LOGGER.error(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode(), e);
	    e.addErrorParam(payNickNameMaxLength);
	    e.setErrorCode(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode());
	    throw e;
	}
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();

	/**
	 * CR73 changes incorporated
	 */
	String isFromSaveBiller = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId());

	String billerUsrInput = StringUtils.EMPTY.equals(isFromSaveBiller)?userInputMap.get(USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getParamName()):userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName());

	String billerId = StringUtils.EMPTY;
	String countryCode = requestBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode();
	String billerType=StringUtils.EMPTY;;
	
	//TZNBC Menu Optimization
	List<BillersListDO> blrsLst ;
	BillersListDO biller=null;
	String category="";
	if (ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")) {
		//Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		List<BillersListDO> categoryLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_CATLIST.getTranId());
		if(null!=categoryLstDO) { 
			BillersListDO categoryListDO = categoryLstDO.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_CATLIST
					.getParamName())) - 1);
			category= categoryListDO.getBillerCategoryId();
		}
	}
	if (StringUtils.isNotEmpty(billerUsrInput) && StringUtils.isNumeric(billerUsrInput)) {
		if(ussdSessionMgmt.getBusinessId().equals("TZNBC") && null!=category && ""!=category) {
			blrsLst=this.billersLstService.getBillerPerCategory(requestBuilderParamsDTO.getMsisdnNo(), ussdSessionMgmt.getBusinessId(), category);
			if(null!=blrsLst)
				biller=blrsLst.get(Integer.parseInt(billerUsrInput)-1);
	}
		else
			biller = this.billersLstService.getBillerId(Integer.parseInt(billerUsrInput), countryCode,requestBuilderParamsDTO.getMsisdnNo(),requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
		billerId= biller.getBillerId();
		billerType = biller.getBillerCategoryId();
	}

	List<BillerArea> billerArea = StringUtils.EMPTY.equals(isFromSaveBiller)?(List<BillerArea>) ussdSessionMgmt.getTxSessions()
		    .get(USSDInputParamsEnum.REG_BILLER_AREA_CODE.getTranId()): (List<BillerArea>) ussdSessionMgmt.getTxSessions()
		    .get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId());

	if(billerArea != null)
	{
		int selectedAreaInput = Integer.parseInt(StringUtils.EMPTY.equals(isFromSaveBiller)?userInputMap.get(USSDInputParamsEnum.REG_BILLER_AREA_CODE.getParamName()):userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName()));

	    String selectArea = billerArea.get(selectedAreaInput - 1).getAreaName();
	    requestParamMap.put(USSDInputParamsEnum.REG_BILLER_AREA_CODE.getParamName(), selectArea);
	}
//	}
	requestParamMap.put(USSDInputParamsEnum.REG_BILLER_NICK_NAME.getParamName(),requestBuilderParamsDTO.getUserInput());
	requestParamMap.put(USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getParamName(), billerId);
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(BILLER_TYPE,billerType);
	//CR-57
	String businessId = requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId();
	String refNum = StringUtils.EMPTY.equals(isFromSaveBiller)?userInputMap.get(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName()):userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BL_REF.getParamName());
	String selectedFrmDstvType = userInputMap.get((USSDConstants.SELECTED_DSTV_BILLER_TYPE));
			/*StringUtils.EMPTY.equals(isFromSaveBiller)
			?userInputMap.get((USSDConstants.SELECTED_DSTV_BILLER_TYPE))
			:userInputMap.get((USSDConstants.SELECTED_DSTV_BILLER_TYPE));*/


	userInputMap.put(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName(), refNum);

	if(selectedFrmDstvType != null && businessId.equalsIgnoreCase(USSDConstants.BUSINESS_ID_ZWBRB) && userInputMap.get(USSDConstants.SELECTED_BILLER_REGB).equalsIgnoreCase("DSTVZIM-2")
			&& selectedFrmDstvType.equalsIgnoreCase("DSTV BO") &&!refNum.substring(0, 2).equalsIgnoreCase("BO") ){

		String dstvBoRef = "BO" + userInputMap.get(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName());
		userInputMap.put(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName(), dstvBoRef);
		userInputMap.put(USSDConstants.SELECTED_DSTV_BO, selectedFrmDstvType );

	}//end

	requestParamMap.put(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName(),userInputMap.get(USSDInputParamsEnum.REG_BILLER_GET_REFNO.getParamName()));

	requestParamMap.put(PAY_GRP, USSDConstants.BILL_PAY);
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
