package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreiveIndvBeneficiaryDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreiveIndvBeneficiaryDetailsRespAdptOperation;

/**
 * @author E20027734
 *	This controller fetches beneficiary details from BEM for Individual beneficiarys.
 *  Used for Credit card beneficiary.
 */
public class RetreiveIndividualBeneficiaryDetailsDAOController implements Controller{

	private RetreiveIndvBeneficiaryDetailsReqAdptOperation retreiveIndvBeneficiaryDetailsReqAdptOperation;
	private RetreiveIndvBeneficiaryDetailsRespAdptOperation retreiveIndvBeneficiaryDetailsRespAdptOperation;
	private TransmissionOperation transmissionOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		Object obj = retreiveIndvBeneficiaryDetailsReqAdptOperation.adaptRequestforOrgBeneficiaryDetails(workContext);
		Object obj1= transmissionOperation.sendAndReceive(obj);
		Object obj2 = retreiveIndvBeneficiaryDetailsRespAdptOperation.adaptResponse(workContext, obj1);
		return obj2;
	}
	public RetreiveIndvBeneficiaryDetailsReqAdptOperation getRetreiveIndvBeneficiaryDetailsReqAdptOperation() {
		return retreiveIndvBeneficiaryDetailsReqAdptOperation;
	}
	public void setRetreiveIndvBeneficiaryDetailsReqAdptOperation(
			RetreiveIndvBeneficiaryDetailsReqAdptOperation retreiveIndvBeneficiaryDetailsReqAdptOperation) {
		this.retreiveIndvBeneficiaryDetailsReqAdptOperation = retreiveIndvBeneficiaryDetailsReqAdptOperation;
	}
	public RetreiveIndvBeneficiaryDetailsRespAdptOperation getRetreiveIndvBeneficiaryDetailsRespAdptOperation() {
		return retreiveIndvBeneficiaryDetailsRespAdptOperation;
	}
	public void setRetreiveIndvBeneficiaryDetailsRespAdptOperation(
			RetreiveIndvBeneficiaryDetailsRespAdptOperation retreiveIndvBeneficiaryDetailsRespAdptOperation) {
		this.retreiveIndvBeneficiaryDetailsRespAdptOperation = retreiveIndvBeneficiaryDetailsRespAdptOperation;
	}
	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

}
