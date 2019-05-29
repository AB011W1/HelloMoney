package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.AddProblem.AddProblemResponse;
import com.barclays.bem.BEMServiceHeader.ServiceResStatus;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;

public class ApplyTDRespAdptOperation extends AbstractResAdptOperationAcct {

	public ApplyTDServiceResponse adaptResponse(WorkContext workContext,Object obj) {

		ApplyTDServiceResponse response = new ApplyTDServiceResponse();
		AddProblemResponse bemResponse = (AddProblemResponse) obj;

		if (!checkResponseHeader(bemResponse.getResponseHeader())) {
			response.setSuccess(false);
		} else {
			ServiceResStatus serviceResStatus = bemResponse.getResponseHeader()
					.getServiceResStatus();
			response.setServiceStatus(serviceResStatus.getServiceResCode());
			response.setResCde(serviceResStatus.getServiceResCode());
			response.setResMsg(serviceResStatus.getServiceResDesc());
			response.setTransDate(serviceResStatus.getServiceRespDateTime()
					.toString());
			response.setTransactionRefNum(serviceResStatus
					.getServiceUniqueRefNo());

		}

		return response;
	}
}
