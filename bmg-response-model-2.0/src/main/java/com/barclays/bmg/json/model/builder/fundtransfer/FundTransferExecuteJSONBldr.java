package com.barclays.bmg.json.model.builder.fundtransfer;

import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.OwnFundTransferExecutedJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.FundTransferExecuteOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;



public class FundTransferExecuteJSONBldr implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof FundTransferExecuteOperationResponse) {
			FundTransferExecuteOperationResponse response = (FundTransferExecuteOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayLoad(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(FundTransferExecuteOperationResponse response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if(response != null && response.isSuccess()){
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
		}else if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
			header.setResId(getResponseId(response.getTxnType()));
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;
	}

	protected void populatePayLoad(
			FundTransferExecuteOperationResponse response,
			BMBPayload bmbPayload) {
		OwnFundTransferExecutedJSONResponseModel responseModel = null;
		if(response!=null && response.isSuccess()){
			responseModel = new OwnFundTransferExecutedJSONResponseModel();
			responseModel.setRefNo(response.getTrnRef());
			responseModel.setTxnDt(BMGFormatUtility.getLongDate(response.getTrnDate()));
//
		}
		bmbPayload.setPayData(responseModel);
	}

	/**
	 * @param txnType
	 * @return
	 */
	private String getResponseId(String txnType){
		String respId = ResponseIdConstants.OWN_FUND_TRANSFER_EXECUTE_RESPONSE_ID;
		if(FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER.equals(txnType)){
			respId = ResponseIdConstants.OWN_FUND_TRANSFER_EXECUTE_RESPONSE_ID;
		}else if(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL.equals(txnType)){
			respId = ResponseIdConstants.INTERNAL_FUND_TRANSFER_EXECUTE_RESPONSE_ID;
		}

		return respId;
	}
}
