package com.barclays.bmg.operation.pesalink;

import java.util.List;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.fundtransfer.own.ValidateAccountListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.own.ValidateAccountListOperationResponse;

public class KitsValidateAccountListOperation extends BMBCommonOperation {

	public ValidateAccountListOperationResponse validate(ValidateAccountListOperationRequest request){

		ValidateAccountListOperationResponse response = new ValidateAccountListOperationResponse();
		response.setContext(request.getContext());
//    	if(srcAcctLst!=null && srcAcctLst.size()==1){
//    		if(destAcctLst!=null && destAcctLst.size()==1){
//    			response.setResCde(FundTransferResponseCodeConstants.OWN_FT_ONLY_ONE_ACCOUNT);
//    			response.setSuccess(false);
//    		}
//    	}
    	if(!response.isSuccess()){
    		getMessage(response);
    	}
    	return response;
	}
}
