package com.barclays.ussd.bmg.statementrequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.AccountChequBookDetails;

public class ValidateStatementReqRequestBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	List<AccountChequBookDetails> lstPayee = (List<AccountChequBookDetails>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.STMT_REQ_SRC_ACT.getTranId());
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	userInputMap.put(USSDInputParamsEnum.STMT_REQ_SRC_ACT.getParamName(), lstPayee.get(
		Integer.parseInt(userInputMap.get(USSDInputParamsEnum.STMT_REQ_SRC_ACT.getParamName())) - 1).getActNo());

	int fromDays = -30;
	int toDays = -1;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	Calendar fromDt = Calendar.getInstance();
	Calendar toDt = Calendar.getInstance();
	fromDt.add(Calendar.DAY_OF_YEAR, fromDays);
	toDt.add(Calendar.DAY_OF_YEAR, toDays);
	String toDate = StringUtils.EMPTY + sdf.format(toDt.getTime());
	String fromDate = StringUtils.EMPTY + sdf.format(fromDt.getTime());

	// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	// GregorianCalendar gCal = new GregorianCalendar();
	// if (gCal.get(Calendar.MONTH) == gCal.getActualMinimum(Calendar.MONTH)) {
	// gCal.roll(Calendar.YEAR, false);
	// }
	// if (gCal.get(Calendar.DATE) == gCal.getActualMinimum(Calendar.DATE)) {
	// gCal.roll(Calendar.MONTH, false);
	// }
	// gCal.roll(Calendar.DATE, false);
	// String toDate = StringUtils.EMPTY + sdf.format(gCal.getTime());
	// gCal.roll(Calendar.MONTH, false);
	// String fromDate = StringUtils.EMPTY + sdf.format(gCal.getTime());

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDInputParamsEnum.STMT_REQ_FROM_DATE.getParamName(), fromDate);
	requestParamMap.put(USSDInputParamsEnum.STMT_REQ_TO_DATE.getParamName(), toDate);

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode()); // Cheque boook
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.STMT_REQ_SRC_ACT.getParamName(), userInputMap
		.get(USSDInputParamsEnum.STMT_REQ_SRC_ACT.getParamName()));
	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
