package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.operation.request.RetailTxnCheckHeaderReq;
import com.barclays.bmg.service.ReportProblemService;
import com.barclays.bmg.service.request.ReportProblemServiceRequest;

/**
 * @author E20027734
 * 
 */
public class BMBRetailTxnRespAdptOperation extends AbstractResAdptOperation {
    private ReportProblemService reportProblemService;

    protected boolean checkResponseHeader(WorkContext workContext, BEMResHeader resHeader, RetailTxnCheckHeaderReq retailTxnCheckHeaderReq) {
	boolean valid = false;
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode) || ResponseCodeConstants.SUBMITTED_TXN_RES_CODE.equals(resCode)) {
		valid = true;
	    }

	    if (ResponseCodeConstants.TXN_PARTIAL_UPDATE.equals(resCode) || ResponseCodeConstants.TXN_SUSPECT.equals(resCode)) {
		// Call Report problem service to update BEM.
		RequestContext request = (RequestContext) args[0];
		ReportProblemServiceRequest prblmReq = new ReportProblemServiceRequest();
		prblmReq.setContext(request.getContext());
		prblmReq.setAccountNumber(retailTxnCheckHeaderReq.getFrmAcctNo());
		prblmReq.setAccountType(retailTxnCheckHeaderReq.getFrmAcctPrdCode());
		prblmReq.setResponseHeader(resHeader);

		reportProblemService.addProblem(prblmReq);
		valid = false;
	    }

	}
	super.checkResponseHeader(resHeader);
	return valid;
    }

    public ReportProblemService getReportProblemService() {
	return reportProblemService;
    }

    public void setReportProblemService(ReportProblemService reportProblemService) {
	this.reportProblemService = reportProblemService;
    }

}
