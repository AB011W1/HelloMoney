package com.barclays.bmg.operation.formvalidation;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.fundtransfer.own.OwnFundTransferRel1ValidateOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.own.OwnFundTransferRel1ValidateOperationResponse;

public class OwnFundTransferRel1ValidateOperation extends BMBCommonOperation{

	public OwnFundTransferRel1ValidateOperationResponse validateAccounts(OwnFundTransferRel1ValidateOperationRequest request){

		OwnFundTransferRel1ValidateOperationResponse response = new OwnFundTransferRel1ValidateOperationResponse();
		Context context = request.getContext();
		response.setContext(context);
		loadParameters(context,ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

		CustomerAccountDTO srcAcct = request.getSrcAcct();
		CustomerAccountDTO destAcct = request.getDestAcct();
		if(srcAcct!=null && destAcct!=null){
			if(!srcAcct.getCurrency().equalsIgnoreCase(destAcct.getCurrency())){
				response.setResCde(FundTransferResponseCodeConstants.PMT_COM_CCY_NOT_SAME);
				response.setSuccess(false);
			}
		}
		if(!response.isSuccess()){
			getMessage(response);
		}
		return response;
	}
}
