/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

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
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.FromAcntLst;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class AirtimeSelectAccountResponseParser implements BmgBaseJsonParser {
	private static final String TRANSACTION_AIRTIME_LABEL = "label.airtime.select.accnum";
	private static final String TRANSACTION_DATABUNDLE_LABEL="label.databundle.select.accnum";
	String businessId; 
	String transNodeId;
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
    	businessId= ussdSessionMgmt.getBusinessId();
    	transNodeId =ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
    	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();

	//CR82
	/*Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	String mAtWt=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
	FromAcntLst payee=(FromAcntLst)txSessions.get(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId());
	if(mAtWt.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
		accounts=payee.getFrActLst();
	}*/
	int accountIndex = 1;
	StringBuilder pageBody = new StringBuilder();
	Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	String currency= ((AuthUserData)(responseBuilderParamsDTO.getUssdSessionMgmt().getUserAuthObj())).getPayData().getCustActs().get(0).getCurr();
	String paramArray[]={userInputMap.get("txnAmt"),currency};
	String airtimeTopupAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_AIRTIME_LABEL, paramArray,
			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));

	//Ghana Data Bundle Change
	if(businessId.equalsIgnoreCase("GHBRB") && transNodeId.equals("ussd0.10")) {
		BillDetails billDetails = new BillDetails();
		int dataBundleInput =Integer.parseInt(userInputMap.get(USSDInputParamsEnum.DATA_BUNDLE_SEL_BUNDLE_LIST.getParamName()));
		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		if(null != ussdSessionMgmt && null != ussdSessionMgmt.getTxSessions()){
			billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get("DataBundleDetails");
		}
		LinkedHashMap<String, String> bundleHashMap = billDetails.getBillInvoiceDetails().getProbaseDetails();
		List<String> dataBundleList = new ArrayList<String>(bundleHashMap.values());
		String amount = dataBundleList.get(dataBundleInput-1);
		 
		airtimeTopupAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSACTION_DATABUNDLE_LABEL, new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
		airtimeTopupAmountLabel = airtimeTopupAmountLabel + " " + amount + " from ";
	}
	
	pageBody.append(airtimeTopupAmountLabel);
	 USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	 AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
	 List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	//CR82
	String mAtWt=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
	if(!mAtWt.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){

		List<Account> accounts = (List<Account>) txSessions.get(USSDInputParamsEnum.AIRTIME_ACCT_LIST.getTranId());
		List<String> GpAcc=new ArrayList<String>();
		 for(int i =0;i<acts.size();i++)
		    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
		    		GpAcc.add(acts.get(i).getMkdActNo());
		    List<Account> srcAcc=accounts;

			 for(int j=0;j<srcAcc.size();j++)
				 if(GpAcc.contains(srcAcc.get(j).getMkdActNo()))
					 srcAcc.remove(j);
		if (CollectionUtils.isNotEmpty(accounts) && accounts.size() > 0 && accounts != null) {
			pageBody.append(USSDConstants.NEW_LINE);
			for (Account account : accounts) {
				pageBody.append(accountIndex++);
				pageBody.append(USSDConstants.DOT_SEPERATOR).append(account.getMkdActNo());
				pageBody.append(USSDConstants.NEW_LINE);
			}
		}
		else
		{
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
	} else {
		FromAcntLst fromAcntLst = (FromAcntLst) txSessions.get("AccountListSaved");
		List<String> GpAcc=new ArrayList<String>();
		 for(int i =0;i<acts.size();i++)
		    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
		    		GpAcc.add(acts.get(i).getMkdActNo());
		    List<AccountData> srcAcc=fromAcntLst.getFrActLst();

			 for(int j=0;j<srcAcc.size();j++)
				 if(GpAcc.contains(srcAcc.get(j).getMkdActNo()))
					 srcAcc.remove(j);
		if (srcAcc != null && srcAcc.size() > 0 && !srcAcc.isEmpty()) {
			pageBody.append(USSDConstants.NEW_LINE);
			for (AccountData acctDet : srcAcc) {
				pageBody.append(accountIndex++);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(acctDet.getMkdActNo());
				pageBody.append(USSDConstants.NEW_LINE);

				menuItemDTO.setPageBody(pageBody.toString());
			}
		}
		else
		{
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}

	}


	responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.NEW_LINE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }

}