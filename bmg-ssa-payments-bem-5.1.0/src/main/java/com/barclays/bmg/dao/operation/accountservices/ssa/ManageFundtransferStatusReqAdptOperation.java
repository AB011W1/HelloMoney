package com.barclays.bmg.dao.operation.accountservices.ssa;

import com.barclays.bem.FundsTransferInfo.FundsTransferInfo;
import com.barclays.bem.ManageFundTransferStatus.ManageFundTransferStatusRequest;
import com.barclays.bmg.dao.accountservices.adapter.ssa.ManageFundtransferStatusHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ssa.ManageFundtransferStatusPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.ManageFundtransferStatusServiceRequest;

public class ManageFundtransferStatusReqAdptOperation {
	ManageFundtransferStatusHeaderAdapter manageFundtransferStatusHeaderAdapter;
	ManageFundtransferStatusPayloadAdapter manageFundtransferStatusPayloadAdapter;

	public ManageFundtransferStatusHeaderAdapter getManageFundtransferStatusHeaderAdapter() {
		return manageFundtransferStatusHeaderAdapter;
	}

	public void setManageFundtransferStatusHeaderAdapter(
			ManageFundtransferStatusHeaderAdapter manageFundtransferStatusHeaderAdapter) {
		this.manageFundtransferStatusHeaderAdapter = manageFundtransferStatusHeaderAdapter;
	}

	public ManageFundtransferStatusPayloadAdapter getManageFundtransferStatusPayloadAdapter() {
		return manageFundtransferStatusPayloadAdapter;
	}

	public void setManageFundtransferStatusPayloadAdapter(
			ManageFundtransferStatusPayloadAdapter manageFundtransferStatusPayloadAdapter) {
		this.manageFundtransferStatusPayloadAdapter = manageFundtransferStatusPayloadAdapter;
	}

	public ManageFundTransferStatusRequest adaptRequest(WorkContext workContext) {
		ManageFundTransferStatusRequest manageFundTransferStatusRequest=new ManageFundTransferStatusRequest();

		manageFundTransferStatusRequest
				.setRequestHeader(manageFundtransferStatusHeaderAdapter
						.buildRequestHeader(workContext));

		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		ManageFundtransferStatusServiceRequest request = (ManageFundtransferStatusServiceRequest) args[0];
		manageFundTransferStatusRequest.setActionCode(request.getActionCode());

		FundsTransferInfo[] finfo=manageFundtransferStatusPayloadAdapter.adaptPayLoad(workContext);
		if(request.getActionCode().equals("SAVE"))
			manageFundTransferStatusRequest.setFundTransferInfoList(finfo);
		else if(request.getActionCode().equals("UPDATE"))
			manageFundTransferStatusRequest.setFundTransferInfo(finfo[0]);

		return manageFundTransferStatusRequest;

	}
}
