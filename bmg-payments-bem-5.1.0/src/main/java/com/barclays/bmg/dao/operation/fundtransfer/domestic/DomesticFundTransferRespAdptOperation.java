package com.barclays.bmg.dao.operation.fundtransfer.domestic;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.payments.BMBRetailTxnRespAdptOperation;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.request.RetailTxnCheckHeaderReq;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;

public class DomesticFundTransferRespAdptOperation extends
		BMBRetailTxnRespAdptOperation {

	public DomesticFundTransferServiceResponse adaptResponse(
			WorkContext workContext, Object obj) {

		DomesticFundTransferServiceResponse fundTransferResponse = new DomesticFundTransferServiceResponse();

		MakeDomesticFundTransferResponse response = (MakeDomesticFundTransferResponse) obj;

		RetailTxnCheckHeaderReq retailTxnCheckHeaderReq = new RetailTxnCheckHeaderReq();

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		DomesticFundTransferServiceRequest fTExecuteRequest = (DomesticFundTransferServiceRequest) args[0];
		CustomerAccountDTO srcAcct = fTExecuteRequest.getSourceAcct();
		retailTxnCheckHeaderReq.setFrmAcctNo(srcAcct.getAccountNumber());
		retailTxnCheckHeaderReq.setFrmAcctPrdCode(srcAcct.getProductCode());

		fundTransferResponse.setSuccess(checkResponseHeader(workContext,
				response.getResponseHeader(), retailTxnCheckHeaderReq));

		String resCode = response.getResponseHeader().getServiceResStatus()
				.getServiceResCode();

		if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
			fundTransferResponse
					.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
		} else if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
			fundTransferResponse
					.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
		}

		return fundTransferResponse;

	}
}
