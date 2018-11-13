package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.PostAuthenticationReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.PostAuthenticationResAdptOperation;

public class PostAuthenticationDAOController implements Controller {

    private PostAuthenticationReqAdptOperation postAuthenticationReqAdptOperation;

    private TransmissionOperation transmissionOperation;

    private PostAuthenticationResAdptOperation postAuthenticationResAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = postAuthenticationReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = postAuthenticationResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public PostAuthenticationReqAdptOperation getPostAuthenticationReqAdptOperation() {
	return postAuthenticationReqAdptOperation;
    }

    public void setPostAuthenticationReqAdptOperation(PostAuthenticationReqAdptOperation postAuthenticationReqAdptOperation) {
	this.postAuthenticationReqAdptOperation = postAuthenticationReqAdptOperation;
    }

    public PostAuthenticationResAdptOperation getPostAuthenticationResAdptOperation() {
	return postAuthenticationResAdptOperation;
    }

    public void setPostAuthenticationResAdptOperation(PostAuthenticationResAdptOperation postAuthenticationResAdptOperation) {
	this.postAuthenticationResAdptOperation = postAuthenticationResAdptOperation;
    }

}
