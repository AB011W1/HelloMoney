package com.barclays.bmg.dao.operation.accountservices;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.OrderPaperStatement.OrderPaperStatementResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.StatmentExecuteServiceRequest;
import com.barclays.bmg.service.response.StatmentExecuteServiceResponse;

public class StatmentExecuteResAdptOperation extends AbstractResAdptOperation {

	public StatmentExecuteServiceResponse adaptResponse(
			WorkContext workContext, Object obj) {



		OrderPaperStatementResponse response = (OrderPaperStatementResponse) obj;

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		StatmentExecuteServiceRequest request = (StatmentExecuteServiceRequest) args[0];

		StatmentExecuteServiceResponse statmentExecuteServiceResponse = new StatmentExecuteServiceResponse();

		statmentExecuteServiceResponse.setContext(request.getContext());

		if(checkResponseHeader(response.getResponseHeader())){

			statmentExecuteServiceResponse.setSuccess(true);

		}else {

			statmentExecuteServiceResponse.setSuccess(false);
		}

		statmentExecuteServiceResponse.setTxnRefNo(request.getContext()
				.getActivityRefNo());
		statmentExecuteServiceResponse.setTxnDt(new Date());

		String resCode = response.getResponseHeader().getServiceResStatus().getServiceResCode();

		if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
			statmentExecuteServiceResponse
					.setTxnMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
		} else if (StringUtils.isNotEmpty(resCode)
				&& ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
			statmentExecuteServiceResponse
					.setTxnMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
		}

		return statmentExecuteServiceResponse;
	}

}
