package com.barclays.bmg.json.model.builder.fundtransfer;

import java.math.BigDecimal;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.InternalFTValidateJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.InternalFundTransferValidateOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class InternalFTValidateJSONBldr extends BMBCommonJSONBuilder implements
		BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof InternalFundTransferValidateOperationResponse) {
			InternalFundTransferValidateOperationResponse response = (InternalFundTransferValidateOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader(response));

			populatePayLoad(response, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header
				.setResId(ResponseIdConstants.INTERNAL_TRANSFER_VALIDATE_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			InternalFundTransferValidateOperationResponse response,
			BMBPayload bmbPayload) {

		InternalFTValidateJSONResponseModel responseModel = null;
		if (response != null && response.isSuccess()) {

			responseModel = new InternalFTValidateJSONResponseModel();
			//String currency = response.getCurreny();
			AmountJSONModel txnAmt = new AmountJSONModel();
			txnAmt.setAmt(BMGFormatUtility.getFormattedAmount(new BigDecimal(
					response.getTxnAmount())));
			txnAmt.setCurr(response.getContext().getLocalCurrency());
			responseModel.setTxnAmt(txnAmt);

			responseModel.setTxnDt(response.getTxnDate());
			CustomerAccountDTO frmAcct = response.getFrmAcct();
			CustomerAccountDTO toAcct = response.getToAcct();
			// CHECK Account Number masked
			// MAsked Acccount Number is pending
			/*responseModel.setFrMskActNo(getMaskedAccountNumber(frmAcct
					.getBranchCode(), frmAcct.getAccountNumber()));

			responseModel.setToMskActNo(getMaskedAccountNumber(toAcct
					.getBranchCode(), toAcct.getAccountNumber()));*/


			 if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
			responseModel.setFrMskActNo(getMaskedAccountNumber(frmAcct
					.getBranchCode(), frmAcct.getAccountNumber()));

			responseModel.setToMskActNo(getMaskedAccountNumber(toAcct
					.getBranchCode(), toAcct.getAccountNumber()));
			    }
			    else {
				responseModel.setFrMskActNo(getMaskedAccountNumber(null, frmAcct.getAccountNumber()));

			responseModel.setToMskActNo(getMaskedAccountNumber(null, toAcct.getAccountNumber()));
			    }





			/*
			 * responseModel .setFrActNo(frmAcct.getAccountNumber());
			 * responseModel .setToActNo(toAcct.getAccountNumber());
			 */
			responseModel.setTxnNot(response.getTxnNote());
			FxRateDTO fxRateDTO = response.getFxRateDTO();
			// Fixed Rate and equivalen amont pending
			if (fxRateDTO != null && fxRateDTO.getEffectiveFXRate() != null
					&& fxRateDTO.getEquivalentAmount() != null) {
				responseModel
						.setFxRt(fxRateDTO.getEffectiveFXRate().toString());

				responseModel.setEqAmt(BMGFormatUtility.getJSONAmount(response
						.getFrmAcct().getCurrency(),
						fxRateDTO.getEquivalentAmount().toString()));
			}
			AmountJSONModel txnLimit = new AmountJSONModel();
			if (response.getAValidDailyLimit() != null) {
				txnLimit.setAmt(BMGFormatUtility.getFormattedAmount(response
						.getAValidDailyLimit()));
				txnLimit.setCurr(response.getContext().getLocalCurrency());
			}
			responseModel.setTxnLmt(txnLimit);
			responseModel.setTxnRefNo(response.getTxnRef());
			responseModel.setTotFee(response.getTxnFee());

			responseModel.setCurr(response.getCurreny());

			responseModel.setToActCurr(toAcct.getCurrency());
			responseModel.setFrActCurr(frmAcct.getCurrency());
			responseModel.setPayId(response.getPayId());
			responseModel.setPayName(response.getPayNickName());
			responseModel.setBeneName(response.getBeneName());
		}

		bmbPayload.setPayData(responseModel);
	}
}
