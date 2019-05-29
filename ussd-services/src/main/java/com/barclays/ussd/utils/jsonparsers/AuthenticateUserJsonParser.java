package com.barclays.ussd.utils.jsonparsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.CasaAccount;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthenticateUserPayData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerProfile;

public class AuthenticateUserJsonParser implements BmgBaseJsonParser {
    private static final String NO_ACCOUNTS_ACTIVE_FOR_LOGIN = "ASM00200";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AuthenticateUserJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {

	    AuthUserData userAuthObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), AuthUserData.class);
	    if (userAuthObj != null) {
		String resCode = userAuthObj.getPayHdr().getResCde();
		if (userAuthObj.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.SUCCESS.getBmgCode(), resCode) || resCode.equals("THM00000"))) {
		    USSDSessionManagement sessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();
		    if (resCode.equals("THM00000")) {// forward THM migrated customer for pin change
			if (LOGGER.isDebugEnabled()) {
			    LOGGER.debug("Redirect to Should Change PIN Flow");
			}
			menuDTO = handleShouldChangePinFlow(responseBuilderParamsDTO, menuDTO, userAuthObj);
		    } else {
			List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();
			if (custActs == null || custActs.isEmpty()) {
			    throw new USSDBlockingException(USSDExceptions.USSD_NO_ACTIVE_ACCTS_FOR_LOGIN.getBmgCode());
			}
			menuDTO = renderMenuOnScreen(userAuthObj.getPayData(), responseBuilderParamsDTO);
			Map<String, Object> userAuthRespMap = new HashMap<String, Object>();
			userAuthRespMap.put(USSDConstants.AUTH_USR_RESP, userAuthObj);
			menuDTO.setUserAuthRespMap(userAuthRespMap);
			sessionManagement.setUserAuthObj(userAuthObj);
		    }
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TOKEN_BLOCKED.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_TOKEN_BLOCKED.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08729.getBmgCode(), resCode))) {
		    throw new USSDNonBlockingException(USSDExceptions.BEM08729.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08733.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_TOKEN_BLOCKED.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08734.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_TOKEN_BLOCKED.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08788.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.BEM08788.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08789.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.BEM08789.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08792.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.BEM08788.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08784.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.BEM08788.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BMB90004.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.BMB90004.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM06001.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.BEM06001.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(NO_ACCOUNTS_ACTIVE_FOR_LOGIN, resCode))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_NO_ACTIVE_ACCTS_FOR_LOGIN.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_PIN_CHANGE_WINDOW_EXPIRED.getBmgCode(), resCode))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_PIN_CHANGE_WINDOW_EXPIRED.getBmgCode());
		} else if (userAuthObj.getPayHdr() != null && resCode.contains("THM00")) {
		   //Forgot Pin Change
			// throw new USSDBlockingException(resCode);
		//	menuDTO=mergeForgotPinFlow(responseBuilderParamsDTO, menuDTO, userAuthObj);
			  throw new USSDNonBlockingException(USSDExceptions.BEM08729.getBmgCode());
		}else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase("BEM09027", resCode))) {
		    throw new USSDNonBlockingException("BEM09027");
		}
		else if (userAuthObj.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BEM08744.getBmgCode(), resCode) || StringUtils.equalsIgnoreCase(
				USSDExceptions.BEM08756.getBmgCode(), resCode))) {
		    menuDTO = handleShouldChangePinFlow(responseBuilderParamsDTO, menuDTO, userAuthObj);

		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM9430.getBmgCode(), resCode))) {
		    menuDTO = handleShouldChangePinFlow(responseBuilderParamsDTO, menuDTO, userAuthObj);
		} else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM9431.getBmgCode(), resCode))) {
		    throw new USSDNonBlockingException(USSDExceptions.BEM9431.getBmgCode());
		}else if (userAuthObj.getPayHdr() != null && (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09032.getBmgCode(), resCode))) {
		    throw new USSDNonBlockingException(USSDExceptions.BEM09032.getBmgCode());
		}else if (userAuthObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing Option Code " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(userAuthObj.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    }
	} catch (JsonParseException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (JsonMappingException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (IOException e) {
	    LOGGER.fatal("IOException : " + e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	return menuDTO;
    }

    private MenuItemDTO handleShouldChangePinFlow(ResponseBuilderParamsDTO responseBuilderParamsDTO, MenuItemDTO menuDTO, AuthUserData userAuthObj) {
	/* Set the pin change required flag to the user auth object */
	if (userAuthObj.getPayData() == null) {
	    AuthenticateUserPayData payData = new AuthenticateUserPayData();
	    CustomerProfile custProf = new CustomerProfile();
	    payData.setCustProf(custProf);
	    userAuthObj.setPayData(payData);
	}
	if (menuDTO == null) {
	    menuDTO = new MenuItemDTO();
	}
	CustomerProfile custProfData = userAuthObj.getPayData().getCustProf();
	// userAuthObj.getPayData().getCustProf().setPinSta(USSDConstants.PIN_CHANGE_REQ);
	custProfData.setPinSta(USSDConstants.PIN_CHANGE_REQ);
	Map<String, Object> userAuthRespMap = new HashMap<String, Object>();
	userAuthRespMap.put(USSDConstants.AUTH_USR_RESP, userAuthObj);
	menuDTO.setUserAuthRespMap(userAuthRespMap);
	USSDSessionManagement sessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();
	sessionManagement.clean();
	sessionManagement.setUserAuthObj(userAuthObj);
	return menuDTO;
    }

    /**
     * @param authenticateUserPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(AuthenticateUserPayData authenticateUserPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();

	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	UserProfile profile = ussdSessionMgmt.getUserProfile();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	String accntLabel = ussdResourceBundle.getLabel(USSDConstants.LBL_ACCNT_NO, new Locale(profile.getLanguage(), profile.getCountryCode()));
	// String avBalLabel =
	// ussdResourceBundle.getLabel(USSDConstants.LBL_AVAIL_AC_BAL, new
	// Locale(profile.getLanguage(),
	// profile.getCountryCode()));
	String ldgBalLabel = ussdResourceBundle.getLabel(USSDConstants.LBL_CURR_AC_BAL, new Locale(profile.getLanguage(), profile.getCountryCode()));
	if (null != authenticateUserPayData && null != authenticateUserPayData.getCustActs()) {

	    List<CustomerMobileRegAcct> customerMobileRegAcctList = authenticateUserPayData.getCustActs();
	    List<CasaAccount> primaryAcctList = new ArrayList<CasaAccount>();
	    for (CustomerMobileRegAcct custMobileRegAcct : customerMobileRegAcctList) {
		CasaAccount customerMobileRegAcct = new CasaAccount();
		customerMobileRegAcct.setAccountNumber(custMobileRegAcct.getActNo());
		customerMobileRegAcct.setBranchCode(custMobileRegAcct.getBrnCde());
		customerMobileRegAcct.setPrimaryIndicator(custMobileRegAcct.getPriInd());
		customerMobileRegAcct.setCurr(custMobileRegAcct.getCurr());
		customerMobileRegAcct.setAccStatus(custMobileRegAcct.getAccStatus());
		primaryAcctList.add(customerMobileRegAcct);
		profile.setCasaAccountList(primaryAcctList);

		pageBody.append(accntLabel).append(custMobileRegAcct.getMkdActNo());
		pageBody.append(USSDConstants.NEW_LINE);
		/*
		 * pageBody.append(avBalLabel).append(custMobileRegAcct.getAvblBal ( ).getCurr()).append(USSDConstants.SINGLE_WHITE_SPACE).append(
		 * custMobileRegAcct.getAvblBal().getAmt()); pageBody.append(USSDConstants.NEW_LINE);
		 */
		pageBody.append(ldgBalLabel).append(custMobileRegAcct.getCurBal().getCurr()).append(USSDConstants.SINGLE_WHITE_SPACE).append(
			custMobileRegAcct.getCurBal().getAmt());
		pageBody.append(USSDConstants.NEW_LINE);
	    }
	    ussdSessionMgmt.setUserProfile(profile);
	    menuItemDTO.setPageBody(pageBody.toString());
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);

	} else { // Added temporary for the auth
	    menuItemDTO.setPageBody("Success");
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	}
	setNextScreenSequenceNumber(menuItemDTO);
	ussdSessionMgmt.clean();
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }
    //Forgot Pin Change
    private MenuItemDTO mergeForgotPinFlow(ResponseBuilderParamsDTO responseBuilderParamsDTO, MenuItemDTO menuDTO, AuthUserData userAuthObj) {
    	/* Set the pin change required flag to the user auth object */
    	if (userAuthObj.getPayData() == null) {
    	    AuthenticateUserPayData payData = new AuthenticateUserPayData();
    	    CustomerProfile custProf = new CustomerProfile();
    	    payData.setCustProf(custProf);
    	    userAuthObj.setPayData(payData);
    	}
    	if (menuDTO == null) {
    	    menuDTO = new MenuItemDTO();
    	}
    	CustomerProfile custProfData = userAuthObj.getPayData().getCustProf();
    	// userAuthObj.getPayData().getCustProf().setPinSta(USSDConstants.PIN_CHANGE_REQ);
    	custProfData.setPinSta(USSDConstants.FORGOT_PIN_REQ);
    	Map<String, Object> userAuthRespMap = new HashMap<String, Object>();
    	userAuthRespMap.put(USSDConstants.AUTH_USR_RESP, userAuthObj);
    	menuDTO.setUserAuthRespMap(userAuthRespMap);
    	USSDSessionManagement sessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();
    	sessionManagement.clean();
    	sessionManagement.setUserAuthObj(userAuthObj);
    	return menuDTO;
        }
}
