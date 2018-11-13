package com.barclays.bmg.dao.processing.pesalink;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.pesalink.CreateIndividualCustomerReqAdptOperation;
import com.barclays.bmg.dao.operation.pesalink.CreateIndividualCustomerResAdptOperation;
import com.barclays.bmg.dao.operation.pesalink.SearchIndividualCustforDeDupCheckReqAdptOperation;
import com.barclays.bmg.dao.operation.pesalink.SearchIndividualCustforDeDupCheckResAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveindividualCustCardListReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveindividualCustCardListResAdptOperation;



public class CreateIndividualCustomerDAOController  implements Controller{


    private CreateIndividualCustomerReqAdptOperation createIndividualCustomerReqAdptOperation;

    private CreateIndividualCustomerResAdptOperation createIndividualCustomerResAdptOperation;

    private TransmissionOperation transmissionOperation;


    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = createIndividualCustomerReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = createIndividualCustomerResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

	public CreateIndividualCustomerReqAdptOperation getCreateIndividualCustomerReqAdptOperation() {
		return createIndividualCustomerReqAdptOperation;
	}

	public void setCreateIndividualCustomerReqAdptOperation(
			CreateIndividualCustomerReqAdptOperation createIndividualCustomerReqAdptOperation) {
		this.createIndividualCustomerReqAdptOperation = createIndividualCustomerReqAdptOperation;
	}

	public CreateIndividualCustomerResAdptOperation getCreateIndividualCustomerResAdptOperation() {
		return createIndividualCustomerResAdptOperation;
	}

	public void setCreateIndividualCustomerResAdptOperation(
			CreateIndividualCustomerResAdptOperation createIndividualCustomerResAdptOperation) {
		this.createIndividualCustomerResAdptOperation = createIndividualCustomerResAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}

	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}


}
