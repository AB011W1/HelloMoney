package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.operation.request.RetailTxnCheckHeaderReq;
import com.barclays.bmg.service.request.FundTransferExecuteServiceRequest;
import com.barclays.bmg.service.response.FundTransferExecuteServiceResponse;



public class FundTransferRespAdptOperation extends BMBRetailTxnRespAdptOperation {

	public FundTransferExecuteServiceResponse adaptResponse(WorkContext workContext, Object obj){

		FundTransferExecuteServiceResponse fundTransferResponse = new FundTransferExecuteServiceResponse();

		MakeDomesticFundTransferResponse response = (MakeDomesticFundTransferResponse) obj;

		RetailTxnCheckHeaderReq retailTxnCheckHeaderReq = new RetailTxnCheckHeaderReq();

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		FundTransferExecuteServiceRequest fTExecuteRequest =
									(FundTransferExecuteServiceRequest)args[0];

		retailTxnCheckHeaderReq.setFrmAcctNo(fTExecuteRequest.getFrmAcct().getAccountNumber());
		retailTxnCheckHeaderReq.setFrmAcctPrdCode(fTExecuteRequest.getFrmAcct().getProductCode());

		fundTransferResponse.setSuccess(checkResponseHeader(workContext,response.getResponseHeader(),retailTxnCheckHeaderReq));

		return fundTransferResponse;

	}
}
