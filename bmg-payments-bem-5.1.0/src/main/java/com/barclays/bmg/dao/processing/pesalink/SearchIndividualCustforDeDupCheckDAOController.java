package com.barclays.bmg.dao.processing.pesalink;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.pesalink.SearchIndividualCustforDeDupCheckReqAdptOperation;
import com.barclays.bmg.dao.operation.pesalink.SearchIndividualCustforDeDupCheckResAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveindividualCustCardListReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveindividualCustCardListResAdptOperation;



public class SearchIndividualCustforDeDupCheckDAOController  implements Controller{


    private SearchIndividualCustforDeDupCheckReqAdptOperation searchIndividualCustforDeDupCheckReqAdptOperation;

    private SearchIndividualCustforDeDupCheckResAdptOperation searchIndividualCustforDeDupCheckResAdptOperation;

    private TransmissionOperation transmissionOperation;


    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = searchIndividualCustforDeDupCheckReqAdptOperation.adaptRequest(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = searchIndividualCustforDeDupCheckResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }


	public SearchIndividualCustforDeDupCheckReqAdptOperation getSearchIndividualCustforDeDupCheckReqAdptOperation() {
		return searchIndividualCustforDeDupCheckReqAdptOperation;
	}


	public void setSearchIndividualCustforDeDupCheckReqAdptOperation(
			SearchIndividualCustforDeDupCheckReqAdptOperation searchIndividualCustforDeDupCheckReqAdptOperation) {
		this.searchIndividualCustforDeDupCheckReqAdptOperation = searchIndividualCustforDeDupCheckReqAdptOperation;
	}


	public SearchIndividualCustforDeDupCheckResAdptOperation getSearchIndividualCustforDeDupCheckResAdptOperation() {
		return searchIndividualCustforDeDupCheckResAdptOperation;
	}


	public void setSearchIndividualCustforDeDupCheckResAdptOperation(
			SearchIndividualCustforDeDupCheckResAdptOperation searchIndividualCustforDeDupCheckResAdptOperation) {
		this.searchIndividualCustforDeDupCheckResAdptOperation = searchIndividualCustforDeDupCheckResAdptOperation;
	}


	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}


	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}


}
