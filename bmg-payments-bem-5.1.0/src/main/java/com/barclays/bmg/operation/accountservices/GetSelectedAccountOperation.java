package com.barclays.bmg.operation.accountservices;

import java.util.List;

import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.CASADetailsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;

public class GetSelectedAccountOperation extends RetrieveAccountListOperation {

	/**
	 * Checks if account is eligible as Source account for activity Id in context.
	 * Return the selected source account.
	 * @param request
	 * @return
	 */

	private CASADetailsOperation casaDetailsOperation;

	public CASADetailsOperation getCasaDetailsOperation() {
		return casaDetailsOperation;
	}

	public void setCasaDetailsOperation(CASADetailsOperation casaDetailsOperation) {
		this.casaDetailsOperation = casaDetailsOperation;
	}

	public GetSelectedAccountOperationResponse getSelectedSourceAccount(GetSelectedAccountOperationRequest request){
		GetSelectedAccountOperationResponse response = new GetSelectedAccountOperationResponse();
		response.setContext(request.getContext());
		RetrieveAcctListOperationRequest allAcctRequest = new RetrieveAcctListOperationRequest();
		allAcctRequest.setContext(request.getContext());
		RetrieveAcctListOperationResponse allAcctResponse = getSourceAcctList(allAcctRequest);

		/**
		 * Added casa account detail operation to get balances
		 */

/*		CASADetailsOperationRequest casaDetailsOperationRequest = new CASADetailsOperationRequest();

		casaDetailsOperationRequest.setAccountNo(request.getAcctNumber());
		casaDetailsOperationRequest.setContext(request.getContext());
		CASADetailsOperationResponse casaDetailsOperationResponse =
									casaDetailsOperation.retrieveCASADetails(casaDetailsOperationRequest);



		response.setSelectedAcct(casaDetailsOperationResponse.getCasaAccountDTO());

*/
		if(allAcctResponse.isSuccess()){
			List<? extends CustomerAccountDTO> srcAcct = allAcctResponse.getAcctList();
			CustomerAccountDTO selectedAcct = getSelectedCustAccount(srcAcct, request.getAcctNumber());
			if(selectedAcct!=null){
				response.setSelectedAcct(selectedAcct);
			}else{
				response.setSuccess(false);
				response.setResCde(FundTransferResponseCodeConstants.INVALID_SRC_ACCT);
			}

		}else{
			response.setSuccess(false);
			response.setResCde(allAcctResponse.getResCde());
		}

		if(!response.isSuccess()){
			getMessage(response);
		}

		return response;
	}

public GetSelectedAccountOperationResponse getSelectedCreditCardAccount(GetSelectedAccountOperationRequest request){
		GetSelectedAccountOperationResponse response = new GetSelectedAccountOperationResponse();
		response.setContext(request.getContext());
		AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
		allAccountServiceRequest.setContext(request.getContext());
		allAccountServiceRequest.setAccountType("CC");
		RetrieveAcctListOperationResponse allAcctResponse = getCreditCardAcctList(allAccountServiceRequest);

		if(allAcctResponse.isSuccess()){
			List<? extends CustomerAccountDTO> CreditCardList = allAcctResponse.getAcctList();
			CustomerAccountDTO selectedAcct = getSelectedCustAccount(CreditCardList, request.getAcctNumber());
			if(selectedAcct!=null){
				response.setSelectedAcct(selectedAcct);
			}else{
				response.setSuccess(false);
				response.setResCde(FundTransferResponseCodeConstants.INVALID_SRC_ACCT);
			}

		}else{
			response.setSuccess(false);
			response.setResCde(allAcctResponse.getResCde());
		}

		if(!response.isSuccess()){
			getMessage(response);
		}

		return response;
	}
	/**
	 * Checks if account is eligible as Destination account for activity Id in context.
	 * Return the selected destination account.
	 * @param request
	 * @return
	 */
	public GetSelectedAccountOperationResponse getSelectedDestinationAccount(GetSelectedAccountOperationRequest request){
		GetSelectedAccountOperationResponse response = new GetSelectedAccountOperationResponse();
		response.setContext(request.getContext());
		RetrieveAcctListOperationRequest allAcctRequest = new RetrieveAcctListOperationRequest();
		allAcctRequest.setContext(request.getContext());
		RetrieveAcctListOperationResponse allAcctResponse = getDestAcctList(allAcctRequest);

		if(allAcctResponse.isSuccess()){
			List<? extends CustomerAccountDTO> destAccts = allAcctResponse.getAcctList();
			CustomerAccountDTO selectedAcct = getSelectedCustAccount(destAccts, request.getAcctNumber());
			if(selectedAcct!=null){
				response.setSelectedAcct(selectedAcct);
			}else{
				response.setSuccess(false);
				response.setResCde(FundTransferResponseCodeConstants.INVALID_DEST_ACCT);
			}

		}else{
			response.setSuccess(false);
			response.setResCde(allAcctResponse.getResCde());
		}

		if(!response.isSuccess()){
			getMessage(response);
		}

		return response;
	}

	/**
	 * Get account from account list using account number.
	 *
	 * @param allAccounts
	 * @param acctNo
	 * @return
	 */
	private CustomerAccountDTO getSelectedCustAccount(List<? extends CustomerAccountDTO> allAccounts, String acctNo){
		CustomerAccountDTO selectedAccount = null;

    	if(allAccounts != null && acctNo != null && !allAccounts.isEmpty()){
    		for(CustomerAccountDTO account : allAccounts){
    			if(acctNo.equals(account.getAccountNumber())){
    				selectedAccount =  account;
    				break;
    			}
    		}
    	}
    	return selectedAccount;
	}
}
