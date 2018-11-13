package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.UpdateLanguagePrefReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.UpdateLanguagePrefResAdptOperation;

public class UpdateLanguagePrefDAOController implements Controller {

    private UpdateLanguagePrefReqAdptOperation updateLanguagePrefReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private UpdateLanguagePrefResAdptOperation updateLanguagePrefResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	Object obj = updateLanguagePrefReqAdptOperation.adaptRequest(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	Object obj2 = updateLanguagePrefResAdptOperation.adaptResponse(workContext, obj1);
	return obj2;
    }

    public void setUpdateLanguagePrefReqAdptOperation(UpdateLanguagePrefReqAdptOperation updateLanguagePrefReqAdptOperation) {
	this.updateLanguagePrefReqAdptOperation = updateLanguagePrefReqAdptOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public void setUpdateLanguagePrefResAdptOperation(UpdateLanguagePrefResAdptOperation updateLanguagePrefResAdptOperation) {
	this.updateLanguagePrefResAdptOperation = updateLanguagePrefResAdptOperation;
    }

}
