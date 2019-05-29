package com.barclays.bmg.dao.operation.accountservices.ssa;

import java.util.Date;

import com.barclays.bem.ManageFundTransferStatus.ManageFundTransferStatusResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.ManageFundtransferStatusServiceRequest;
import com.barclays.bmg.service.response.ManageFundtransferStatusServiceResponse;

public class ManageFundtransferStatusRespAdptOperation extends
		AbstractResAdptOperation {
	 public ManageFundtransferStatusServiceResponse adaptResponse(WorkContext workContext, Object obj) {

		 ManageFundTransferStatusResponse response = (ManageFundTransferStatusResponse) obj;
			ManageFundtransferStatusServiceResponse manageFundtransferStatusServiceResponse = new ManageFundtransferStatusServiceResponse();
			String resCode = null;
			if(null != response && null != response.getResponseHeader() && null != response.getResponseHeader().getServiceResStatus()
					&& null !=response.getResponseHeader().getServiceResStatus().getServiceResCode())
				resCode = response.getResponseHeader().getServiceResStatus().getServiceResCode();

			DAOContext daoContext = (DAOContext) workContext;
			Object[] args = daoContext.getArguments();
			ManageFundtransferStatusServiceRequest request = (ManageFundtransferStatusServiceRequest) args[0];

			manageFundtransferStatusServiceResponse.setResCde(resCode);
			if(null != response && null != response.getResponseHeader() && null != response.getResponseHeader().getServiceResStatus()
					&& null !=response.getResponseHeader().getServiceResStatus().getServiceResCode())
				manageFundtransferStatusServiceResponse.setServiceResponseCode(response.getResponseHeader().getServiceResStatus()
				.getServiceResCode());

			if (response != null && checkResponseHeader(response.getResponseHeader())) {
			    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCode)) {
			    	manageFundtransferStatusServiceResponse.setResMsg(ResponseCodeConstants.SUCCESS_TXN_RESPONSE_MESSAGE);
			    } else {
			    	manageFundtransferStatusServiceResponse.setResMsg(ResponseCodeConstants.SUBMITTED_TXN_RESPONSE_MESSAGE);
			    }
			    manageFundtransferStatusServiceResponse.setTxnRefNo(request.getContext().getActivityRefNo());
			    manageFundtransferStatusServiceResponse.setTxnDt(new Date());
			    manageFundtransferStatusServiceResponse.setMobileNos(response.getMobileNumber());

			} else {
				manageFundtransferStatusServiceResponse.setSuccess(Boolean.FALSE);
			}

			return manageFundtransferStatusServiceResponse;
		    }

}