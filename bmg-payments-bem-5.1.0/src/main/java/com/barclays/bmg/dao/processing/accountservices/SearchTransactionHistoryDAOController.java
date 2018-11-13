package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.SearchTransactionHistoryReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.SearchTransactionHistoryResAdptOperation;

public class SearchTransactionHistoryDAOController implements Controller {

    private SearchTransactionHistoryReqAdptOperation searchTransactionHistoryReqAdptOperation;
    private SearchTransactionHistoryResAdptOperation searchTransactionHistoryResAdptOperation;
    private TransmissionOperation transmissionOperation;

    public void setSearchTransactionHistoryReqAdptOperation(SearchTransactionHistoryReqAdptOperation searchTransactionHistoryReqAdptOperation) {
	this.searchTransactionHistoryReqAdptOperation = searchTransactionHistoryReqAdptOperation;
    }

    public void setSearchTransactionHistoryResAdptOperation(SearchTransactionHistoryResAdptOperation searchTransactionHistoryResAdptOperation) {
	this.searchTransactionHistoryResAdptOperation = searchTransactionHistoryResAdptOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {
	// TODO Auto-generated method stub
	Object obj = searchTransactionHistoryReqAdptOperation.adaptRequestforAddBeneficiary(workContext);
	Object obj1 = transmissionOperation.sendAndReceive(obj);
	Object obj2 = searchTransactionHistoryResAdptOperation.adaptResponse(workContext, obj1);
	return obj2;
    }

}
