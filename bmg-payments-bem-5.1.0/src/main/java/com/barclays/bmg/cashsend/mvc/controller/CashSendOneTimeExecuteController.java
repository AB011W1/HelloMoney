package com.barclays.bmg.cashsend.mvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.cashsend.mvc.command.CashSendOneTimeExecuteCommand;
import com.barclays.bmg.cashsend.operation.CashSendOneTimeExecuteOperation;
import com.barclays.bmg.cashsend.operation.request.CashSendOneTimeExecuteOperationRequest;
import com.barclays.bmg.cashsend.operation.response.CashSendOneTimeExecuteOperationResponse;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.constants.MessageCodeConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CashSendRequestDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.ussd.utils.USSDConstants;

public class CashSendOneTimeExecuteController extends BMBAbstractCommandController {

    private BMBJSONBuilder bmbJSONBuilder;

    private static final Logger LOGGER = Logger.getLogger(CashSendOneTimeExecuteController.class);

    private CashSendOneTimeExecuteOperation cashSendOneTimeExecuteOperation;

    @Override
    protected String getActivityId(Object command) {
	return ActivityConstant.PMT_FT_CS;
    }

    private boolean varifyTxnRefNo(String reqTxnRefNo, CashSendRequestDTO cashSendRequestDTO) {
	boolean result = false;
	String txnRefNo = cashSendRequestDTO.getTxnRefNo();

	if (txnRefNo.equalsIgnoreCase(reqTxnRefNo)) {
	    result = true;

	}

	return result;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	setLastStep(request);

	CashSendOneTimeExecuteCommand cashOneTimeExecuteCommand = (CashSendOneTimeExecuteCommand) command;

	Context context = buildContext(request);
	String txnRefNo = cashOneTimeExecuteCommand.getTxnRefNo();
	CashSendRequestDTO cashSendRequestDTO = (CashSendRequestDTO) getSessionAttribute(request, "CASH_SEND_DTLS");
	boolean result = false;
	if(null != txnRefNo && null != cashSendRequestDTO)
		result	= varifyTxnRefNo(txnRefNo, cashSendRequestDTO);

	CashSendOneTimeExecuteOperationResponse cashSendOneTimeExecuteOperationResponse = null;
	boolean isCashSendErrorResponse = true;
	if (cashSendRequestDTO != null && txnRefNo != null && result) {
	    CashSendOneTimeExecuteOperationRequest cashSendOneTimeExecuteOperationRequest = new CashSendOneTimeExecuteOperationRequest();
	    cashSendOneTimeExecuteOperationRequest.setCashSendRequestDTO(cashSendRequestDTO);
	    cashSendOneTimeExecuteOperationRequest.setContext(context);
	    LOGGER.debug("encryptCashSendPin method call");
	    cashSendOneTimeExecuteOperationResponse = cashSendOneTimeExecuteOperation.encryptCashSendPin(cashSendOneTimeExecuteOperationRequest);

	    if ((cashSendOneTimeExecuteOperationResponse != null) && (cashSendOneTimeExecuteOperationResponse.getPin() != null)) {
		try {
		    isCashSendErrorResponse = false;
		    cashSendRequestDTO.setVPin(cashSendOneTimeExecuteOperationResponse.getPin());
		    cashSendOneTimeExecuteOperationRequest.setCashSendRequestDTO(cashSendRequestDTO);

		    cashSendOneTimeExecuteOperationResponse = cashSendOneTimeExecuteOperation
			    .executeCashSendOperation(cashSendOneTimeExecuteOperationRequest);

		    /* Code starts for SMS */

		    if (cashSendOneTimeExecuteOperationResponse != null) {
		    	if (cashSendOneTimeExecuteOperationResponse.isSuccess()) {
		    	cashSendOneTimeExecuteOperation.sendSMSSuccessAcknowledgment(cashSendOneTimeExecuteOperationRequest,
		    			cashSendOneTimeExecuteOperationResponse);
		    	} else if (!cashSendOneTimeExecuteOperationResponse.isSuccess()) {
		      cashSendOneTimeExecuteOperation.sendSMSFailAcknowledgment(cashSendOneTimeExecuteOperationRequest,
		      cashSendOneTimeExecuteOperationResponse);
		      }
		    }
		} catch (Exception ex) {
			cashSendOneTimeExecuteOperationResponse.setSuccess(Boolean.FALSE);
		    cashSendOneTimeExecuteOperation.sendSMSFailAcknowledgment(cashSendOneTimeExecuteOperationRequest,
		    cashSendOneTimeExecuteOperationResponse);
		    throw ex;
		}
		/*Code ends for SMS */
	    }
	}
	if (isCashSendErrorResponse) {
	    cashSendOneTimeExecuteOperationResponse = getCashSendErrorResponse(context);
	}
	return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder)
		.createMultipleJSONResponse(cashSendOneTimeExecuteOperationResponse);
    }

    private CashSendOneTimeExecuteOperationResponse getCashSendErrorResponse(Context context) {
	CashSendOneTimeExecuteOperationResponse cashSendOneTimeExecuteOperationResponse = null;
	cashSendOneTimeExecuteOperationResponse = new CashSendOneTimeExecuteOperationResponse();
	cashSendOneTimeExecuteOperationResponse.setContext(context);
	cashSendOneTimeExecuteOperationResponse.setResCde(FundTransferResponseCodeConstants.CASH_SEND_NEVER_INITIATED);
	cashSendOneTimeExecuteOperationResponse.setResMsg(getErrorMessage(MessageCodeConstant.CASH_SEND_NEVER_INITIATED));
	cashSendOneTimeExecuteOperationResponse.setSuccess(false);
	return cashSendOneTimeExecuteOperationResponse;
    }

    /**
     * This method takes in httpserquest object as the input parameter and builds the context.
     *
     * @param httpRequest
     * @return
     */
    protected Context buildContext(HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);

	Map<String, Object> userMap = getUserMapFromSession(httpRequest);
	context.setPpMap((Map<String, String>) userMap.get(SessionConstant.SESSION_PP_MAP));
	// context.setActivityId("PMT_FT_CASHSEND_ONETIME");
	context.setActivityId("PMT_FT_CS");
	//context.setActivityRefNo(trimRefNumber(context.getActivityRefNo()));
	//context.setOrgRefNo(trimRefNumber(context.getOrgRefNo()));
	String referenceNumber = generateReferenceNumber();
	context.setActivityRefNo(referenceNumber);
	context.setOrgRefNo(referenceNumber);
	return context;

    }
    
    private String generateReferenceNumber() {
		// Set<String> numbers = new HashSet<String>();
		String generatedString = RandomStringUtils.random(USSDConstants.REFERENCE_NUMBER_LENGTH,
				USSDConstants.USE_LETTER, USSDConstants.USE_NUMBER);
		if (generatedString.length() != 10 || generatedString.startsWith("-")) {
			// System.out.println("failure at " + count);
			generateReferenceNumber();
		}
		return generatedString;
	}

    private String trimRefNumber(String OrgRefNumber) {
	if (OrgRefNumber != null && OrgRefNumber.length() > 10) {
	    return OrgRefNumber.substring(0, 10);
	}
	return null;
    }

    public CashSendOneTimeExecuteOperation getCashSendOneTimeExecuteOperation() {
	return cashSendOneTimeExecuteOperation;
    }

    public void setCashSendOneTimeExecuteOperation(CashSendOneTimeExecuteOperation cashSendOneTimeExecuteOperation) {
	this.cashSendOneTimeExecuteOperation = cashSendOneTimeExecuteOperation;
    }

    public BMBJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

}
