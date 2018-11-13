package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.investment.BondDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.investment.BondDetailsRespAdptOperation;

public class BondDetailsDAOController implements Controller {

    private BondDetailsReqAdptOperation bondDetailsReqAdptOperation;

    private TransmissionOperation investmentTransmissionOperation;

    private BondDetailsRespAdptOperation bondDetailsRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = bondDetailsReqAdptOperation.adaptRequestForBondDetails(workContext);

	Object obj1 = investmentTransmissionOperation.sendAndReceive(obj);

	Object obj2 = bondDetailsRespAdptOperation.adaptResponseForBondDetails(workContext, obj1);

	return obj2;
    }

    public void setInvestmentTransmissionOperation(TransmissionOperation investmentTransmissionOperation) {
	this.investmentTransmissionOperation = investmentTransmissionOperation;
    }

    public void setBondDetailsReqAdptOperation(BondDetailsReqAdptOperation bondDetailsReqAdptOperation) {
	this.bondDetailsReqAdptOperation = bondDetailsReqAdptOperation;
    }

    public void setBondDetailsRespAdptOperation(BondDetailsRespAdptOperation bondDetailsRespAdptOperation) {
	this.bondDetailsRespAdptOperation = bondDetailsRespAdptOperation;
    }

}
