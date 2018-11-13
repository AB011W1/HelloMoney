package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.AddBeneficiaryReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.AddBeneficiaryResAdptOperation;

public class AddBeneficiaryDAOController implements Controller {

	private AddBeneficiaryReqAdptOperation addBeneficiaryReqAdptOperation;
	private AddBeneficiaryResAdptOperation addBeneficiaryResAdptOperation;
	private TransmissionOperation transmissionOperation;

	public void setAddBeneficiaryReqAdptOperation(
			AddBeneficiaryReqAdptOperation addBeneficiaryReqAdptOperation) {
		this.addBeneficiaryReqAdptOperation = addBeneficiaryReqAdptOperation;
	}


	public void setAddBeneficiaryResAdptOperation(
			AddBeneficiaryResAdptOperation addBeneficiaryResAdptOperation) {
		this.addBeneficiaryResAdptOperation = addBeneficiaryResAdptOperation;
	}


	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}


	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		// TODO Auto-generated method stub
		Object obj = addBeneficiaryReqAdptOperation.adaptRequestforAddBeneficiary(workContext);
		Object obj1= transmissionOperation.sendAndReceive(obj);
		Object obj2 = addBeneficiaryResAdptOperation.adaptResponse(workContext, obj1);
		return obj2;
	}


	/**
	 * @return the addBeneficiaryReqAdptOperation
	 */
	public AddBeneficiaryReqAdptOperation getAddBeneficiaryReqAdptOperation() {
		return addBeneficiaryReqAdptOperation;
	}


	/**
	 * @return the addBeneficiaryResAdptOperation
	 */
	public AddBeneficiaryResAdptOperation getAddBeneficiaryResAdptOperation() {
		return addBeneficiaryResAdptOperation;
	}


	/**
	 * @return the transmissionOperation
	 */
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
}
