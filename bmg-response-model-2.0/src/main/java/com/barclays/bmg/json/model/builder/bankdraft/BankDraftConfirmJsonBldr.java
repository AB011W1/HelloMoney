package com.barclays.bmg.json.model.builder.bankdraft;

import java.util.Map;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.fundtransfer.BankDraftPayInfoJSONResponseModel;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;

public class BankDraftConfirmJsonBldr extends
		BankDraftRetrievePayeeInfoJSONBldr {

	@Override
	protected TransactionLimitOperationResponse getTransactionLimitOperationResponse(
			ResponseContext... responses) {
		return null;
	}

	@Override
	protected void postProcess(BankDraftPayInfoJSONResponseModel responseModel,
			ResponseContext... responses) {

		ResponseContext responseContext = responses[0];

		Context context = responseContext.getContext();

		Map<String, Object> contextMap = responseContext.getContext()
				.getContextMap();

		responseModel.setAmount((AmountJSONModel) contextMap.get("txnAmount"));
		responseModel.setDelTypSel(getLabel(context, (String) contextMap
				.get("delTypSel"), "FT_DELIVERY_TYPE"));
		responseModel.setSelPayAt(getLabel(context, (String) contextMap
				.get("payAt"), "CMN_COUNTRY"));
		responseModel.setSelBrnNam((String) contextMap.get("brnNam"));
		responseModel.setSelBrnCde((String) contextMap.get("brnCde"));
		responseModel.setFxRate((String) contextMap.get("fxRate"));
		responseModel
				.setEqAmt((AmountJSONModel) contextMap.get("eqAmtJSONAmt"));

		//responseModel.setTxnNot(convertStringToUnicode((String)contextMap.get("payRemarks"))); //Added convertStringToUnicode() to convert to unicode
		responseModel.setTxnNot((String)contextMap.get("payRemarks"));
		responseModel.setCntrLst(null);
		responseModel.setCurrLst(null);
		//responseModel.setTxnMsg(responseContext);
	}

}
