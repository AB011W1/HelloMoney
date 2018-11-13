package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.ViewTxnHistoryDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.request.billpayment.ViewTxnHistoryDetailsOperationRequest;

/**
 * @author BTCI
 *
 */
public class ViewTxnHistoryDetailsController extends
		BMBAbstractCommandController {
	private String activityId;

	/**
	 * @param activityId
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#
	 * getActivityId(java.lang.Object)
	 */
	@Override
	protected String getActivityId(Object command) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		return null;
	}

	/**
	 * @param request
	 * @param httpRequest
	 * @return Context
	 */
	private Context addContext(RequestContext request,
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);
		context.setActivityId(activityId);
		request.setContext(context);
		return context;
	}

	/**
	 * @param httpRequest
	 * @param response
	 * @param command
	 * @param errors
	 * @return ViewTxnHistoryDetailsOperationRequest
	 */
	protected ViewTxnHistoryDetailsOperationRequest buildOperationRequest(
			HttpServletRequest httpRequest, HttpServletResponse response,
			Object command, BindException errors) {

		ViewTxnHistoryDetailsCommand viewTxnHistoryDetailsCommand = (ViewTxnHistoryDetailsCommand) command;
		ViewTxnHistoryDetailsOperationRequest viewTxnHistoryDetailsOperationRequest = new ViewTxnHistoryDetailsOperationRequest();
		Context context = addContext(viewTxnHistoryDetailsOperationRequest,
				httpRequest);
		viewTxnHistoryDetailsOperationRequest.setContext(context);
		viewTxnHistoryDetailsOperationRequest
				.setTransactionRefNo(viewTxnHistoryDetailsCommand
						.getTransactionRefNo());

		return viewTxnHistoryDetailsOperationRequest;

	}

}
