package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.StatmentExecuteResAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.StatmentReqAdptOperation;

public class StatmentExecuteDAOController implements Controller{

	private StatmentReqAdptOperation statmentReqAdptOperation;

	private TransmissionOperation transmissionOperation;

	private StatmentExecuteResAdptOperation statmentExecuteResAdptOperation;

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = statmentReqAdptOperation.adaptRequest(workContext);

		Object obj1 = transmissionOperation.sendAndReceive(obj);

		Object obj2 = statmentExecuteResAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}

	/**
	 * @return the statmentReqAdptOperation
	 */
	public StatmentReqAdptOperation getStatmentReqAdptOperation() {
		return statmentReqAdptOperation;
	}

	/**
	 * @param statmentReqAdptOperation the statmentReqAdptOperation to set
	 */
	public void setStatmentReqAdptOperation(
			StatmentReqAdptOperation statmentReqAdptOperation) {
		this.statmentReqAdptOperation = statmentReqAdptOperation;
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
	 * @return the statmentExecuteResAdptOperation
	 */
	public StatmentExecuteResAdptOperation getStatmentExecuteResAdptOperation() {
		return statmentExecuteResAdptOperation;
	}

	/**
	 * @param statmentExecuteResAdptOperation the statmentExecuteResAdptOperation to set
	 */
	public void setStatmentExecuteResAdptOperation(
			StatmentExecuteResAdptOperation statmentExecuteResAdptOperation) {
		this.statmentExecuteResAdptOperation = statmentExecuteResAdptOperation;
	}





}
