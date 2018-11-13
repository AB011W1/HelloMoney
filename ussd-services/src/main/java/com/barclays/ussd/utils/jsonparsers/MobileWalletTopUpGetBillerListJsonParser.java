package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillerList;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.CatPayeeLst;

public class MobileWalletTopUpGetBillerListJsonParser implements BmgBaseJsonParser {
    // , ScreenSequenceCustomizer {

    @Autowired
    ListValueResServiceImpl listValueResService;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MobileWalletTopUpGetBillerListJsonParser.class);

    public ListValueResServiceImpl getListValueResService() {
	return listValueResService;
    }

    public void setListValueResService(ListValueResServiceImpl listValueResService) {
	this.listValueResService = listValueResService;
    }

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	int maxBillers = 0;

	try {

	    BillerList billerLst = mapper.readValue(jsonString, BillerList.class);
	    if (billerLst != null) {
		if (billerLst.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(billerLst.getPayHdr().getResCde())) {
		    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		    String sysPrefMaxBillers = getSystemPreferenceData(ussdSessionMgmt.getUserProfile(), SystemPreferenceConstants.SYS_PARAM_BNF,
			    SystemPreferenceConstants.MAX_BILLER_SIZE);

		    boolean check = StringUtils.isNotEmpty(sysPrefMaxBillers) && StringUtils.isNumeric(sysPrefMaxBillers);

		    if (check) {
			maxBillers = Integer.parseInt(sysPrefMaxBillers);
		    }

		    // set the payee accnt list to the session
		    for (CatPayeeLst ele : billerLst.getPayData().getCatzedPayLst()) {

			if (USSDConstants.BILL_PAYMENT_PAY_CATEGORY.equalsIgnoreCase(ele.getPayCat())) {
			    if (ussdSessionMgmt.getTxSessions() == null) {
				ussdSessionMgmt.setTxSessions(new HashMap<String, Object>(5));
			    }

			    int benfSize = ele.getBnfLst().size();

			    if (maxBillers == 0 || benfSize == 0) {
				throw new USSDNonBlockingException(USSDExceptions.BPY00660_WALLET_EMPTY.getBmgCode());
			    } else if (maxBillers <= benfSize) {
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId(),
					ele.getBnfLst().subList(0, maxBillers));
			    } else if (maxBillers >= benfSize) {
				ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId(), ele.getBnfLst());
			    }

			}
		    }
		} else if (billerLst.getPayHdr() != null) {
			if(USSDExceptions.BPY00660.getBmgCode().equals(billerLst.getPayHdr().getResCde())){
				LOGGER.error("Error while servicing custome " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(USSDExceptions.BPY00660_WALLET_EMPTY.getBmgCode());
			}else{
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(billerLst.getPayHdr().getResCde());
			}
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
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

   /* public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOURTEEN.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<Beneficiery> bnfLst = (List<Beneficiery>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getTranId());
	if (bnfLst != null && bnfLst.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }*/

    public String getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
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
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOURTEEN.getSequenceNo());
    }
}