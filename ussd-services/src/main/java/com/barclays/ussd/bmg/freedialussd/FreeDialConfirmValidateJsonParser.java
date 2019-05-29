/**
 *
 */
package com.barclays.ussd.bmg.freedialussd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidateResponse;



public class FreeDialConfirmValidateJsonParser implements BmgBaseJsonParser {

	private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
	private static final Logger LOGGER = Logger.getLogger(FreeDialConfirmValidateJsonParser.class);
	private static final String TRANSACTION_AIRTIME_LABEL = "label.airtime";
	private static final String TRANSACTION_AIRTIME_ERROR_LABEL = "label.airtime.error";
	private static final String DEBIACCNUM_LABEL = "label.debit.accnum";

	@Autowired
	private ListValueResServiceImpl listValueResService;


	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			AirtimeValidateResponse airtimeValidateResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
					AirtimeValidateResponse.class);

			if (airtimeValidateResponse != null) {
				if (airtimeValidateResponse.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(airtimeValidateResponse.getPayHdr().getResCde())) {
					menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, airtimeValidateResponse.getPayData());
					List<String> txnRefNo = new ArrayList<String>(1);
					txnRefNo.add(airtimeValidateResponse.getPayData().getTxnRefNo());
					responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.FREE_DIAL_CONFIRM.getTranId(), txnRefNo);
				}  else if (airtimeValidateResponse.getPayHdr() != null) {
					USSDSessionManagement session = responseBuilderParamsDTO.getUssdSessionMgmt();
					Map<String, Object> userInputMapAirtel = session.getTxSessions();
					if(userInputMapAirtel!=null && userInputMapAirtel.get("extra")!=null && (userInputMapAirtel.get("extra").toString().equals("FREEDIALAIRTEL") || userInputMapAirtel.get("extra").toString().equals("FREEDIALAIRTELZM"))){
						LOGGER.fatal("unable to service: " + airtimeValidateResponse.getPayHdr().getResMsg());
						if(airtimeValidateResponse.getPayHdr().getResCde().equals("FTR00507"))
							throw new USSDBlockingException("FTR00530");
						else if(airtimeValidateResponse.getPayHdr().getResCde().equals("BMB90003"))
							throw new USSDBlockingException("BMB90014");
						else if(airtimeValidateResponse.getPayHdr().getResCde().equals("BMB90010"))
							throw new USSDBlockingException("BMB90010");
						else if(airtimeValidateResponse.getPayHdr().getResCde().equals("BPY00614"))
							throw new USSDBlockingException("BPY00614");
						else if(airtimeValidateResponse.getPayHdr().getResCde().equals("BMB90012")
								||airtimeValidateResponse.getPayHdr().getResCde().equals("BPY00604")
								||airtimeValidateResponse.getPayHdr().getResCde().equals("ATPR0625")){
							USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
							Map<String, Object> userInputMap = ussdSessionMgmt.getTxSessions();

							String amount=userInputMap.get("txnAmt").toString();

							String mininimumAmtSndr = getSystemPreferenceData(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile(), SystemPreferenceConstants.SYS_PARAM_MWALLET,
									SystemPreferenceConstants.MWALLET_MIN_AMT_SNDR);

							String maximumAmtSndr = getSystemPreferenceData(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile(), SystemPreferenceConstants.SYS_PARAM_MWALLET,
									SystemPreferenceConstants.MWALLET_MAX_AMT_SNDR);

							List<String> errorParams=new ArrayList<String>();
							errorParams.add(amount);
							errorParams.add(mininimumAmtSndr);
							errorParams.add(maximumAmtSndr);

							menuDTO=renderMenuOnScreen(responseBuilderParamsDTO,mininimumAmtSndr,maximumAmtSndr);
							USSDNonBlockingException nbk=new USSDNonBlockingException("BMB90012",menuDTO.getPageBody());
							nbk.setErrorParams(errorParams);
							throw nbk;
						}
					}else{
						LOGGER.fatal("unable to service: " + airtimeValidateResponse.getPayHdr().getResMsg());
						throw new USSDNonBlockingException(airtimeValidateResponse.getPayHdr().getResCde());
					}
				}else if (airtimeValidateResponse.getPayHdr() != null
						&& TRANSACTION_AMT_LIMIT_ERROR.equalsIgnoreCase(airtimeValidateResponse.getPayHdr().getResCde())) {
					throw new USSDNonBlockingException(USSDExceptions.USSD_TOP_UP_TRAN_AMT_LIMIT_EXCEEDED.getBmgCode());
				} else {
					LOGGER.error("Error while servicing: " + airtimeValidateResponse.getPayHdr().getResMsg());
					throw new USSDNonBlockingException(airtimeValidateResponse.getPayHdr().getResCde());
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
				USSDNonBlockingException nbk=(USSDNonBlockingException) e;
				throw nbk;
			}if (e instanceof USSDBlockingException) {
				throw new USSDBlockingException(((USSDBlockingException) e).getErrCode(),((USSDBlockingException) e).getErrMsg());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		return menuDTO;
	}

	/**
	 * @param responseBuilderParamsDTO
	 * @param airtimeValidatePayData
	 * @param warningMsg
	 * @return MenuItemDTO
	 */
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, String minLimit, String maxLimit){
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, Object> userInputMap = ussdSessionMgmt.getTxSessions();

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		String paramArray[]= new String[]{userInputMap.get("txnAmt").toString(),minLimit, maxLimit};
		String amountErrorLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AIRTIME_ERROR_LABEL, paramArray,
				new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
						responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
		menuItemDTO.setPageBody(amountErrorLabel);
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
		return menuItemDTO;
	}
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, AirtimeValidatePayData airtimeValidatePayData) {
		MenuItemDTO menuItemDTO = null;
		if (airtimeValidatePayData != null) {
			USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

			Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			Map<String, Object> userInputFreedialMap= ussdSessionMgmt.getTxSessions();
			menuItemDTO = new MenuItemDTO();
			StringBuilder pageBody = new StringBuilder();
			UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
			String paramArray[]=new String[1];
			if(userInputFreedialMap!=null && userInputFreedialMap.get("extra")!=null && (userInputFreedialMap.get("extra").toString().equals("FREEDIALAIRTEL") || userInputFreedialMap.get("extra").toString().equals("FREEDIALAIRTELZM")))
				paramArray[0]=userInputFreedialMap.get("txnAmt").toString();
			else
				paramArray[0]=userInputMap.get("txnAmt");
			String airtimeTopupAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AIRTIME_LABEL, paramArray,
					new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
							responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));

			String language = ussdSessionMgmt.getUserProfile().getLanguage();
			String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
			Locale locale = new Locale(language, countryCode);
			String confirmLabel = ussdResourceBundle.getLabel(USSDConstants.LABEL_AIRTIME_CONFIRM, locale);
			String fromAccLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DEBIACCNUM_LABEL, locale);
			String mobileNumLabel = ussdResourceBundle.getLabel(USSDConstants.USSD_TRANSACTION_MWALLETE_MOBILE, locale);
			String airtimeServiceLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.transaction.service", locale);
			Account account = airtimeValidatePayData.getSrcAcct();
			pageBody.append(airtimeTopupAmountLabel);
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(mobileNumLabel);
			String mbNum = null;
			if(userInputFreedialMap!=null && userInputFreedialMap.get("extra")!=null && (userInputFreedialMap.get("extra").toString().equals("FREEDIALAIRTEL") || userInputFreedialMap.get("extra").toString().equals("FREEDIALAIRTELZM")))
				mbNum = userInputFreedialMap.get(USSDInputParamsEnum.FREE_DIAL_MOB_NUM.getParamName()).toString();
			else
				mbNum = userInputMap.get(USSDInputParamsEnum.FREE_DIAL_MOB_NUM.getParamName());

//			if(userInputMap == null) {
//				userInputMap = new HashMap<String,String>();
//				userInputMap.put("BillerName", airtimeValidatePayData.getPrvder().getBillerName());
//				userInputMap.put("mblNo", mbNum);
				//if(userInputMap.get("bizId").toString().equals("GHBRB"))
//				if(userInputMap.get(USSDConstants.BMG_BUSINESS_ID_PARAM_NAME).equalsIgnoreCase("GHBRB"))
//				{
//					userInputMap.put("extra", "FREEDIALAIRTEL");
//				} //else if(userInputMap.get("bizId").toString().equals("ZMBRB"))
//				else if(userInputMap.get(USSDConstants.BMG_BUSINESS_ID_PARAM_NAME).equalsIgnoreCase("ZMBRB"))
//				{
//					userInputMap.put("extra", "FREEDIALAIRTELZM");
//				}
//				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
//			} else {
//				userInputMap.put("BillerName", airtimeValidatePayData.getPrvder().getBillerName());
//			}

			if(userInputMap!=null)
				userInputMap.put("BillerName", airtimeValidatePayData.getPrvder().getBillerName());
			else{
				userInputMap = new HashMap<String,String>();
				userInputMap.put("BillerName", airtimeValidatePayData.getPrvder().getBillerName());
				userInputMap.put("mblNo", mbNum);
				userInputMap.put("extra", "FREEDIALAIRTEL");
				ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
				}

			pageBody.append(mbNum);
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(airtimeServiceLabel);
			pageBody.append(airtimeValidatePayData.getPrvder().getBillerName());

			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(fromAccLabel);
			pageBody.append(account.getMkdActNo());

			if (!responseBuilderParamsDTO.isErrorneousPage()) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(confirmLabel);
			}
			USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			menuItemDTO.setPageBody(pageBody.toString());
			menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
			menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			setNextScreenSequenceNumber(menuItemDTO);
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
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
}
