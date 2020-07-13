package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bem.CustomerInfo.CustomerBasic;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.CustomerAccountBasic;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.ValidateMobileDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.ValidateMobileDetailsResAdptOperation;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;


public class PayeeValidateDAOController implements Controller {

	private ValidateMobileDetailsReqAdptOperation validateMobileDetailsReqAdptOperation;
	private TransmissionOperation validateMobileDetailsTransmissionOperation;
	private ValidateMobileDetailsResAdptOperation validateMobileDetailsResAdptOperation;

	
	
	public ValidateMobileDetailsReqAdptOperation getValidateMobileDetailsReqAdptOperation() {
		return validateMobileDetailsReqAdptOperation;
	}

	public void setValidateMobileDetailsReqAdptOperation(
			ValidateMobileDetailsReqAdptOperation validateMobileDetailsReqAdptOperation) {
		this.validateMobileDetailsReqAdptOperation = validateMobileDetailsReqAdptOperation;
	}

	public TransmissionOperation getValidateMobileDetailsTransmissionOperation() {
		return validateMobileDetailsTransmissionOperation;
	}

	public void setValidateMobileDetailsTransmissionOperation(
			TransmissionOperation validateMobileDetailsTransmissionOperation) {
		this.validateMobileDetailsTransmissionOperation = validateMobileDetailsTransmissionOperation;
	}

	public ValidateMobileDetailsResAdptOperation getValidateMobileDetailsResAdptOperation() {
		return validateMobileDetailsResAdptOperation;
	}

	public void setValidateMobileDetailsResAdptOperation(
			ValidateMobileDetailsResAdptOperation validateMobileDetailsResAdptOperation) {
		this.validateMobileDetailsResAdptOperation = validateMobileDetailsResAdptOperation;
	}

	
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		// TODO Auto-generated method stub
		Object obj = validateMobileDetailsReqAdptOperation.adaptRequest(workContext);		
		Object obj1 = validateMobileDetailsTransmissionOperation.sendAndReceive(obj);
		return validateMobileDetailsResAdptOperation.adaptResponse(workContext, obj1);
	}

		

}
