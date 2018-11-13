package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrieveIndCustBySCVIDReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrieveIndCustBySCVIDResAdptOperation;

public class RetrieveIndCustBySCVIDDAOController implements Controller {

    private RetrieveIndCustBySCVIDReqAdptOperation retrieveIndCustBySCVIDReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private RetrieveIndCustBySCVIDResAdptOperation retrieveIndCustBySCVIDResAdptOperation;

    public void setRetrieveIndCustBySCVIDReqAdptOperation(RetrieveIndCustBySCVIDReqAdptOperation retrieveIndCustBySCVIDReqAdptOperation) {
	this.retrieveIndCustBySCVIDReqAdptOperation = retrieveIndCustBySCVIDReqAdptOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public void setRetrieveIndCustBySCVIDResAdptOperation(RetrieveIndCustBySCVIDResAdptOperation retrieveIndCustBySCVIDResAdptOperation) {
	this.retrieveIndCustBySCVIDResAdptOperation = retrieveIndCustBySCVIDResAdptOperation;
    }

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	// TODO Auto-generated method stub
	Object obj = retrieveIndCustBySCVIDReqAdptOperation.adaptRequest(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	return retrieveIndCustBySCVIDResAdptOperation.adaptResponse(workContext, obj1);
    }

}
