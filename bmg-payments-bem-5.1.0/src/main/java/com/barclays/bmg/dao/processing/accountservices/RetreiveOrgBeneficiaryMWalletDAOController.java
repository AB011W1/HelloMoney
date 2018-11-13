package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreiveOrgBeneficiaryDetailsMWalletReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.RetreiveOrgBeneficiaryDetailsRespAdptOperation;

/**
 * @author E20027734
 * This controller fetches the beneficiary details from BEM for Mobile top and Bill Payment Payees.
 */
public class RetreiveOrgBeneficiaryMWalletDAOController implements Controller{
	private RetreiveOrgBeneficiaryDetailsMWalletReqAdptOperation beneficiaryDetailsMWalletReqAdptOperation;
	//private RetreiveOrgBeneficiaryDetailsReqAdptOperation retreiveOrgBeneficiaryDetailsReqAdptOperation;
	private  TransmissionOperation transmissionOperation;
	private RetreiveOrgBeneficiaryDetailsRespAdptOperation retreiveOrgBeneficiaryDetailsRespAdptOperation;
	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {

		Object obj = beneficiaryDetailsMWalletReqAdptOperation.adaptRequestforOrgBeneficiaryDetails(workContext);
		Object obj1 = transmissionOperation.sendAndReceive(obj);
		Object obj2 = retreiveOrgBeneficiaryDetailsRespAdptOperation.adaptResponse(workContext, obj1);
		return obj2;
	}

	public TransmissionOperation getTransmissionOperation() {
		return transmissionOperation;
	}
	public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
		this.transmissionOperation = transmissionOperation;
	}
	public RetreiveOrgBeneficiaryDetailsRespAdptOperation getRetreiveOrgBeneficiaryDetailsRespAdptOperation() {
		return retreiveOrgBeneficiaryDetailsRespAdptOperation;
	}
	public void setRetreiveOrgBeneficiaryDetailsRespAdptOperation(
			RetreiveOrgBeneficiaryDetailsRespAdptOperation retreiveOrgBeneficiaryDetailsRespAdptOperation) {
		this.retreiveOrgBeneficiaryDetailsRespAdptOperation = retreiveOrgBeneficiaryDetailsRespAdptOperation;
	}

	/**
	 * @return the beneficiaryDetailsMWalletReqAdptOperation
	 */
	public RetreiveOrgBeneficiaryDetailsMWalletReqAdptOperation getBeneficiaryDetailsMWalletReqAdptOperation() {
		return beneficiaryDetailsMWalletReqAdptOperation;
	}

	/**
	 * @param beneficiaryDetailsMWalletReqAdptOperation the beneficiaryDetailsMWalletReqAdptOperation to set
	 */
	public void setBeneficiaryDetailsMWalletReqAdptOperation(
			RetreiveOrgBeneficiaryDetailsMWalletReqAdptOperation beneficiaryDetailsMWalletReqAdptOperation) {
		this.beneficiaryDetailsMWalletReqAdptOperation = beneficiaryDetailsMWalletReqAdptOperation;
	}
}