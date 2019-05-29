package com.barclays.bmg.ussd.dao.processing;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.ussd.dao.operation.RegisterCustomerToProductReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RegisterCustomerToProductResAdptOperation;

public class RegisterCustomerToProductDAOController implements Controller {


	private RegisterCustomerToProductReqAdptOperation registerCustomerToProductReqAdptOperation;

	private RegisterCustomerToProductResAdptOperation registerCustomerToProductResAdptOperation;

	private TransmissionOperation transmissionOperation;

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = registerCustomerToProductReqAdptOperation
				.adaptRequestForRegisterCustomerToProduct(workContext);

		Object obj1 = transmissionOperation.sendAndReceive(obj);

		Object obj2 = registerCustomerToProductResAdptOperation
				.adaptResponseForRegisterCustomer(workContext, obj1);

		return obj2;

	}

	public RegisterCustomerToProductReqAdptOperation getRegisterCustomerToProductReqAdptOperation() {
		return registerCustomerToProductReqAdptOperation;
	}

	public void setRegisterCustomerToProductReqAdptOperation(
			RegisterCustomerToProductReqAdptOperation registerCustomerToProductReqAdptOperation) {
		this.registerCustomerToProductReqAdptOperation = registerCustomerToProductReqAdptOperation;
	}

	public RegisterCustomerToProductResAdptOperation getRegisterCustomerToProductResAdptOperation() {
		return registerCustomerToProductResAdptOperation;
	}

	public void setRegisterCustomerToProductResAdptOperation(
			RegisterCustomerToProductResAdptOperation registerCustomerToProductResAdptOperation) {
		this.registerCustomerToProductResAdptOperation = registerCustomerToProductResAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}

	public void setTransmissionOperation(
			TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

}
