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
import com.barclays.bmg.json.model.fundtransfer.OwnFundTransferValidateJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.FundTransferValidateOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class FundTransferValidateJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

	if (responseContext instanceof FundTransferValidateOperationResponse) {
	    FundTransferValidateOperationResponse response = (FundTransferValidateOperationResponse) responseContext;
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
	} else if (response != null) {
	    header.setResCde(response.getResCde());
	    header.setResMsg(response.getResMsg());
	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	header.setResId(ResponseIdConstants.OWN_FUND_TRANSFER_VALIDATE_RESPONSE_ID);

	return header;
    }

    protected void populatePayLoad(FundTransferValidateOperationResponse response, BMBPayload bmbPayload) {

	OwnFundTransferValidateJSONResponseModel responseModel = null;

	if (response != null && response.isSuccess()) {
	    responseModel = new OwnFundTransferValidateJSONResponseModel();
	    AmountJSONModel txnAmt = new AmountJSONModel();

	    txnAmt.setAmt(BMGFormatUtility.getFormattedAmount(new BigDecimal(response.getTxnAmount())));
	    txnAmt.setCurr(response.getToAcct().getCurrency());
	    responseModel.setTxnAmt(txnAmt);
	    responseModel.setTxnDt(response.getTxnDate());
	    CustomerAccountDTO frmAcct = response.getFrmAcct();
	    CustomerAccountDTO toAcct = response.getToAcct();
	    if (branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())) {
		responseModel.setFrMskActNo(getMaskedAccountNumber(frmAcct.getBranchCode(), frmAcct.getAccountNumber()));
		responseModel.setToMskActNo(getMaskedAccountNumber(toAcct.getBranchCode(), toAcct.getAccountNumber()));
	    } else {
		responseModel.setFrMskActNo(getMaskedAccountNumber(null, frmAcct.getAccountNumber()));
		responseModel.setToMskActNo(getMaskedAccountNumber(null, toAcct.getAccountNumber()));
	    }

	    responseModel.setTxnNot(response.getTxnNote());

	    // Fixed Rate and equivalen amont pending
	    FxRateDTO fxRateDTO = response.getFxRateDTO();
	    if (fxRateDTO != null && fxRateDTO.getEffectiveFXRate() != null) {
		responseModel.setFxRt(fxRateDTO.getEffectiveFXRate().toString());
	    }
	    if (fxRateDTO != null && fxRateDTO.getEquivalentAmount() != null) {
		responseModel.setEqAmt(BMGFormatUtility
			.getJSONAmount(response.getFrmAcct().getCurrency(), fxRateDTO.getEquivalentAmount().toString()));
	    }
	    responseModel.setTxnRefNo(response.getTxnRef());
	    responseModel.setTotFee(response.getTxnFee());

	    responseModel.setCurr(response.getCurreny());
	    responseModel.setToActCurr(toAcct.getCurrency());
	    responseModel.setFrActCurr(frmAcct.getCurrency());
	    AmountJSONModel txnLimit = null;
	    if (response.getAValidDailyLimit() != null) {
		txnLimit = new AmountJSONModel();
		txnLimit.setAmt(BMGFormatUtility.getFormattedAmount(response.getAValidDailyLimit()));
		txnLimit.setCurr(response.getContext().getLocalCurrency());
	    }

	    responseModel.setTxnLmt(txnLimit);

	}

	bmbPayload.setPayData(responseModel);
    }

}