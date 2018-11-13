package com.barclays.bmg.dao.operation.accountservices;

import java.util.List;

import com.barclays.bem.AddProblem.AddProblemResponse;
import com.barclays.bem.problem.CaseEntity;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;

public class ReportProblemRespAdptOperation extends AbstractResAdptOperation{

	public ReportProblemServiceResponse adaptResponse(WorkContext workContext, Object obj){
		ReportProblemServiceResponse response = new ReportProblemServiceResponse();
		AddProblemResponse bemResponse = (AddProblemResponse)obj;

		if(checkResponseHeader(bemResponse.getResponseHeader())){
			response.setSuccess(true);
			CaseEntity[] caseEntityList = bemResponse.getAddProblemResult().getEntitiesList();
			response.setCaseNumber(caseEntityList[0].getCaseNumber());
			response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
		} else{
			response.setSuccess(false);
			response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
		}

		return response;
	}
}
