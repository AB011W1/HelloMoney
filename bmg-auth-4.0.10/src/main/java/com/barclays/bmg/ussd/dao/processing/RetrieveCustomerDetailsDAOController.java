package com.barclays.bmg.ussd.dao.processing;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveCustomerDetailsReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveCustomerDetailsResAdptOperation;

public class RetrieveCustomerDetailsDAOController implements Controller {
    /**
     * The instance variable for selfRegistrationExecutionReqAdptOperation of type SelfRegistrationExecutionReqAdptOperation
     */
    private RetrieveCustomerDetailsReqAdptOperation retrieveCustomerDetailsReqAdptOperation;
    /**
     * The instance variable for selfRegistrationExecutionResAdptOperation of type SelfRegistrationExecutionResAdptOperation
     */
    private RetrieveCustomerDetailsResAdptOperation retrieveCustomerDetailsResAdptOperation;
    private TransmissionOperation transmissionOperation;

    /**
     * Overrides handleRequest method of Controller
     * 
     * @param WorkContext
     * @return Object
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = retrieveCustomerDetailsReqAdptOperation.adaptRequestForDetails(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = retrieveCustomerDetailsResAdptOperation.adaptResponseForDetails(workContext, obj1);

	return obj2;
    }

    public RetrieveCustomerDetailsReqAdptOperation getRetrieveCustomerDetailsReqAdptOperation() {
	return retrieveCustomerDetailsReqAdptOperation;
    }

    public void setRetrieveCustomerDetailsReqAdptOperation(RetrieveCustomerDetailsReqAdptOperation retrieveCustomerDetailsReqAdptOperation) {
	this.retrieveCustomerDetailsReqAdptOperation = retrieveCustomerDetailsReqAdptOperation;
    }

    public RetrieveCustomerDetailsResAdptOperation getRetrieveCustomerDetailsResAdptOperation() {
	return retrieveCustomerDetailsResAdptOperation;
    }

    public void setRetrieveCustomerDetailsResAdptOperation(RetrieveCustomerDetailsResAdptOperation retrieveCustomerDetailsResAdptOperation) {
	this.retrieveCustomerDetailsResAdptOperation = retrieveCustomerDetailsResAdptOperation;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
