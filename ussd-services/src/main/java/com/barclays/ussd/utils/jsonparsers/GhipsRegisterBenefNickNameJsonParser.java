package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SearchIndividualCustNQResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.SearchIndividualCustNQPayData;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDLengthValidator;

public class GhipsRegisterBenefNickNameJsonParser implements BmgBaseJsonParser,SystemPreferenceValidator {

	@Autowired
	private ListValueResServiceImpl listValueResService;
	private static final String BEM_ERRCODE_7502 = "BEM7502";
	private static final Logger LOGGER = Logger.getLogger(GhipsRegisterBenefNickNameJsonParser.class);

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException{
		MenuItemDTO menuDTO = new MenuItemDTO();
		menuDTO.setPageHeader("LBL9999");
		String customerName=getCustomerName(responseBuilderParamsDTO,menuDTO);
		if(customerName!=null && !customerName.equals("")){
			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO,customerName,menuDTO);
		}
		return menuDTO;
	}

	/**
	 * @param payData
	 * @param responseBuilderParamsDTO
	 * @return MenuItemDTO
	 */
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO,String customerName,MenuItemDTO menuItemDTO) {
		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
			txSessions = new HashMap<String, Object>(8);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDConstants.GHIPPS_CUSTOMER_NAME,customerName);
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// Refer to sequencer.properties to set the below value
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
	}

	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		UserProfile userProfile = ussdSessionMgmt.getUserProfile();
		String payNickNameMaxLength = getSystemPreferenceData(userProfile, SystemPreferenceConstants.SYS_PARAM_BNF,
				SystemPreferenceConstants.PAYEE_NICKNAME_LENGTH_MAX);
		USSDCompositeValidator validator = new USSDCompositeValidator(new USSDLengthValidator(payNickNameMaxLength));
		try {
			validator.validate(userInput);
		} catch (USSDNonBlockingException e) {
			LOGGER.error(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode(), e);
			e.addErrorParam(payNickNameMaxLength);
			e.setErrorCode(USSDExceptions.USSD_PAYEE_NICK_NAME_INVALID_LEN.getUssdErrorCode());
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
	private String getCustomerName(ResponseBuilderParamsDTO responseBuilderParamsDTO,MenuItemDTO menuItemDTO) throws USSDNonBlockingException, USSDBlockingException{
		String customeName="";
		try{

			ObjectMapper mapper = new ObjectMapper();
			SearchIndividualCustNQResponse searchIndividualCustNQResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
					SearchIndividualCustNQResponse.class);
			if (searchIndividualCustNQResponse != null && searchIndividualCustNQResponse.getPayHdr() != null) {
				String resCode=searchIndividualCustNQResponse.getPayHdr().getResCde();
				UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
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
				}  else if (BEM_ERRCODE_7502.equalsIgnoreCase(resCode)) {
					LOGGER.error("Account number does not exist: " + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09113.getBmgCode(), resCode)) {
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
		return customeName;
	}



}
