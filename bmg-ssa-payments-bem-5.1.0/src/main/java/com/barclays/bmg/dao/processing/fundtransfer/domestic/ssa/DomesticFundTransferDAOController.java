package com.barclays.bmg.dao.processing.fundtransfer.domestic.ssa;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.fundtransfer.domestic.ssa.DomesticFundTransferReqAdptOperation;
import com.barclays.bmg.dao.operation.fundtransfer.domestic.ssa.DomesticFundTransferRespAdptOperation;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;

public class DomesticFundTransferDAOController implements Controller {

	private DomesticFundTransferReqAdptOperation domesticFundTransferReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private DomesticFundTransferRespAdptOperation domesticFundTransferRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = domesticFundTransferReqAdptOperation.adaptRequestForFundTransfer(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = domesticFundTransferRespAdptOperation.adaptResponse(workContext, obj1);

		DomesticFundTransferServiceResponse domesticFundTransferServiceResponse =
									(DomesticFundTransferServiceResponse) obj2;
		if(domesticFundTransferServiceResponse.isSuccess()){

			MakeDomesticFundTransferRequest fundTransferRequest =
									(MakeDomesticFundTransferRequest) obj;
			BEMReqHeader reqHeader = fundTransferRequest.getRequestHeader();

			domesticFundTransferServiceResponse
				.setTrnRef(reqHeader.getServiceContext().getConsumerUniqueRefNo());
			domesticFundTransferServiceResponse
				.setTrnDate(reqHeader.getServiceContext().getServiceDateTime().getTime());
		}
		return domesticFundTransferServiceResponse;
	}
	public DomesticFundTransferReqAdptOperation getDomesticFundTransferReqAdptOperation() {
		return domesticFundTransferReqAdptOperation;
	}
	public void setDomesticFundTransferReqAdptOperation(
			DomesticFundTransferReqAdptOperation domesticFundTransferReqAdptOperation) {
		this.domesticFundTransferReqAdptOperation = domesticFundTransferReqAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public DomesticFundTransferRespAdptOperation getDomesticFundTransferRespAdptOperation() {
		return domesticFundTransferRespAdptOperation;
	}
	public void setDomesticFundTransferRespAdptOperation(
			DomesticFundTransferRespAdptOperation domesticFundTransferRespAdptOperation) {
		this.domesticFundTransferRespAdptOperation = domesticFundTransferRespAdptOperation;
	}

}
