package com.barclays.bmg.json.model.builder.statement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.StatementValidationJsonModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.statement.StatmentValidateOperationResponse;

public class StatementRequestValidationBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.STATMENT_REQUEST_EXECUTE);

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {
		StatementValidationJsonModel responseModel = new StatementValidationJsonModel();
		CasaAccountJSONModel srcAct = new CasaAccountJSONModel();
		StatmentValidateOperationResponse statmentValidateOperationResponse = (StatmentValidateOperationResponse) responses[0];

		//casaAccountJSONModel.setActNo(statmentValidateOperationResponse.getActNo().getAccountNumber());
		//casaAccountJSONModel.setBrnNam(statmentValidateOperationResponse.getBranchLookUpDTO().getBankName());
		srcAct.setBrnCde(statmentValidateOperationResponse.getActNo().getBranchCode());
		srcAct.setMkdActNo(getMaskedAccountNumber(statmentValidateOperationResponse.getActNo().getBranchCode(), statmentValidateOperationResponse.getActNo().getAccountNumber()));
		responseModel.setSrcAct(srcAct);
		//responseModel.setActNo(statmentValidateOperationResponse.getActNo().getAccountNumber());
		//responseModel.setResDtTm(statmentValidateOperationResponse.get)
		//responseModel.setBrnCde(statmentValidateOperationResponse.getBrnCde());
		responseModel.setFromDate(statmentValidateOperationResponse.getFromDate());
		responseModel.setToDate(statmentValidateOperationResponse.getToDate());
		responseModel.setTxnRefNo(statmentValidateOperationResponse.getTxnRefNo());

		bmbPayload.setPayData(responseModel);

	}

	private  String stringToDate(String strDate){

		long dateLong = convertCalToLong(strDate);

		Calendar calendar = new GregorianCalendar();

		calendar.setLenient(false);

		calendar.setTimeInMillis(dateLong);

		Date date  = calendar.getTime();

		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		//return dateFormat.format(date);
		return getShortDate(date);
	}

	public  final String getShortDate(Date date) {
		if (date == null) {

			return null;
		}

		SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yyyy");

		return smf.format(date);

	}

	private Date convertInToDate(String  dateString){

		long longDate = convertCalToLong(dateString);

		Date dtDate = new Date(longDate);

		return dtDate;
	}

	private long convertCalToLong(String calendar){

		/*Calendar cal = new GregorianCalendar();

		cal.setTime(new Date(calendar));*/

		Long l = Long.parseLong(calendar);

		return l;
	}

}
