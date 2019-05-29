package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.FromAcntLst;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.SrcAccount;

public class MobileWalletTopupFromAccountJsonParser implements BmgBaseJsonParser {
	private static final String FUNDTRANS_FROM_LABEL = "label.fundtrans.from";
    @SuppressWarnings("unchecked")
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param responseBuilderParamsDTO
     * @param otbpFrmAcntLstData
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();//CR82 changes

    AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
   /* for(CustomerMobileRegAcct act:acts)
    	if(act.getGroupWalletIndicator()!=null && act.getGroupWalletIndicator().equals("Y"))
    		gpACList.add(act.getMkdActNo());*/


   /* for(int i =0;i<acts.size();i++)
    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
    		acts.remove(i);
*/
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	//for Kadikope
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	if(txSessions.containsKey(USSDConstants.CREDIT_MOBILE_WALLET)){
		txSessions.remove(USSDConstants.CREDIT_MOBILE_WALLET);
	}

	Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	String currency= ((AuthUserData)(responseBuilderParamsDTO.getUssdSessionMgmt().getUserAuthObj())).getPayData().getCustActs().get(0).getCurr();
	String paramArray[]={userInputMap.get("txnAmt"),currency};
	String mWallettAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(FUNDTRANS_FROM_LABEL, paramArray,
			new Locale(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage(),
					responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode()));
	//CR82
	String mAtWtSavedBenf=userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF)== null?"":(String) userInputMap.get(BillPaymentConstants.AT_MW_SAVED_BENEF);
	StringBuilder pageBody = new StringBuilder();
	if(mAtWtSavedBenf.equals(BillPaymentConstants.AT_MW_SAVED_BENEF)){
		pageBody.append(mWallettAmountLabel);
		FromAcntLst fromAcntLst = (FromAcntLst) ussdSessionMgmt.getTxSessions().get("AccountListSaved");
		List<String> GpAcc=new ArrayList<String>();
		 for(int i =0;i<acts.size();i++)
		    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
		    		GpAcc.add(acts.get(i).getMkdActNo());
		    List<AccountData> srcAcc=fromAcntLst.getFrActLst();
		    if(null != srcAcc){
		    	for(int j=0;j<srcAcc.size();j++)
					 if(GpAcc.contains(srcAcc.get(j).getMkdActNo()))
						 srcAcc.remove(j);
		    }


		if (srcAcc != null && srcAcc.size() > 0 && !srcAcc.isEmpty()) {
			int accountIndex = 1;
			for (AccountData acctDet : srcAcc) {
				menuItemDTO = new MenuItemDTO();
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(accountIndex);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(acctDet.getMkdActNo());
				accountIndex++;
			}
		}
		else
		{
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}
	} else {
		List<SrcAccount> srcAccounts = (List<SrcAccount>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getTranId());
		List<String> GpAcc=new ArrayList<String>();
		 for(int i =0;i<acts.size();i++)
		    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
		    		GpAcc.add(acts.get(i).getMkdActNo());
		 if(null != srcAccounts){
			 for(int j=0;j<srcAccounts.size();j++)
				 if(GpAcc.contains(srcAccounts.get(j).getMkdActNo()))
					 srcAccounts.remove(j);
		 }

		if (srcAccounts != null && !srcAccounts.isEmpty()) {
			menuItemDTO = new MenuItemDTO();
			int index = 1;
			pageBody.append(mWallettAmountLabel);
			for (SrcAccount srcAccount : srcAccounts) {
				//if(!gpACList.contains(srcAccount.getMkdActNo())){
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(index++);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(srcAccount.getMkdActNo());
				//}
			}
		}
		else
		{
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
		}

	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);

	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
    }
}