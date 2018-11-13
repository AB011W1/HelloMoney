package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDApplySourceAccount;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.SelectedFDProduct;

public class FDApplyConfirmParser implements BmgBaseJsonParser {

    private static final String CONFIRM_LABEL = "label.confirm";
    private static final String ACCNUM_LABEL = "label.accountnumber.caps";
    private static final String AMOUNT_LABEL = "label.amount.caps";
    private static final String TENURE_LABEL = "label.tenure.caps";

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    @SuppressWarnings("unchecked")
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	List<SelectedFDProduct> selectedFdProductList = (List<SelectedFDProduct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.FD_APPLY_SEL_PROD.getTranId());

	String selectedProductStr = userInputMap.get(USSDInputParamsEnum.FD_APPLY_SEL_PROD.getParamName());
	String enteredAmount = userInputMap.get(USSDInputParamsEnum.FD_APPLY_ENTER_AMT.getTranId());
	String enteredActIndex = userInputMap.get(USSDInputParamsEnum.FD_APPLY_SEL_AC.getTranId());
	Integer selectedAccountIndex = Integer.parseInt(enteredActIndex) - 1;
	SelectedFDProduct selectedFDProduct = selectedFdProductList.get(Integer.parseInt(selectedProductStr) - 1);
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	List<FDApplySourceAccount> accountDetails = (List<FDApplySourceAccount>) txSessions.get(USSDInputParamsEnum.FD_APPLY_SEL_AC.getTranId());
	FDApplySourceAccount accountDetail = accountDetails.get(selectedAccountIndex);
	StringBuilder pageBody = new StringBuilder();
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	String language = ussdSessionMgmt.getUserProfile().getLanguage();
	String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	Locale locale = new Locale(language, countryCode);
	String confirmLabel = ussdResourceBundle.getLabel(CONFIRM_LABEL, locale);
	String fromAccLabel = ussdResourceBundle.getLabel(ACCNUM_LABEL, locale);
	String amountLabel = ussdResourceBundle.getLabel(AMOUNT_LABEL, locale);
	String tenureLabel = ussdResourceBundle.getLabel(TENURE_LABEL, locale);
	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(fromAccLabel).append(USSDConstants.SINGLE_WHITE_SPACE);
	pageBody.append(accountDetail.getMkdActNo());

	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(tenureLabel).append(USSDConstants.SINGLE_WHITE_SPACE);
	pageBody.append(selectedFDProduct.getSelectedTenure());

	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(amountLabel).append(USSDConstants.SINGLE_WHITE_SPACE);
	pageBody.append(enteredAmount);

	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append("Prod Desc:").append(USSDConstants.SINGLE_WHITE_SPACE);
	pageBody.append(selectedFDProduct.getSelProductDesc());

	if (!responseBuilderParamsDTO.isErrorneousPage()) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(confirmLabel);
	}

	HashMap<String, String> confirmationRequestMap = new HashMap<String, String>();
	confirmationRequestMap.put(USSDConstants.PAY_GRP, USSDConstants.BILL_PAY);
	confirmationRequestMap.put(USSDConstants.ACT_NO, accountDetail.getActNo());
	confirmationRequestMap.put(USSDConstants.DEPOSIT_AMT, enteredAmount);
	confirmationRequestMap.put(USSDConstants.TENURE_MON, selectedFDProduct.getTenMon());
	confirmationRequestMap.put(USSDConstants.TENURE_DAY, selectedFDProduct.getTenDays());
	confirmationRequestMap.put(USSDConstants.PRODUCT_ID, selectedFDProduct.getSelProductID());
	txSessions.put(USSDConstants.FD_APPLY_CONFIRMATION, confirmationRequestMap);
	menuItemDTO.setPageBody(pageBody.toString());

	// insert the back and home options
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

    }

}
