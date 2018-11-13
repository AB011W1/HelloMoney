package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.constants.BillPaymentConstants;
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
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletTxConfirm;

public class MobileWalletTopupConfirmJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(MobileWalletTopupConfirmJsonParser.class);
    @Resource(name = "inprogressErrorCodeList")
    private List<String> inprogressErrorCodeList;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	try {

	    MobileWalletTxConfirm mobileWalletTxConfirm = mapper.readValue(jsonString, MobileWalletTxConfirm.class);
	    //mobileWalletTxConfirm.getPayHdr().setResCde("00000");//mocked for testing
	    String displayMessage = null;
	    String txnRefNo = null;
	    String saveABenefeciaryLabel = null;//CR82
	    if (mobileWalletTxConfirm != null) {
		if ((mobileWalletTxConfirm.getPayHdr() != null)
			&& (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(mobileWalletTxConfirm.getPayHdr().getResCde()))) {

		    if (mobileWalletTxConfirm.getPayData() != null)
			txnRefNo = mobileWalletTxConfirm.getPayData().getTxnRefNo();
		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_TRANSACTION_MWALLETE_SUCCESS);

		    //CR82
		    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		    String mWalletWon=userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER)== null?"":(String) userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER);
		    String mAtWtSaveBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
		    if(!(mWalletWon.equals(BillPaymentConstants.MWALLET_WON_NUMBER)|| mAtWtSaveBenf.equalsIgnoreCase(BillPaymentConstants.AT_MW_SAVED_BENEF)))
		    saveABenefeciaryLabel = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_OTBP_SAVE_BENEFECIARY);//CR82

		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage, saveABenefeciaryLabel);//CR82
		} else if (isInProgressErrorCode(mobileWalletTxConfirm.getPayHdr().getResCde())) {
		    txnRefNo = mobileWalletTxConfirm.getPayHdr().getTxnRefNo();
		    displayMessage = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_TRANSACTION_INPROGRESS);
		    //CR82
		    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		    String mWalletWon=userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER)== null?"":(String) userInputMap.get(BillPaymentConstants.MWALLET_WON_NUMBER);
		    String mAtWtSaveBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
		    if(!(mWalletWon.equals(BillPaymentConstants.MWALLET_WON_NUMBER)|| mAtWtSaveBenf.equalsIgnoreCase(BillPaymentConstants.AT_MW_SAVED_BENEF)))
		    saveABenefeciaryLabel = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_OTBP_SAVE_BENEFECIARY);//CR82
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, txnRefNo, displayMessage, saveABenefeciaryLabel);//CR82

		} else if (mobileWalletTxConfirm.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(mobileWalletTxConfirm.getPayHdr().getResCde());
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

    private boolean isInProgressErrorCode(String resCode) {
	boolean result = false;
	String bemResCode = null;
	if (resCode != null) {
	    bemResCode = resCode;
	    if (bemResCode.startsWith("BEM")) {
		bemResCode = bemResCode.substring(3);
	    }
	    return inprogressErrorCodeList.contains(bemResCode);
	}
	return result;
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, String txnRefNo, String displayMessage, String saveABenefeciaryLabel) {
    Map<String,String> userInputMap=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	String txIdLabel = getLabel(responseBuilderParamsDTO, USSDConstants.TRAN_ID_LABEL_ID);
	String amtLabel=getLabel(responseBuilderParamsDTO, USSDConstants.USSD_TRANSACTION_MWALLETE_AMOUNT);
	String mblLabel=getLabel(responseBuilderParamsDTO, USSDConstants.USSD_TRANSACTION_MWALLETE_MOBILE);
	String srvcLabel=getLabel(responseBuilderParamsDTO, USSDConstants.USSD_TRANSACTION_MWALLETE_SERVICE);
	pageBody.append(displayMessage);

	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(txIdLabel);
	pageBody.append(txnRefNo);

	/*pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(amtLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("txnAmt"));*/
	pageBody.append(USSDConstants.NEW_LINE);

	pageBody.append(mblLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("mblNo"));
	pageBody.append(USSDConstants.NEW_LINE);

	pageBody.append(srvcLabel);
	pageBody.append(USSDConstants.SPACE);
	pageBody.append(userInputMap.get("BillerName"));
	pageBody.append(USSDConstants.NEW_LINE);
	//CR82 Start
	if(null != saveABenefeciaryLabel){
		if(!(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equals("GHBRB") && responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID))){
			pageBody.append(saveABenefeciaryLabel);
		}
	}
	//End


	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setPageBody(pageBody.toString());
	//MNO initiated MWallet
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	if(ussdSessionMgmt.getBusinessId().equals("GHBRB") && ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId().equals(USSDConstants.GH_FREE_DIAL_USSD_TRAN_ID)){
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("FALSE");
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setBackOptionReq("FALSE");
	}else{
		ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
	//responseBuilderParamsDTO.getUssdSessionMgmt().clean();//CR*82
	return menuItemDTO;
    }

    private String getLabel(ResponseBuilderParamsDTO responseBuilderParamsDTO, String label) {
	String labelValue = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	labelValue = ussdResourceBundle.getLabel(label, locale);
	return labelValue;
    }

    private String getTxnRefNo(String refNumber) {
	String refno;
	int cnt = refNumber.indexOf("-");
	refno = refNumber.substring(0, cnt);
	return refno;

    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());
    }

}