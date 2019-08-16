package com.barclays.bmg.ussd.dao.operation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerAccountListServiceResponse;

public class RetrieveCustomerAccountListResAdptOperation {

    public RetrieveCustomerAccountListServiceResponse adaptResponseForAccountList(WorkContext workContext, Object obj) {

	// DAOContext daoContext = (DAOContext) workContext;

	// Object[] args = daoContext.getArguments();

	// RetrieveCustomerAccountListServiceRequest request = (RetrieveCustomerAccountListServiceRequest) args[0];

	RetrieveCustomerAccountListServiceResponse response = new RetrieveCustomerAccountListServiceResponse();
	// SearchIndividualCustByAcctResponse bemResponse = (SearchIndividualCustByAcctResponse) obj;
	/*
	 * DeleteIndividualCustomerBeneficiaryResponse bemResponse = (DeleteIndividualCustomerBeneficiaryResponse) obj;
	 * 
	 * if (null != bemResponse.getTransactionResponseStatus()) response.setSuccess(true); if
	 * (checkResponseHeader(bemResponse.getResponseHeader())) { response.setSuccess(true); } else { response.setSuccess(false); }
	 */

	CASAAccountDTO casaAccount1 = new CASAAccountDTO();
	casaAccount1.setOverDraftLimit(new BigDecimal(536565));
	casaAccount1.setAccountStatus("active");
	casaAccount1.setAccountType("primary");
	casaAccount1.setAccountNickName("account1");
	casaAccount1.setOperativeFlag(true);

	/*
	 * CASAAccountDTO casaAccount2=new CASAAccountDTO(); casaAccount2.setOverDraftLimit(new BigDecimal(554645));
	 * casaAccount2.setAccountStatus("active"); casaAccount2.setAccountType("savings"); casaAccount2.setAccountNickName("account2");
	 * casaAccount2.setOperativeFlag(false);
	 * 
	 * CASAAccountDTO casaAccount3=new CASAAccountDTO(); casaAccount3.setOverDraftLimit(new BigDecimal(454857));
	 * casaAccount3.setAccountStatus("active"); casaAccount3.setAccountType("current"); casaAccount3.setAccountNickName("account3");
	 * casaAccount3.setOperativeFlag(false);
	 */

	List<CASAAccountDTO> accountList = new ArrayList<CASAAccountDTO>();
	accountList.add(casaAccount1);
	// accountList.add(casaAccount2);
	// accountList.add(casaAccount3);
	/*
	 * CustomerDetailsDTO customerDTO=new CustomerDetailsDTO(); customerDTO.setCasaAccountList(accountList);
	 */

	/*
	 * CustomerAccountDTO custDTO=new CustomerAccountDTO(); custDTO.setAccountNickName("Jim S"); custDTO.setAccountStatus("active");
	 * custDTO.setAccountType("current"); custDTO.setAvailableBalance(new BigDecimal(321321)); custDTO.setCustomerId(request.getCustomerId());
	 * custDTO.setAddressLine1("JHB 112");
	 */
	/*
	 * IndividualCustomerList individualCustomerList=bemResponse.getIndividualCustomerList(); List
	 * individualCustomerBasic=individualCustomerList.getIndividualCustomerBasic(); String scvid=(String)individualCustomerBasic.get(0);
	 */
	/*
	 * response.setRegistered(true); response.setCustomerDetails(customerDTO);
	 */
	response.setSuccess(true);
	return response;
    }

}
