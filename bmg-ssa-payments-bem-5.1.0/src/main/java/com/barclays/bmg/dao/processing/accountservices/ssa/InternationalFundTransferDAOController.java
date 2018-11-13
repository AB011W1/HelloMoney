package com.barclays.bmg.dao.processing.accountservices.ssa;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.MakeInternationalFundTransfer.MakeInternationalFundTransferRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ssa.InternationalFundTransferReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ssa.InternationalFundTransferRespAdptOperation;
import com.barclays.bmg.service.response.InternationalFundTransferServiceResponse;

public class InternationalFundTransferDAOController implements Controller {
	private InternationalFundTransferReqAdptOperation internationalFundTransferReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private InternationalFundTransferRespAdptOperation internationalFundTransferRespAdptOperation;

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		Object obj = internationalFundTransferReqAdptOperation.adaptRequestForInternationalFundTransfer(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = internationalFundTransferRespAdptOperation.adaptResponse(workContext, obj1);

		InternationalFundTransferServiceResponse serviceResponse =
									(InternationalFundTransferServiceResponse) obj2;
		if(serviceResponse.isSuccess()){

			MakeInternationalFundTransferRequest fundTransferRequest =
									(MakeInternationalFundTransferRequest) obj;
			BEMReqHeader reqHeader = fundTransferRequest.getRequestHeader();

			serviceResponse
				.setTrnRef(reqHeader.getServiceContext().getConsumerUniqueRefNo());
			serviceResponse
				.setTrnDate(reqHeader.getServiceContext().getServiceDateTime().getTime());
		}
		return serviceResponse;
	}

	public InternationalFundTransferReqAdptOperation getInternationalFundTransferReqAdptOperation() {
		return internationalFundTransferReqAdptOperation;
	}

	public void setInternationalFundTransferReqAdptOperation(
			InternationalFundTransferReqAdptOperation internationalFundTransferReqAdptOperation) {
		this.internationalFundTransferReqAdptOperation = internationalFundTransferReqAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}

	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

	public InternationalFundTransferRespAdptOperation getInternationalFundTransferRespAdptOperation() {
		return internationalFundTransferRespAdptOperation;
	}

	public void setInternationalFundTransferRespAdptOperation(
			InternationalFundTransferRespAdptOperation internationalFundTransferRespAdptOperation) {
		this.internationalFundTransferRespAdptOperation = internationalFundTransferRespAdptOperation;
	}

}
