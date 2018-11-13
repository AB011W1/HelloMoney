package com.barclays.bmg.operation.fxrate;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxBoardRatesDTO;
import com.barclays.bmg.fxrate.operation.request.FxRateOperationRequest;
import com.barclays.bmg.fxrate.operation.response.FxRateOperationResponse;
import com.barclays.bmg.fxrate.service.request.FXBoardRatesServiceRequest;
import com.barclays.bmg.fxrate.service.response.FXBoardRatesServiceResponse;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.service.FXBoardRatesService;

public class FxRateOperation extends BMBCommonOperation {

    private FXBoardRatesService fxBoardRatesService;



    public FXBoardRatesService getFxBoardRatesService() {
		return fxBoardRatesService;
	}



	public void setFxBoardRatesService(FXBoardRatesService fxBoardRatesService) {
		this.fxBoardRatesService = fxBoardRatesService;
	}



	/**
     * @param request
     * @param sourceAcct
     * @param response
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_FX_RATE", stepId = "1", activityType = "auditFxRate")
    public FxRateOperationResponse retrieveFxRate(FxRateOperationRequest fxRateOperationRequest) {

	FXBoardRatesServiceRequest fxRequest = new FXBoardRatesServiceRequest();

	Context context = fxRateOperationRequest.getContext();
	fxRequest.setContext(context);

	loadParameters(context, context.getActivityId(), ActivityConstant.COMMON_ID);

	FxRateOperationResponse fxRateOperationResponse = new FxRateOperationResponse();
	FxBoardRatesDTO fxBoardRatesDTO = null;

	CustomerAccountDTO toAcct = new CustomerAccountDTO();
	toAcct.setCurrency(fxRateOperationRequest.getDestCurrency());

	CustomerAccountDTO fromAcct = new CustomerAccountDTO();
	fromAcct.setAccountNumber(fxRateOperationRequest.getActNo());
	fromAcct.setCurrency(fxRateOperationRequest.getSrcCurrency());
	fromAcct.setBranchCode(fxRateOperationRequest.getBranchCode());
	// fromAcct.setProductCode(sourceAcct.getProductCode());

	fxRequest.setContext(fxRateOperationRequest.getContext());
	fxRequest.setFrmCustActDTO(fromAcct);
	fxRequest.setToCustActDTO(toAcct);
	fxRequest.setTxnAmt(fxRateOperationRequest.getTxnAmt());
	fxRequest.setTxnType(fxRateOperationRequest.getTxnType());

	FXBoardRatesServiceResponse fxBoardRatesResponse = fxBoardRatesService.retreiveFxRate(fxRequest);

	if (fxBoardRatesResponse != null) {
		fxBoardRatesDTO = new FxBoardRatesDTO();
		if(fxBoardRatesResponse.getBuyRate()!=null){
		fxBoardRatesDTO.setBuyRate(fxBoardRatesResponse.getBuyRate());
		}if(fxBoardRatesResponse.getSellRate()!=null){
		fxBoardRatesDTO.setSellRate(fxBoardRatesResponse.getSellRate());
		}
		fxRateOperationResponse.setResCde(fxBoardRatesResponse.getResCde());
		fxRateOperationResponse.setResMsg(fxBoardRatesResponse.getResMsg());
		fxRateOperationResponse.setSuccess(fxBoardRatesResponse.isSuccess());
	}

	fxRateOperationResponse.setFxBoardRateDTO(fxBoardRatesDTO);

	return fxRateOperationResponse;

    }
}
