package com.barclays.bmg.operation.accountdetails;

import java.util.List;

import com.barclays.bmg.constants.ApplyTDResponseCodeConstant;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.ApplyTDOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDOperationResponse;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;

/**
 * @author E20041929
 * 
 */
public class ApplyTDInitOperation extends TDAccountDetailsOperation {

    public ApplyTDOperationResponse getSourceAccountList(ApplyTDOperationRequest request) {

	List<? extends CustomerAccountDTO> allAccountList = getAllAccountList(request);
	ApplyTDOperationResponse response = new ApplyTDOperationResponse();
	response.setContext(request.getContext());
	List<? extends CustomerAccountDTO> srcActLst = null;

	if (allAccountList != null) {
	    srcActLst = getSourceAccountsList(allAccountList, request);
	}
	if (srcActLst != null) {
	    response.setSuccess(true);
	    response.setResCde(ApplyTDResponseCodeConstant.SOURCE_ACCOUNT_LIST_RETURNED);
	    response.setResMsg(ApplyTDResponseCodeConstant.APPLY_TD_INIT_SUCCESS);
	    response.setSourceAccList(srcActLst);
	} else {
	    response.setSuccess(false);
	    response.setResCde(ApplyTDResponseCodeConstant.SOURCE_ACCOUNT_LIST_NOT_RETURNED);
	    response.setResMsg(ApplyTDResponseCodeConstant.APPLY_TD_INIT_NOT_SUCCESS);
	}

	return response;
    }

    /**
     * Retrieves All Accounts
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<? extends CustomerAccountDTO> getAllAccountList(ApplyTDOperationRequest request) {
	// TODO Auto-generated method stub
	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(request.getContext());

	AllAccountServiceResponse allAccountsServiceResponse = super.getAllAccountService().retrieveAllAccount(allAccountServiceRequest);

	List<CustomerAccountDTO> accountList = (List<CustomerAccountDTO>) allAccountsServiceResponse.getAccountList();

	return accountList;
    }

}
