package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.CASAAccountTnxHistoryActivityReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.CASAAccountTrnxHistoryRespAdptOperation;

public class CASAAccountTransactionHistoryDAOController implements Controller {

    private TransmissionOperation transmissionOperation;

    private CASAAccountTrnxHistoryRespAdptOperation accountTrnxHistoryRespAdptOperation;

    private CASAAccountTnxHistoryActivityReqAdptOperation accountTnxHistoryActivityReqAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = accountTnxHistoryActivityReqAdptOperation.adaptRequestForCASAAccountTrnxHistoryActivity(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = accountTrnxHistoryRespAdptOperation.adaptResponseForCASAAccountActivity(workContext, obj1);

	return obj2;
    }

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    public CASAAccountTrnxHistoryRespAdptOperation getAccountTrnxHistoryRespAdptOperation() {
	return accountTrnxHistoryRespAdptOperation;
    }

    public void setAccountTrnxHistoryRespAdptOperation(CASAAccountTrnxHistoryRespAdptOperation accountTrnxHistoryRespAdptOperation) {
	this.accountTrnxHistoryRespAdptOperation = accountTrnxHistoryRespAdptOperation;
    }

    public CASAAccountTnxHistoryActivityReqAdptOperation getAccountTnxHistoryActivityReqAdptOperation() {
	return accountTnxHistoryActivityReqAdptOperation;
    }

    public void setAccountTnxHistoryActivityReqAdptOperation(CASAAccountTnxHistoryActivityReqAdptOperation accountTnxHistoryActivityReqAdptOperation) {
	this.accountTnxHistoryActivityReqAdptOperation = accountTnxHistoryActivityReqAdptOperation;
    }

}
