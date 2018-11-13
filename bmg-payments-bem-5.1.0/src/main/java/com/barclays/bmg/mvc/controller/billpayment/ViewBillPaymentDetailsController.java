package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.operation.payments.ViewTxnHistoryDetailsOperation;
import com.barclays.bmg.operation.request.billpayment.ViewTxnHistoryDetailsOperationRequest;
import com.barclays.bmg.operation.response.billpayment.ViewTxnHistoryDetailsOperationResponse;

/**
 * @author BTCI
 *
 */
public class ViewBillPaymentDetailsController extends
		ViewTxnHistoryDetailsController {
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private ViewTxnHistoryDetailsOperation viewBillPaymentDetailsOperation;

	/**
	 * @param viewBillPaymentDetailsOperation
	 */
	public void setViewBillPaymentDetailsOperation(
			ViewTxnHistoryDetailsOperation viewBillPaymentDetailsOperation) {
		this.viewBillPaymentDetailsOperation = viewBillPaymentDetailsOperation;
	}

	/**
	 * @param bmbJSONBuilder
	 */
	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.mvc.controller.billpayment.ViewTxnHistoryDetailsController
	 * #handle1(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setLastStep(httpRequest);
		ViewTxnHistoryDetailsOperationResponse viewTxnHistoryDetailsOperationResponse = null;
		ViewTxnHistoryDetailsOperationRequest viewTxnHistoryDetailsOperationRequest = null;

		viewTxnHistoryDetailsOperationRequest = buildOperationRequest(
				httpRequest, response, command, errors);

		viewTxnHistoryDetailsOperationResponse = viewBillPaymentDetailsOperation
				.viewTxnHistoryDetails(viewTxnHistoryDetailsOperationRequest);

		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(viewTxnHistoryDetailsOperationResponse);
	}

}
