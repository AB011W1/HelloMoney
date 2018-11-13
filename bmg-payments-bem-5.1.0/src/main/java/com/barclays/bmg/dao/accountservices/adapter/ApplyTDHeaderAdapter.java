package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.ApplyTDServiceRequest;

public class ApplyTDHeaderAdapter extends AbstractReqAdptOperation{


	public BEMReqHeader buildRequestHeader(WorkContext workContext){

		BEMReqHeader reqHeader = super.buildRequestHeader(workContext, ServiceIdConstants.ADD_PROBLEM_SERVICE);
		//reqHeader.getBankUserContext().setStaffID("BIR");
		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		ApplyTDServiceRequest request=(ApplyTDServiceRequest)args[0];

		if(request.getContext().getContextMap()!=null && request.getContext().getContextMap().containsKey(SystemParameterConstant.APPLY_TD_USER_ID)){
			reqHeader.getBankUserContext().setStaffID(request.getContext().getContextMap().get(SystemParameterConstant.APPLY_TD_USER_ID).toString());
		}

		return reqHeader;


	}

}
