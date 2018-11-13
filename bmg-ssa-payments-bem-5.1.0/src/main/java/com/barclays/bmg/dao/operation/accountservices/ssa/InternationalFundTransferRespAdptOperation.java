package com.barclays.bmg.dao.operation.accountservices.ssa;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.MakeInternationalFundTransfer.MakeInternationalFundTransferResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.BMBRetailTxnRespAdptOperation;
import com.barclays.bmg.operation.request.RetailTxnCheckHeaderReq;
import com.barclays.bmg.service.request.InternationalFundTransferServiceRequest;
import com.barclays.bmg.service.response.InternationalFundTransferServiceResponse;

public class InternationalFundTransferRespAdptOperation extends
		BMBRetailTxnRespAdptOperation {

	public InternationalFundTransferServiceResponse adaptResponse(
			WorkContext workContext, Object obj) {

		InternationalFundTransferServiceResponse response = new InternationalFundTransferServiceResponse();

		MakeInternationalFundTransferResponse bemResponse = (MakeInternationalFundTransferResponse) obj;

		RetailTxnCheckHeaderReq retailTxnCheckHeaderReq = new RetailTxnCheckHeaderReq();

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		InternationalFundTransferServiceRequest serviceReq = (InternationalFundTransferServiceRequest) args[0];

		retailTxnCheckHeaderReq.setFrmAcctNo(serviceReq.getSourceAcct()
				.getAccountNumber());
		retailTxnCheckHeaderReq.setFrmAcctPrdCode(serviceReq.getSourceAcct()
				.getProductCode());

		response.setSuccess(checkResponseHeader(workContext, bemResponse
				.getResponseHeader(), retailTxnCheckHeaderReq));

		String resCode = bemResponse.getResponseHeader().getServiceResStatus()
				.getServiceResCode();
		if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
			response
					.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
		} else if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
			response
					.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
		}
		return response;

	}
}
