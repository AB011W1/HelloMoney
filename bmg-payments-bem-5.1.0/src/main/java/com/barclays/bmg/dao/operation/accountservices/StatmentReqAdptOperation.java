package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.OrderPaperStatement.OrderPaperStatementRequest;
import com.barclays.bmg.dao.accountservices.adapter.StatmentPayloadAdapter;
import com.barclays.bmg.dao.accountservices.adapter.StatmentRequestHeaderAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class StatmentReqAdptOperation {

	StatmentRequestHeaderAdapter statmentRequestHeaderAdapter;

	StatmentPayloadAdapter statmentPayloadAdapter;

	//public StatmetnBEMServiceRequest adaptRequest(WorkContext workContext){
	public OrderPaperStatementRequest adaptRequest(WorkContext workContext){
		OrderPaperStatementRequest orderPaperStatementRequest = new OrderPaperStatementRequest();

		/*ChequeBookBEMServiceRequest chequeBookBEMServiceRequest =
					new ChequeBookBEMServiceRequest();*/

		orderPaperStatementRequest.setRequestHeader(statmentRequestHeaderAdapter.buildRequestHeader(workContext));
		orderPaperStatementRequest.setOrderPaperStatement(statmentPayloadAdapter.adaptPayLoad(workContext));
/*		StatmetnBEMServiceRequest statmetnBEMServiceRequest = new StatmetnBEMServiceRequest();
		statmetnBEMServiceRequest.setBemReqHeader(statmentRequestHeaderAdapter.buildRequestHeader(workContext));
		statmetnBEMServiceRequest.setBemResHeaderHolder(new BEMResHeaderHolder());
		statmetnBEMServiceRequest.setOrderPaperStatementRequest();
		statmetnBEMServiceRequest.setTransactionResponseStatusHolder(new TransactionResponseStatusHolder());
*/
		return orderPaperStatementRequest;


	}

	/**
	 * @return the statmentRequestHeaderAdapter
	 */
	public StatmentRequestHeaderAdapter getStatmentRequestHeaderAdapter() {
		return statmentRequestHeaderAdapter;
	}

	/**
	 * @param statmentRequestHeaderAdapter the statmentRequestHeaderAdapter to set
	 */
	public void setStatmentRequestHeaderAdapter(
			StatmentRequestHeaderAdapter statmentRequestHeaderAdapter) {
		this.statmentRequestHeaderAdapter = statmentRequestHeaderAdapter;
	}

	/**
	 * @return the statmentPayloadAdapter
	 */
	public StatmentPayloadAdapter getStatmentPayloadAdapter() {
		return statmentPayloadAdapter;
	}

	/**
	 * @param statmentPayloadAdapter the statmentPayloadAdapter to set
	 */
	public void setStatmentPayloadAdapter(
			StatmentPayloadAdapter statmentPayloadAdapter) {
		this.statmentPayloadAdapter = statmentPayloadAdapter;
	}




}
