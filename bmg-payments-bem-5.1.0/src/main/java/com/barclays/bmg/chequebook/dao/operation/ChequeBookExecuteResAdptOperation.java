package com.barclays.bmg.chequebook.dao.operation;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.chequebook.dao.bem.response.ChequeBookBEMServiceResponse;
import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.chequebook.service.response.ChequeBookExecuteServiceResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;

public class ChequeBookExecuteResAdptOperation extends AbstractResAdptOperation {

	public ChequeBookExecuteServiceResponse adaptResponse(
			WorkContext workContext, Object obj) {

		ChequeBookBEMServiceResponse response = (ChequeBookBEMServiceResponse) obj;

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		ChequeBookExecuteServiceRequest request = (ChequeBookExecuteServiceRequest) args[0];

		ChequeBookExecuteServiceResponse chequeBookExecuteServiceResponse = new ChequeBookExecuteServiceResponse();

		chequeBookExecuteServiceResponse.setContext(request.getContext());

		chequeBookExecuteServiceResponse
				.setSuccess(checkResponseHeader(response
						.getBemResHeaderHolder().value));

		chequeBookExecuteServiceResponse.setTxnRefNo(request.getContext()
				.getActivityRefNo());
		chequeBookExecuteServiceResponse.setTxnDt(new Date());

		String resCode = response.getBemResHeaderHolder().value
				.getServiceResStatus().getServiceResCode();

		if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
			chequeBookExecuteServiceResponse
					.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
		} else if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
			chequeBookExecuteServiceResponse
					.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
		}

		return chequeBookExecuteServiceResponse;
	}

}
