package com.barclays.bmg.operation.accountdetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.dto.CreditCardStmtPointsInfoDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.CreditCardDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CreditCardDetailsOperationResponse;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardUnbilledTransServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardUnbilledTransServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;

/**
 * @author BMB Team
 *
 */

public class CreditCardDetailsOperation extends AbstractCreditCardOperation {

    private CreditCardDetailsService creditCardDetailsService;
    private AllAccountService allAccountService;
    private ProductEligibilityService productEligibilityService;

    public ProductEligibilityService getProductEligibilityService() {
	return productEligibilityService;
    }

    public void setProductEligibilityService(ProductEligibilityService productEligibilityService) {
	this.productEligibilityService = productEligibilityService;
    }

    /**
     * 1. Retrieve the credit card account details. 2. Retrieve the credit card transactions. get the reward information. 3. Retrieve the credit card
     * un-billed transactions.
     *
     * @param request
     * @return
     *
     */

    public CreditCardDetailsOperationResponse retrieveCreditCardDetails(CreditCardDetailsOperationRequest request) {

	Context context = request.getContext();

	CreditCardDetailsOperationResponse ccDetailsOperationResponse = new CreditCardDetailsOperationResponse();
	boolean respSuccessFlg = false;
	String respCode = "";
	String orgCode = "";

	super.loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

	// Need to call all account service request for getting updated card
	// desc from PRIME
	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(request.getContext());
	Map<String, String> ccdMapList = new HashMap<String, String>();
	CreditCardAccountDTO creditCardAccountDTO = null;// ORCHARD CHANGES
	CreditCardTransactionHistoryDTO primaryCardHistory = null;
	List<CreditCardTransactionHistoryDTO> replacementCardGroupedHistoryList = null;
	List<CreditCardTransactionHistoryDTO> supplimentryCardGroupedHistoryList = null;
	CreditCardStmtPointsInfoDTO rewardInfo = null;

	// ORCHARD CHANGES START
	String reqAccNo = request.getAccountNo();

	// Retrive all account List from BEM
	AllAccountServiceResponse allAccountServiceResp = allAccountService.retrieveAllAccount(allAccountServiceRequest);

	List<? extends CustomerAccountDTO> customerAccountDTOList = new ArrayList<CustomerAccountDTO>();
	if(null != allAccountServiceResp)
		customerAccountDTOList = allAccountServiceResp.getAccountList();
	if (null != allAccountServiceResp && allAccountServiceResp.isSuccess()) {
	    customerAccountDTOList = allAccountServiceResp.getAccountList();
	}

	if (null != customerAccountDTOList && customerAccountDTOList.size() > 0) {

	    List<CustomerAccountDTO> ccdAccountList = new ArrayList<CustomerAccountDTO>();

	    for (CustomerAccountDTO customerAccountDTO : customerAccountDTOList) {

		if (customerAccountDTO instanceof CreditCardAccountDTO) {

		    String accNo = customerAccountDTO.getAccountNumber();

		    if (accNo.equalsIgnoreCase(reqAccNo)) {

			creditCardAccountDTO = (CreditCardAccountDTO) customerAccountDTO;

			if (creditCardAccountDTO.getPrimary() != null) {

			    orgCode = creditCardAccountDTO.getPrimary().getCreditCardOrgCode();

			}

		    }
		    ccdAccountList.add(customerAccountDTO);
		}
	    }

	    if (ccdAccountList != null && !ccdAccountList.isEmpty()) {

		for (CustomerAccountDTO customerDTO : ccdAccountList) {
		    ccdMapList.put(customerDTO.getAccountNumber(), customerDTO.getProductCode());
		}
	    }
	}

	if (allAccountServiceResp != null && customerAccountDTOList != null && customerAccountDTOList.size() > 0) {
	    respSuccessFlg = allAccountServiceResp.isSuccess();
	    respCode = allAccountServiceResp.getResCde();

	    if (respSuccessFlg) {

		/*List<CreditCardDTO> addSupplimentrylist = null;
		if (addSupplimentrylist != null) {
		    for (int i = 0; i < addSupplimentrylist.size(); i++) {
			creditCardAccountDTO.addSupplementary(addSupplimentrylist.get(i));
		    }
		}*/

		// Credit Card Unbilled Trans
		CreditCardUnbilledTransServiceRequest ccUnbilledTransServiceReq = new CreditCardUnbilledTransServiceRequest();
		ccUnbilledTransServiceReq.setContext(context);
		ccUnbilledTransServiceReq.setAccountNo(reqAccNo);

		CreditCardUnbilledTransServiceResponse ccUnbilledTransServiceResp = creditCardDetailsService
			.retrieveCreditCardUnbilledTrans(ccUnbilledTransServiceReq);

		if (ccUnbilledTransServiceResp != null) {
		    respSuccessFlg = ccUnbilledTransServiceResp.isSuccess();
		    respCode = ccUnbilledTransServiceResp.getResCde();
		    if (respSuccessFlg && creditCardAccountDTO != null) {
			List<CreditCardActivityDTO> activityList = null;

			if (ccUnbilledTransServiceResp != null && ccUnbilledTransServiceResp.getCreditCardActivityDTOList() != null) {

			    activityList = ccUnbilledTransServiceResp.getCreditCardActivityDTOList();
			    if (activityList != null) {

				primaryCardHistory = getPrimaryCardHistoryList(creditCardAccountDTO, activityList);
				replacementCardGroupedHistoryList = getReplacementCardGroupedHistoryList(creditCardAccountDTO, activityList);
				supplimentryCardGroupedHistoryList = getSupplimentryCardGroupedHistoryList(creditCardAccountDTO, activityList);

			    }

			} else {
			    primaryCardHistory = getPrimaryCardHistoryListWithoutActivityList(creditCardAccountDTO);
			}
			ccDetailsOperationResponse.setSuccess(true);
		    } else {
			respSuccessFlg = false;
		    }
		} else {
		    primaryCardHistory = getPrimaryCardHistoryListWithoutActivityList(creditCardAccountDTO);

		}
	    }

	}

	// response
	ccDetailsOperationResponse.setSuccess(respSuccessFlg);
	ccDetailsOperationResponse.setResCde(respCode);
	ccDetailsOperationResponse.setContext(context);
	ccDetailsOperationResponse.setCreditCardAccountDTO(creditCardAccountDTO);
	ccDetailsOperationResponse.setCreditCardStmtPointsInfo(rewardInfo);
	ccDetailsOperationResponse.setPrimaryCardHistory(primaryCardHistory);
	ccDetailsOperationResponse.setReplacementCardGroupedHistoryList(replacementCardGroupedHistoryList);
	ccDetailsOperationResponse.setSupplimentryCardGroupedHistoryList(supplimentryCardGroupedHistoryList);
	ccDetailsOperationResponse.setResMsg("");
	if (!respSuccessFlg) {
	    ccDetailsOperationResponse.setResCde(AccountServiceResponseCodeConstant.ACT_ACTDETAIL_NOACTAVAILABLE);
	}

	// Added to update Card desc from all account service request into card
	// details page
	if (ccdMapList != null && !ccdMapList.isEmpty() && ccDetailsOperationResponse.getCreditCardAccountDTO() != null) {

	    ccDetailsOperationResponse.getCreditCardAccountDTO().setProductCode(
		    ccdMapList.get(ccDetailsOperationResponse.getCreditCardAccountDTO().getAccountNumber()));

	}

	if (!ccDetailsOperationResponse.isSuccess()) {
	    super.getMessage(ccDetailsOperationResponse);
	}

	return ccDetailsOperationResponse;
    }

    public void setCreditCardDetailsService(CreditCardDetailsService creditCardDetailsService) {
	this.creditCardDetailsService = creditCardDetailsService;
    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    /**
     * Filter the Account list by using Product eligibility service
     *
     * @param accountList
     * @param request
     * @param response
     * @return
     */
    private List<CustomerAccountDTO> filterAccountList(List<? extends CustomerAccountDTO> accountList, CreditCardDetailsOperationRequest request) {
	// TODO Auto-generated method stub
	ProductEligibilityServiceRequest productEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	productEligibilityServiceRequest.setContext(request.getContext());
	productEligibilityServiceRequest.setActivityId(request.getContext().getActivityId());
	productEligibilityServiceRequest.setRoleCategoryCode(request.getContext().getSystemId());
	productEligibilityServiceRequest.setAccountList(accountList);

	// filter account list by product eligibility
	ProductEligibilityListServiceResponse productEligListServiceResp = productEligibilityService
		.getEligibleAccounts(productEligibilityServiceRequest);

	List<CustomerAccountDTO> prodEligSerResList = null;

	if (productEligListServiceResp.getAccountList() != null) {
	    prodEligSerResList = (List<CustomerAccountDTO>) productEligListServiceResp.getAccountList();
	}

	return prodEligSerResList;

	/**
	 * The original code as below: ProductEligibilityListServiceResponse productEligListServiceResp = productEligibilityService
	 * .getEligibleAccounts(productEligibilityServiceRequest); if (productEligListServiceResp.getAccountList() != null) {
	 * productEligListServiceResp.getAccountList().size()); }
	 *
	 * return (List<CustomerAccountDTO>) productEligListServiceResp .getAccountList();
	 */

    }

}
