package com.barclays.bmg.json.model.builder.statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.billpayment.StatementValidationJsonModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.statement.StatmentExecuteOperationResponse;

public class StatementRequestExecuteBldr extends
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

		StatmentExecuteOperationResponse statmentExecuteOperationResponse = (StatmentExecuteOperationResponse) responses[0];

		responseModel.setTxnRefNo(statmentExecuteOperationResponse.getRefNo());

		//Date date = DateTimeUtil.getCurrentBusinessCalendar(responses[0].getContext()).getTime();

		String refDate = getDateFromStr("dd/MM/yyyy");

		responseModel.setRefDate(refDate);

		bmbPayload.setPayData(responseModel);

	}
	public  String getDateFromStr(String stringType) {

		Date date = getTodayDate();

		DateFormat formatter = new SimpleDateFormat(stringType);

		formatter.setLenient(false);

		String strDate  = formatter.format(date);

		return strDate;

	}
	private Date getTodayDate(){

		Calendar cal =  new GregorianCalendar();

		 cal.setLenient(false);

		 return cal.getTime();
	}

	/* public static Date getDateFromStr(final Date dateStr, final String stringType) {
	        Date date = null;
	        try {
	            DateFormat formatter;
	            formatter = new SimpleDateFormat(stringType);
	            date = (Date)formatter.parse(dateStr.toString());

	        } catch (ParseException e) {
//	            throw new CCSSystemException(SV_DATE_FORMAT_EXCEPTION,
//	                    "Date format parse exception!", e);
	        	
	        }

	        return date;

	    }
	private long convertCalToLong(String calendar){

		Calendar cal = new GregorianCalendar();

		cal.setTime(new Date(calendar));

		Long l = Long.parseLong(calendar);

		return l;
	}*/

}
