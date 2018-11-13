package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.AddProblem.AddProblemRequest;
import com.barclays.bmg.dao.accountservices.adapter.ReportProblemHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ReportProblemPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ReportProblemReqAdptOperation {

	private ReportProblemHeaderAdapter reportProblemHeaderAdapter;
	private ReportProblemPayloadAdapter reportProblemPayloadAdapter;

	public AddProblemRequest adaptRequestForRequestProblem(WorkContext workContext){

		AddProblemRequest request = new AddProblemRequest();
		request.setRequestHeader(reportProblemHeaderAdapter.buildRequestHeader(workContext));
		request.setRequest(reportProblemPayloadAdapter.adaptPayload(workContext));
		return request;
	}

	public ReportProblemHeaderAdapter getReportProblemHeaderAdapter() {
		return reportProblemHeaderAdapter;
	}

	public void setReportProblemHeaderAdapter(
			ReportProblemHeaderAdapter reportProblemHeaderAdapter) {
		this.reportProblemHeaderAdapter = reportProblemHeaderAdapter;
	}

	public ReportProblemPayloadAdapter getReportProblemPayloadAdapter() {
		return reportProblemPayloadAdapter;
	}

	public void setReportProblemPayloadAdapter(
			ReportProblemPayloadAdapter reportProblemPayloadAdapter) {
		this.reportProblemPayloadAdapter = reportProblemPayloadAdapter;
	}

}
