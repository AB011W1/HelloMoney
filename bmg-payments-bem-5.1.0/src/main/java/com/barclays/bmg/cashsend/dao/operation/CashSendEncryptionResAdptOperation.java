package com.barclays.bmg.cashsend.dao.operation;


import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinResponse;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;




public class CashSendEncryptionResAdptOperation extends AbstractResAdptOperation {

    public CashSendOneTimeExecuteServiceResponse adaptResponse(WorkContext workContext, Object obj) {

    	CreditCardATMPinResponse bemResponse = (CreditCardATMPinResponse) obj;
    	CashSendOneTimeExecuteServiceResponse cashSendExecuteCashSendOneTimeExecuteServiceResponse = new CashSendOneTimeExecuteServiceResponse();
    	String resCode = bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode();

    	cashSendExecuteCashSendOneTimeExecuteServiceResponse.setResCde(resCode);
    	cashSendExecuteCashSendOneTimeExecuteServiceResponse.setServiceResponse(bemResponse.getResponseHeader().getServiceResStatus()
    		.getServiceResCode());

    	if (bemResponse != null && checkResponseHeader(bemResponse.getResponseHeader())) {
    	    cashSendExecuteCashSendOneTimeExecuteServiceResponse.setPin(bemResponse.getEncryptPinReponses().getEncryptPin());

    	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
    		cashSendExecuteCashSendOneTimeExecuteServiceResponse.setResMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
    	    } else {
    		cashSendExecuteCashSendOneTimeExecuteServiceResponse.setResMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
    	    }

    	} else {
    	    cashSendExecuteCashSendOneTimeExecuteServiceResponse.setSuccess(Boolean.FALSE);
    	}

    	return cashSendExecuteCashSendOneTimeExecuteServiceResponse;
    }

}
