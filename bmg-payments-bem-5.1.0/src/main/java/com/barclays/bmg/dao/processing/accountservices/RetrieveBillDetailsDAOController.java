package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrieveBillDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrieveBillDetailsRespAdptOperation;

public class RetrieveBillDetailsDAOController implements Controller{

	private RetrieveBillDetailsReqAdptOperation retrieveBillDetailsReqAdptOperation;
	private TransmissionOperation transmissionOperation;
	private RetrieveBillDetailsRespAdptOperation retrieveBillDetailsRespAdptOperation;

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = retrieveBillDetailsReqAdptOperation.adaptRequestForRetrieveBillDetails(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = retrieveBillDetailsRespAdptOperation.adaptResponse(workContext, obj1);
		return obj2;
	}

	/**
	 * @return the retrieveBillDetailsReqAdptOperation
	 */
	public RetrieveBillDetailsReqAdptOperation getRetrieveBillDetailsReqAdptOperation() {
		return retrieveBillDetailsReqAdptOperation;
	}
	/**
	 * @param retrieveBillDetailsReqAdptOperation the retrieveBillDetailsReqAdptOperation to set
	 */
	public void setRetrieveBillDetailsReqAdptOperation(
			RetrieveBillDetailsReqAdptOperation retrieveBillDetailsReqAdptOperation) {
		this.retrieveBillDetailsReqAdptOperation = retrieveBillDetailsReqAdptOperation;
	}
	/**
	 * @return the transmissionOperation
	 */
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	/**
	 * @param transmissionOperation the transmissionOperation to set
	 */
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	/**
	 * @return the retrieveBillDetailsRespAdptOperation
	 */
	public RetrieveBillDetailsRespAdptOperation getRetrieveBillDetailsRespAdptOperation() {
		return retrieveBillDetailsRespAdptOperation;
	}
	/**
	 * @param retrieveBillDetailsRespAdptOperation the retrieveBillDetailsRespAdptOperation to set
	 */
	public void setRetrieveBillDetailsRespAdptOperation(
			RetrieveBillDetailsRespAdptOperation retrieveBillDetailsRespAdptOperation) {
		this.retrieveBillDetailsRespAdptOperation = retrieveBillDetailsRespAdptOperation;
	}

}
