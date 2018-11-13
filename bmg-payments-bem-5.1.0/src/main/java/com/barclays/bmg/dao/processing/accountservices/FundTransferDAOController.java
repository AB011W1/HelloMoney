package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.FundTransferReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.FundTransferRespAdptOperation;
import com.barclays.bmg.service.response.FundTransferExecuteServiceResponse;

public class FundTransferDAOController implements Controller {

	private FundTransferReqAdptOperation fundTransferReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private FundTransferRespAdptOperation fundTransferRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = fundTransferReqAdptOperation.adaptRequestForFundTransfer(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = fundTransferRespAdptOperation.adaptResponse(workContext, obj1);

		FundTransferExecuteServiceResponse fundTransferExecuteServiceResponse =
									(FundTransferExecuteServiceResponse) obj2;
		if(fundTransferExecuteServiceResponse.isSuccess()){

			MakeDomesticFundTransferRequest fundTransferRequest =
									(MakeDomesticFundTransferRequest) obj;
			BEMReqHeader reqHeader = fundTransferRequest.getRequestHeader();

			fundTransferExecuteServiceResponse
				.setTrnRef(reqHeader.getServiceContext().getConsumerUniqueRefNo());
			fundTransferExecuteServiceResponse
				.setTrnDate(reqHeader.getServiceContext().getServiceDateTime().getTime());
		}
		return fundTransferExecuteServiceResponse;
	}
	public FundTransferReqAdptOperation getFundTransferReqAdptOperation() {
		return fundTransferReqAdptOperation;
	}
	public void setFundTransferReqAdptOperation(
			FundTransferReqAdptOperation fundTransferReqAdptOperation) {
		this.fundTransferReqAdptOperation = fundTransferReqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public FundTransferRespAdptOperation getFundTransferRespAdptOperation() {
		return fundTransferRespAdptOperation;
	}
	public void setFundTransferRespAdptOperation(
			FundTransferRespAdptOperation fundTransferRespAdptOperation) {
		this.fundTransferRespAdptOperation = fundTransferRespAdptOperation;
	}

}
