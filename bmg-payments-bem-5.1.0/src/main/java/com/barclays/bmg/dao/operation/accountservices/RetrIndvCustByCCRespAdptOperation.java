package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveIndividualCustByCCNumber.FinancialTransactionCardDetails;
import com.barclays.bem.RetrieveIndividualCustByCCNumber.RetrieveIndividualCustomerByCCNumberResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.response.RetreiveIndvCustInfoServiceResponse;

public class RetrIndvCustByCCRespAdptOperation extends AbstractResAdptOperationAcct {

    public RetreiveIndvCustInfoServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	RetreiveIndvCustInfoServiceResponse response = new RetreiveIndvCustInfoServiceResponse();

	RetrieveIndividualCustomerByCCNumberResponse bemResponse = (RetrieveIndividualCustomerByCCNumberResponse) obj;

	CustomerDTO customerDTO = new CustomerDTO();
	if (checkResponseHeader(bemResponse.getResponseHeader())) {

	    FinancialTransactionCardDetails cardDetails = bemResponse.getIndividualCustomerDetailsResponse().getFinancialTransactionCardDetails();
	    if (cardDetails != null) {
		cardDetails.getCreditCardDetailsInfo().getCardBlockCode();
		if (cardDetails.getCreditCardDetailsInfo() != null && cardDetails.getCreditCardDetailsInfo().getCardEmbossedName() != null) {

		    customerDTO.setFullName(cardDetails.getCreditCardDetailsInfo().getCardEmbossedName());
		} else {
		    // -- Added else condition bcoz CardEmbossedName field is
		    // renamed as CardHolderName in orchard

		    customerDTO.setFullName(cardDetails.getCreditCardDetailsInfo().getCardHolderName());
		}

		CreditCardAccountDTO creditCardAccountDTO = new CreditCardAccountDTO();
		customerDTO.setCreditCardAccountDTO(creditCardAccountDTO);
		creditCardAccountDTO.setAccountNumber(cardDetails.getCreditCardAccountNumber());
		creditCardAccountDTO.setAccountStatus(cardDetails.getAccountLifecycleStatusCode());
		CreditCardDTO primary = new CreditCardDTO();
		creditCardAccountDTO.setPrimary(primary);
		primary.setCardNumber(cardDetails.getCreditCardDetailsInfo().getCreditCardNumber());
		primary.setCardStatus(cardDetails.getCreditCardDetailsInfo().getCardLifecycleStatusCode());
		primary.setCreditCardOwnershipTypeCode(cardDetails.getCreditCardDetailsInfo().getCreditCardOwnershipTypeCode());

		response.setCustDTO(customerDTO);
		response.setSuccess(true);
		response.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);
	    } else {
		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.BCD_INVALID_CARD_NUMBER);
	    }
	} else {
	    response.setSuccess(false);
	}

	return response;
    }

    private String checkServiceResponseHeader(BEMResHeader resHeader) throws Exception {

	String returnCode = "";

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();
	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode) || AccountErrorCodeConstant.PARTIAL_SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;
	    }

	    else if (errorList != null) {
		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {

		    if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EXIST)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EMPTY_PRIME)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO;
		    } else {
			throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		    }
		}
	    }
	}

	return returnCode;
    }
}
