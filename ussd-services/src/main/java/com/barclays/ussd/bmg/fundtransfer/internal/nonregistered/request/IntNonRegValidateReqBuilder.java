package com.barclays.ussd.bmg.fundtransfer.internal.nonregistered.request;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class IntNonRegValidateReqBuilder implements BmgBaseRequestBuilder {
    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDSessionManagement ussdSessionMgmt = null;
	Map<String, String> requestParamMap = new HashMap<String, String>();
	USSDBaseRequest request = new USSDBaseRequest();
	ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	String curr = null;

	if(ussdSessionMgmt.getTxSessions().containsKey("CREDIT_CARD_TRAN")){

	List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>)ussdSessionMgmt.getTxSessions()
				.get(USSDInputParamsEnum.INT_NR_FT_CREDIT_LIST.getTranId());
	String userCreditSelection = userInputMap.get(USSDInputParamsEnum.INT_NR_FT_CREDIT_LIST.getParamName());
	CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userCreditSelection) - 1);
	requestParamMap.put("frActNo", creditCard.getActNo());
    //Added card no details to fetch only selected card
	requestParamMap.put("ccNumber", creditCard.getCrdNo());
	requestParamMap.put("CREDIT_CARD_TRAN", "CREDIT_CARD_TRAN");
	curr = creditCard.getCurr();
	} else {

	List frmActList = (List) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.INT_NR_FT_SOURCE_LIST.getTranId());
	AccountDetails selectedFrmAcct = (AccountDetails) frmActList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.INT_NR_FT_SOURCE_LIST
		.getParamName())) - 1);
	requestParamMap.put("frActNo", selectedFrmAcct.getActNo());
	curr = selectedFrmAcct.getCurr();
	}

	String userBranchSelection = userInputMap.get(USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getParamName());
	if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
	    List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getTranId());
	    UssdBranchLookUpDTO branchCodeLookUpDTO = branchList.get(Integer.parseInt(userBranchSelection) - 1);

	    requestParamMap.put("beneficiaryBranchCode", branchCodeLookUpDTO.getBranchCode());
	}

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

	String txtAmt = userInputMap.get(USSDInputParamsEnum.INT_NR_FT_AMOUNT.getParamName());

	// TODO: Remove the below check once BMG approves of this service.
	if (curr == null) {
	    // requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase("TZBRB"))
	    curr = "TZS";
	}
	// String benBranchCode = (String) userInputMap.get(USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getParamName());

	String benAcctNo = null;
	if(null != userInputMap.get(USSDInputParamsEnum.INT_NR_FT_TO_AC.getParamName()))
		benAcctNo= userInputMap.get(USSDInputParamsEnum.INT_NR_FT_TO_AC.getParamName());
	else
	{
		benAcctNo = userInputMap.get(USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName());
		ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName(), benAcctNo);
	}
	
	String benName = userInputMap.get(USSDInputParamsEnum.INT_NR_FT_NICK_NAME.getParamName());

	Calendar cal = Calendar.getInstance();
	Date dt = new Date(cal.getTimeInMillis());
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	String dateAsString = sdf.format(dt);

	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_FT_BARCLAYS, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	requestParamMap.put("txnDt", dateAsString);
	requestParamMap.put("curr", curr);

	requestParamMap.put("payDesc", transactionRemarks);
	requestParamMap.put("txnAmt", txtAmt);
	requestParamMap.put("beneficiaryAccountNumber", benAcctNo);
	requestParamMap.put("beneficiaryName", benName);

	requestParamMap.put("opCde", requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put("serVer", "2.0");
	
	if(benAcctNo.length() == 21) {
		String payReason[]={"Business","Loan Payment","Others","Rentals","Transfer","School Fees"};
		requestParamMap.put(USSDConstants.EXT_BNK_TXNNOT_PARAM, payReason[Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KE_EXT_BANK_FT_RSO_PAYMENT.getParamName()))-1]);
		transactionRemarks = payReason[Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KE_EXT_BANK_FT_RSO_PAYMENT.getParamName()))-1];
		requestParamMap.put("payDesc", transactionRemarks);
	}
	
	request.setRequestParamMap(requestParamMap);
	return request;
    }
}