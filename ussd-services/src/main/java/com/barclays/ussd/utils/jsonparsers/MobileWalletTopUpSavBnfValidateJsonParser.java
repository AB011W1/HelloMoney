package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.regbiller.ValidateRegBillerBean;

public class MobileWalletTopUpSavBnfValidateJsonParser implements BmgBaseJsonParser {


    private static final String MOBILEWALLET_TOPUP_SAV_BNF_CONFIRM_MSG = "airtimetopup.sav.bnf.confirm.msg";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final Logger LOGGER = Logger.getLogger(MobileWalletTopUpSavBnfValidateJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	 menuDTO = renderMenuOnScreen( responseBuilderParamsDTO);

	return menuDTO;
    }

    /**
     * @param validateRegBillerPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
    	MenuItemDTO menuItemDTO = new MenuItemDTO();
    	ValidateRegBillerBean regBillerValidateObj;
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		regBillerValidateObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ValidateRegBillerBean.class);
    	USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
    	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
    	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
    	Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
    	String mobilenumber = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName());
    	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
    	List<MobileWalletProvider> mobileWalletProvider = (List<MobileWalletProvider>) txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST
    			.getTranId());
    	String selectedMobileWalletProvider = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_NEW_BENE_SRC_MON.getParamName());
    	if(selectedMobileWalletProvider==null){
    		selectedMobileWalletProvider=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName());
		}
    	MobileWalletProvider mobileWalletProviderSelected = mobileWalletProvider.get(Integer.parseInt(selectedMobileWalletProvider) - 1);
    	String mnoSelected =mobileWalletProviderSelected.getBillerName();
    	String nickName = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_SAVE_BEN_NICK_NAME.getParamName()).trim();
    	List<String> params = new ArrayList<String>(1);
    	params.add(mobilenumber);
    	params.add(mnoSelected);
    	params.add(nickName);
    	String[] paramArray = params.toArray(new String[params.size()]);
    	String successMessage =  responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MOBILEWALLET_TOPUP_SAV_BNF_CONFIRM_MSG, paramArray,
    			new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
    	StringBuilder pageBody = new StringBuilder();
    	pageBody.append(successMessage);
    	pageBody.append(USSDConstants.NEW_LINE);
    	pageBody.append(ussdResourceBundle.getLabel(CONFIRM_LABEL, locale));



    	menuItemDTO.setPageBody(pageBody.toString());
    	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
    	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
    	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
    	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
    	setNextScreenSequenceNumber(menuItemDTO);
    	List<String> txnRefNo = new ArrayList<String>(1);
    	txnRefNo.add(regBillerValidateObj.getPayData().getOrgRefNo());
    	// set the payee accnt list to the session
    	responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.MOBILE_WALLET_SAVE_BEN_VALIDATE.getTranId(), txnRefNo);
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
    	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWELVE.getSequenceNo());

    }


}
