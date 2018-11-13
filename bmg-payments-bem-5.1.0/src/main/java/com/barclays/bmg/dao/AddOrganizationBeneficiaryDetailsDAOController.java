package com.barclays.bmg.dao;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.AddOrgBeneficiaryReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.AddOrgBeneficiaryRespAdptOperation;

/**
 * @author E20041929 Controller is mainly designed for registering billers to
 *         BEM from Mobile apps/USSD etc
 */
public class AddOrganizationBeneficiaryDetailsDAOController implements
		Controller {

	private AddOrgBeneficiaryReqAdptOperation addOrgBeneficiaryReqAdptOperation;
	private AddOrgBeneficiaryRespAdptOperation addOrgBeneficiaryRespAdptOperation;

	private TransmissionOperation transmissionOperation;

	// This method will be automatically invoked from DaoFrontControllerImpl
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = getAddOrgBeneficiaryReqAdptOperation()
				.adaptRequestforAddOrgBeneficiary(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = addOrgBeneficiaryRespAdptOperation.adaptResponse(
				workContext, obj1);
		return obj2;
	}

	public AddOrgBeneficiaryReqAdptOperation getAddOrgBeneficiaryReqAdptOperation() {
		return addOrgBeneficiaryReqAdptOperation;
	}

	public void setAddOrgBeneficiaryDetailsReqAdptOperation(
			AddOrgBeneficiaryReqAdptOperation addOrgBeneficiaryDetailsReqAdptOperation) {
		this.addOrgBeneficiaryReqAdptOperation = addOrgBeneficiaryDetailsReqAdptOperation;
	}

	public AddOrgBeneficiaryRespAdptOperation getAddOrgBeneficiaryRespAdptOperation() {
		return addOrgBeneficiaryRespAdptOperation;
	}

	public void setAddOrgBeneficiaryRespAdptOperation(
			AddOrgBeneficiaryRespAdptOperation addOrgBeneficiaryRespAdptOperation) {
		this.addOrgBeneficiaryRespAdptOperation = addOrgBeneficiaryRespAdptOperation;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}

	public void setTransmissionOperation(
			TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}

}
