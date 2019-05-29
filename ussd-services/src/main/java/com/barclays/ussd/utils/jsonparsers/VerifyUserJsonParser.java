package com.barclays.ussd.utils.jsonparsers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.verifyuser.VerifyUserPayData;
import com.barclays.ussd.utils.jsonparsers.bean.verifyuser.VerifyUserResponse;

public class VerifyUserJsonParser implements BmgBaseJsonParser {

    private static final String USER_STATUS_DEREGISTERED = "DEREGISTER";
    private static final String USER_STATUS_BLOCKED = "BLOCK";
    private static final String USER_STATUS_SUSPENDED = "SUSPEND";
    private static final String USER_DOESNT_EXISTS = "BEM08777";
    private static final Logger LOGGER = Logger.getLogger(VerifyUserJsonParser.class);
    private static final Logger TIME_AUDIT_LOGGER = Logger.getLogger("com.barclays.ussd.audit-time");

    //Commented as to remove extra XAS call-Prod issue
    private static final String USER_DOESNT_EXISTS_IN_MCE = "BEM09074";
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
	    VerifyUserResponse verifyUserResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), VerifyUserResponse.class);
	    if (verifyUserResponse != null) {
		if (verifyUserResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(verifyUserResponse.getPayHdr().getResCde())) {
		    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		    VerifyUserPayData payData = verifyUserResponse.getPayData();
		    String userStatusInMCE = payData.getUserStatusInMCE();

		    if (userStatusInMCE == null || StringUtils.isEmpty(userStatusInMCE)
			    || StringUtils.equalsIgnoreCase(userStatusInMCE, USER_STATUS_DEREGISTERED)) {
			throw new USSDBlockingException(USSDExceptions.USSD_MOBILE_NOT_REG.getBmgCode());
		    } else if (StringUtils.equalsIgnoreCase(userStatusInMCE, USER_STATUS_BLOCKED)
			    || StringUtils.equalsIgnoreCase(userStatusInMCE, USER_STATUS_SUSPENDED)) {
			throw new USSDBlockingException(USSDExceptions.USSD_SERVICES_DISABLED.getBmgCode());
		    }

		    String prefLang = payData.getLangPref();
		    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
		    if (prefLang != null && StringUtils.isNotEmpty(prefLang)) {
			userProfile.setLanguage(prefLang);
		    }
		    userProfile.setPinSta(payData.getCryptoPinStatus());
		    userProfile.setUsrSta(payData.getUserStatusInMCE());

		    //CR-77
		    userProfile.setCustomerFirstName(payData.getCustomerFirstName());

		    // userProfile.setUsrSta(USSDConstants.USER_STATUS_MIGRATED);
		    if (StringUtils.equalsIgnoreCase(USSDConstants.USER_STATUS_MIGRATED, userProfile.getUsrSta())) {
			ussdSessionMgmt.setUserMigration(true);
		    }

			//CR-35: Self Registration Change
		    if(null != payData.getCustomerAccessStatusCode()){
		    	ussdSessionMgmt.setCustomerAccessStatusCode(payData.getCustomerAccessStatusCode());
		    }

		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);

		} else if (verifyUserResponse.getPayHdr() != null && USER_DOESNT_EXISTS.equalsIgnoreCase(verifyUserResponse.getPayHdr().getResCde())) {
		    TIME_AUDIT_LOGGER.info("This is the UnRegistered User,,,,");
		    throw new USSDBlockingException(USSDExceptions.USSD_MOBILE_NOT_REG.getBmgCode());
		}else if (verifyUserResponse.getPayHdr() != null && USER_DOESNT_EXISTS_IN_MCE.equalsIgnoreCase(verifyUserResponse.getPayHdr().getResCde())) {
			 //Added as to remove extra XAS call-Prod issue to enhance performance
			TIME_AUDIT_LOGGER.info("This is the UnRegistered User in MCEs,,,,");
		    throw new USSDBlockingException(USSDExceptions.USSD_MOBILE_NOT_REG.getBmgCode());
		}
		else if (verifyUserResponse.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(verifyUserResponse.getPayHdr().getResCde());
		} else if (verifyUserResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BMB90004.getBmgCode(), verifyUserResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.BMB90004.getBmgCode());
		} else if (verifyUserResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BEM06001.getBmgCode(), verifyUserResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.BEM06001.getBmgCode());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    } else {
		LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	return menuDTO;
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }
}
