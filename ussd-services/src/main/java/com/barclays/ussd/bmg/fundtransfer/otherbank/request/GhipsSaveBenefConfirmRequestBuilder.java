package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class GhipsSaveBenefConfirmRequestBuilder implements
		BmgBaseRequestBuilder {

    private static final String MM_DD_YYYY = "MM/dd/yyyy";
    private static final String REM3 = "rem3";
    private static final String REM2 = "rem2";
    private static final String REM1 = "rem1";
    private static final String BANK_SWIFT_CODE="bankSwiftCode";
    private static final String PAY_RSON = "rsonOfPayment";
    private static final String USSD = "USSD";

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		AccountDetails acntDet = (AccountDetails) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_AMT.getParamName());

		String txtAmt = userInputMap.get(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_ENTER_AMT.getParamName());
		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		String curr = acntDet.getCurr();

		requestParamMap.put(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_CONFIRM.getParamName(), txtAmt);
		requestParamMap.put(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_PAYEE_INFO.getParamName(), curr);

	/*	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_FT_OTHER_BANK, new Locale(ussdSessionMgmt
			.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));*/


		requestParamMap.put(USSDConstants.EXT_BNK_CHDESC_PARAM, USSD);
		requestParamMap.put(USSDConstants.EXT_BNK_PAYDTLS_PARAM, USSD);

		String payReason[]={"Personal","School fees","Remittance","Payment","Salary","Allowance","Others"};
		requestParamMap.put(USSDInputParamsEnum.EXT_BANK_FT_TRAN_REMARKS.getParamName(), payReason[Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_PAYMENT_REASON_LIST.getParamName()))-1]);
		//requestParamMap.put(PAY_RSON, payReason[Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_PAYMENT_REASON_LIST.getParamName()))-1]);

		requestParamMap.put(USSDConstants.EXT_BNK_REM1_PARAM, REM1);
		requestParamMap.put(USSDConstants.EXT_BNK_REM2_PARAM, REM2);
		requestParamMap.put(USSDConstants.EXT_BNK_REM3_PARAM, REM3);

		String swiftCode=null;
		requestParamMap.put(BANK_SWIFT_CODE,swiftCode);
		Calendar cal = Calendar.getInstance();
		Date dt = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_YYYY);
		String dateAsString = sdf.format(dt);
		requestParamMap.put(USSDConstants.EXT_BNK_TXN_DT, dateAsString);
		requestParamMap.put("txnType", FundTransferConstants.TXN_TYPE_FUND_TRANSFER_GHIPPS);

		request.setRequestParamMap(requestParamMap);
		return request;
    }

}
