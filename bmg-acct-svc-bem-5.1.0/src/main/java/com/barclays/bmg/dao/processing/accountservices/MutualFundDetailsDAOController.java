package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.investment.MutualFundDetailsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.investment.MutualFundDetailsRespAdptOperation;

public class MutualFundDetailsDAOController implements Controller {

    private MutualFundDetailsReqAdptOperation mutualFundDetailsReqAdptOperation;

    private TransmissionOperation investmentTransmissionOperation;

    private MutualFundDetailsRespAdptOperation mutualFundDetailsRespAdptOperation;

    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = mutualFundDetailsReqAdptOperation.adaptRequestForMutualFundDetails(workContext);

	Object obj1 = investmentTransmissionOperation.sendAndReceive(obj);

	Object obj2 = mutualFundDetailsRespAdptOperation.adaptResponseForMutualFundDetails(workContext, obj1);

	return obj2;
    }

    public void setInvestmentTransmissionOperation(TransmissionOperation investmentTransmissionOperation) {
	this.investmentTransmissionOperation = investmentTransmissionOperation;
    }

    public void setMutualFundDetailsReqAdptOperation(MutualFundDetailsReqAdptOperation mutualFundDetailsReqAdptOperation) {
	this.mutualFundDetailsReqAdptOperation = mutualFundDetailsReqAdptOperation;
    }

    public void setMutualFundDetailsRespAdptOperation(MutualFundDetailsRespAdptOperation mutualFundDetailsRespAdptOperation) {
	this.mutualFundDetailsRespAdptOperation = mutualFundDetailsRespAdptOperation;
    }

}
