package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.GroupWalletAccountSummaryReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.GroupWalletAccountSummaryRespAdptOperation;

public class GroupWalletAccountSummaryDAOController implements Controller {

	GroupWalletAccountSummaryReqAdptOperation groupWalletAccountSummaryReqAdptOperation;
	TransmissionOperation groupWalletAccountSummaryTransmissionOperation;
	GroupWalletAccountSummaryRespAdptOperation groupWalletAccountSummaryRespAdptOperation;

	public void setGroupWalletAccountSummaryReqAdptOperation(
			GroupWalletAccountSummaryReqAdptOperation groupWalletAccountSummaryReqAdptOperation) {
		this.groupWalletAccountSummaryReqAdptOperation = groupWalletAccountSummaryReqAdptOperation;
	}

	public void setGroupWalletAccountSummaryTransmissionOperation(
			TransmissionOperation groupWalletAccountSummaryTransmissionOperation) {
		this.groupWalletAccountSummaryTransmissionOperation = groupWalletAccountSummaryTransmissionOperation;
	}

	public void setGroupWalletAccountSummaryRespAdptOperation(
			GroupWalletAccountSummaryRespAdptOperation groupWalletAccountSummaryRespAdptOperation) {
		this.groupWalletAccountSummaryRespAdptOperation = groupWalletAccountSummaryRespAdptOperation;
	}

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		// TODO Auto-generated method stub
		Object obj = groupWalletAccountSummaryReqAdptOperation.adaptRequest(workContext);

		Object obj1 = groupWalletAccountSummaryTransmissionOperation.sendAndReceive(obj);

		Object obj2 = groupWalletAccountSummaryRespAdptOperation.adaptResponse(workContext, obj1);

		return obj2;
	}

}
