package com.barclays.bmg.ussd.dao.processing;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveindividualCustCardListReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.RetrieveindividualCustCardListResAdptOperation;

public class RetrieveindividualCustCardListDAOController implements Controller {

	 /**
     * The instance variable for retrieveindividualCustCardListReqAdptOperation of type RetrieveindividualCustCardListReqAdptOperation
     */
    private RetrieveindividualCustCardListReqAdptOperation retrieveindividualCustCardListReqAdptOperation;
    /**
     * The instance variable for retrieveindividualCustCardListResAdptOperation of type RetrieveindividualCustCardListResAdptOperation
     */
    private RetrieveindividualCustCardListResAdptOperation retrieveindividualCustCardListResAdptOperation;
    /**
     * The instance variable for transmissionOperation of type TransmissionOperation
     */
    private TransmissionOperation transmissionOperation;

    /**
     * Overrides handleRequest method of Controller
     *
     * @param WorkContext
     * @return Object
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = retrieveindividualCustCardListReqAdptOperation.adaptRequestForIndvCustCardList(workContext);

	Object obj1 = transmissionOperation.sendAndReceive(obj);

	Object obj2 = retrieveindividualCustCardListResAdptOperation.adaptResponse(workContext, obj1);

	return obj2;
    }

    /**
     * Getter for RetrieveindividualCustCardListReqAdptOperation
     *
     *@param none
     *@return RetrieveindividualCustCardListReqAdptOperation
     */
    public RetrieveindividualCustCardListReqAdptOperation getRetrieveindividualCustCardListReqAdptOperation() {
		return retrieveindividualCustCardListReqAdptOperation;
	}

    /**
     * Setter for RetrieveindividualCustCardListReqAdptOperation
     *
     * @param RetrieveindividualCustCardListReqAdptOperation
     * @return void
     */
	public void setRetrieveindividualCustCardListReqAdptOperation(
			RetrieveindividualCustCardListReqAdptOperation retrieveindividualCustCardListReqAdptOperation) {
		this.retrieveindividualCustCardListReqAdptOperation = retrieveindividualCustCardListReqAdptOperation;
	}

	/**
     * Getter for RetrieveindividualCustCardListResAdptOperation
     *
     *@param none
     *@return RetrieveindividualCustCardListResAdptOperation
     */
	public RetrieveindividualCustCardListResAdptOperation getRetrieveindividualCustCardListResAdptOperation() {
		return retrieveindividualCustCardListResAdptOperation;
	}

	 /**
     * Setter for RetrieveindividualCustCardListResAdptOperation
     *
     * @param RetrieveindividualCustCardListResAdptOperation
     * @return void
     */
	public void setRetrieveindividualCustCardListResAdptOperation(
			RetrieveindividualCustCardListResAdptOperation retrieveindividualCustCardListResAdptOperation) {
		this.retrieveindividualCustCardListResAdptOperation = retrieveindividualCustCardListResAdptOperation;
	}


	/**
     * Getter for TransmissionOperation
     *
     *@param none
     *@return TransmissionOperation
     */
    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    /**
     * Setter for TransmissionOperation
     *
     * @param TransmissionOperation
     * @return void
     */
    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

}
