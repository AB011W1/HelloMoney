package com.barclays.ussd.utils.jsonparsers;

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
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.FromAcntLst;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
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
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();//CR82 changes
	MenuItemDTO menuItemDTO = null;
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
		if (fromAcntLst != null && fromAcntLst.getFrActLst() != null && !fromAcntLst.getFrActLst().isEmpty()) {
			int accountIndex = 1;
			for (AccountData acctDet : fromAcntLst.getFrActLst()) {
				menuItemDTO = new MenuItemDTO();
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(accountIndex);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(acctDet.getMkdActNo());
				accountIndex++;
			}
		}
	} else {
		List<SrcAccount> srcAccounts = (List<SrcAccount>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.MOBILE_WALLET_FROM_ACCOUNT.getTranId());
		if (srcAccounts != null && !srcAccounts.isEmpty()) {
			menuItemDTO = new MenuItemDTO();
			int index = 1;
			pageBody.append(mWallettAmountLabel);
			for (SrcAccount srcAccount : srcAccounts) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(index++);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(srcAccount.getMkdActNo());
			}
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
