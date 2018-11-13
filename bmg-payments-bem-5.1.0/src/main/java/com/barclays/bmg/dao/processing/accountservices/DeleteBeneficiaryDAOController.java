package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.DeleteBeneficiaryReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.DeleteBeneficiaryResAdptOperation;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryDAOController implements Controller {

	private DeleteBeneficiaryReqAdptOperation deleteBeneficiaryReqAdptOperation;
	private DeleteBeneficiaryResAdptOperation deleteBeneficiaryResAdptOperation;
	private TransmissionOperation transmissionOperation;


	/**
	 * @param deleteBeneficiaryReqAdptOperation
	 */
	public void setDeleteBeneficiaryReqAdptOperation(
			DeleteBeneficiaryReqAdptOperation deleteBeneficiaryReqAdptOperation) {
		this.deleteBeneficiaryReqAdptOperation = deleteBeneficiaryReqAdptOperation;
	}


	/**
	 * @param deleteBeneficiaryResAdptOperation
	 */
	public void setDeleteBeneficiaryResAdptOperation(
			DeleteBeneficiaryResAdptOperation deleteBeneficiaryResAdptOperation) {
		this.deleteBeneficiaryResAdptOperation = deleteBeneficiaryResAdptOperation;
	}


	/**
	 * @param transmissionOperation
	 */
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}


	/* (non-Javadoc)
	 * @see com.barclays.bmg.dao.core.processing.Controller#handleRequest(com.barclays.bmg.dao.core.context.WorkContext)
	 */
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		Object obj = deleteBeneficiaryReqAdptOperation.adaptRequestforDeleteBeneficiary(workContext);
		Object obj1= transmissionOperation.sendAndReceive(obj);
		Object obj2 = deleteBeneficiaryResAdptOperation.adaptResponse(workContext, obj1);
		return obj2;
	}


}
