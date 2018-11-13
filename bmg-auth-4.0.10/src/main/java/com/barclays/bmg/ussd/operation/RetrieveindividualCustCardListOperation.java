package com.barclays.bmg.ussd.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.DebitCardDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.service.request.RetrieveindividualCustCardListServiceRequest;
import com.barclays.bmg.service.response.RetrieveindividualCustCardListServiceResponse;
import com.barclays.bmg.ussd.auth.operation.request.RetrieveindividualCustCardListOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.RetrieveindividualCustCardListOperationResponse;
import com.barclays.bmg.ussd.service.RetrieveindividualCustCardListService;

public class RetrieveindividualCustCardListOperation extends BMBCommonOperation {
	private RetrieveindividualCustCardListService retrieveindividualCustCardListService;
	private static final Logger LOGGER = Logger.getLogger(RetrieveindividualCustCardListOperation.class);
	/**
     * Get all debit cards for user
     *
     * @param request
     * @param response
     * @return
     */
    public RetrieveindividualCustCardListOperationResponse retrieveCustCardList(RetrieveindividualCustCardListOperationRequest request) {
    	  LOGGER.debug(" Entry RetrieveindividualCustCardListOperation retrieveCustCardList");
    	List<DebitCardDTO>  debitCardDTOList=new ArrayList<DebitCardDTO>();
    	Context context = request.getContext();
    	loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
    	String accountNo;
    	RetrieveindividualCustCardListOperationResponse retrieveindividualCustCardListOperationResponse=new RetrieveindividualCustCardListOperationResponse();
    	retrieveindividualCustCardListOperationResponse.setContext(context);
    	RetrieveindividualCustCardListServiceRequest retrieveindividualCustCardListServiceRequest = new RetrieveindividualCustCardListServiceRequest();
    	//Added as we have to append branch code in case of eBox countries to fetch Card List
    	retrieveindividualCustCardListServiceRequest.setContext(request.getContext());
    	if (branchCodeCountryList.contains(context.getBusinessId())) {
			accountNo = request.getBranchCode() + request.getAccountNo();
		} else
			accountNo = request.getAccountNo();

    	retrieveindividualCustCardListServiceRequest.setAccountNo(accountNo);
	RetrieveindividualCustCardListServiceResponse retrieveindividualCustCardListServiceResponse = retrieveindividualCustCardListService.retrieveCustCardList(retrieveindividualCustCardListServiceRequest);

	if (retrieveindividualCustCardListServiceResponse != null && retrieveindividualCustCardListServiceResponse.getDebitCardDTOList() != null
		&& retrieveindividualCustCardListServiceResponse.getDebitCardDTOList().size() !=0) {
		debitCardDTOList=retrieveindividualCustCardListServiceResponse.getDebitCardDTOList();
		retrieveindividualCustCardListOperationResponse.setDebitCardDTOList(debitCardDTOList);
		retrieveindividualCustCardListOperationResponse.setSuccess(true);
	}
	   else {

		   retrieveindividualCustCardListOperationResponse.setSuccess(false);
	   	   retrieveindividualCustCardListOperationResponse.setResMsg(retrieveindividualCustCardListServiceResponse.getResMsg());
		   retrieveindividualCustCardListOperationResponse.setResCde(retrieveindividualCustCardListServiceResponse.getResCde());
	   }

	return retrieveindividualCustCardListOperationResponse;
    }
	public RetrieveindividualCustCardListService getRetrieveindividualCustCardListService() {
		return retrieveindividualCustCardListService;
	}
	public void setRetrieveindividualCustCardListService(
			RetrieveindividualCustCardListService retrieveindividualCustCardListService) {
		this.retrieveindividualCustCardListService = retrieveindividualCustCardListService;
	}

}
