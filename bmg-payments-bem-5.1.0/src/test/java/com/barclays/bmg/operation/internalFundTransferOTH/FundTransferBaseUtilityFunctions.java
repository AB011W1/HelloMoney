package com.barclays.bmg.operation.internalFundTransferOTH;


import com.barclays.bmg.operation.BaseClass;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;
import com.barclays.bmg.service.response.TransactionAbilityResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;
import com.barclays.bmg.service.response.UpgradeTransactionLimitServiceResponse;

public class FundTransferBaseUtilityFunctions extends BaseClass {

	public FundTransferBaseUtilityFunctions() {
		super();
	}
	protected DomesticFundTransferServiceResponse getDomesticFundTransferServiceResponseMock() {
		   DomesticFundTransferServiceResponse response = new DomesticFundTransferServiceResponse();
			response.setSuccess(true);
			response.setTrnRef("101010");
			response.setTrnDate(new Date("10/10/2012"));
			response.setTxnMsg("succeded");

		return response;
	}

	  protected UpgradeTransactionLimitServiceResponse getUpgradeTransactionLimitServiceResponseMock() {
		  UpgradeTransactionLimitServiceResponse response = new UpgradeTransactionLimitServiceResponse();
			response.setSuccess(true);
			response.setTxnTyp("daily");



		return response;
	}

	  protected TransactionAbilityResponse getTransactionAbilityServiceResponseMock() {
		  TransactionAbilityResponse response = new TransactionAbilityResponse();
		  response.setSuccess(true);
			response.setTransactionable(true);
			response.setNextBusinessDate(new Date("10/10/2012"));


		return response;
	}
	  protected TransactionAbilityResponse getTransactionAbilityServiceResponseMockFor() {
		  TransactionAbilityResponse response = new TransactionAbilityResponse();
		  response.setSuccess(false);
			response.setTransactionable(false);
			response.setNextBusinessDate(null);


		return response;
	}
	  protected DomesticFundTransferServiceResponse getDomesticFundTransferServiceResponseMockForWrongUser() {
		   DomesticFundTransferServiceResponse response = new DomesticFundTransferServiceResponse();
			response.setSuccess(false);
			response.setTrnRef(null);
			response.setTrnDate(null);
			response.setTxnMsg("failure");

		return response;
	}

	protected void settransactionDTO(DomesticFundTransferExecuteOperationRequest request) {
			TransactionDTO transactionDTO = new TransactionDTO();
			transactionDTO.setBeneficiaryDTO(new BeneficiaryDTO());
			transactionDTO.setTxnAmt(new Amount("INR",new BigDecimal(300)));
			transactionDTO.setScndLvlauthReq(false);
         request.setTransactionDTO(transactionDTO);
	}
   protected TransactionLimitServiceResponse getTransactionLimitServiceResponseMockForError() {
		  TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
			response.setSuccess(false);
			response.setAuthRequired(true);
			response.setTransactionLimit(null);
			response.setAValidDailyLimit(null);

		return response;
	}
	  protected CASADetailsServiceResponse getCASADetailsServiceResponseMock() {
		  CASADetailsServiceResponse response = new CASADetailsServiceResponse();
			response.setSuccess(true);
 			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName("PUNE_INDIA");
			CustomerAccountDTO.setAccountStatus("ENABLED");
			CustomerAccountDTO.setAccountNumber("9000000008");
			CustomerAccountDTO.setAsset(new BigDecimal(300));
			response.setCasaAccountDTO(CustomerAccountDTO);
			response.setTxnTyp("FALTU");

		return response;
	}
	  protected CASADetailsServiceResponse getCASADetailsServiceResponseMockForError() {
		  CASADetailsServiceResponse response = new CASADetailsServiceResponse();
			response.setSuccess(false);
 			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName(null);
			CustomerAccountDTO.setAccountStatus(null);
			CustomerAccountDTO.setAccountNumber(null);
			CustomerAccountDTO.setAsset(null);
			response.setCasaAccountDTO(CustomerAccountDTO);
			response.setTxnTyp("FALTU");

		return response;
	}

	  protected AllAccountServiceResponse getRetrieveAcctListOperationResponseeMock() {
		  AllAccountServiceResponse response = new AllAccountServiceResponse();
			response.setSuccess(true);
			CASAAccountDTO customerAccountDTO = new CASAAccountDTO();
			customerAccountDTO.setAccountNickName("PUNE_INDIA");
			customerAccountDTO.setAccountStatus("ENABLED");
			customerAccountDTO.setAccountNumber("9000000008");
			customerAccountDTO.setAsset(new BigDecimal(300));
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();

			acctList.add(customerAccountDTO);
			response.setAccountList(acctList);


		return response;
	}
	  protected AllAccountServiceResponse getRetrieveAcctListOperationResponseeMockForError() {
		  AllAccountServiceResponse response = new AllAccountServiceResponse();
			response.setSuccess(false);
			CASAAccountDTO customerAccountDTO = new CASAAccountDTO();
			customerAccountDTO.setAccountNickName(null);
			customerAccountDTO.setAccountStatus(null);
			customerAccountDTO.setAccountNumber(null);
			customerAccountDTO.setAsset(null);
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();

			acctList.add(customerAccountDTO);
			response.setAccountList(acctList);


		return response;
	}
	  protected ProductEligibilityListServiceResponse getProductEligibilityOperationResponseMock() {
		  ProductEligibilityListServiceResponse response = new ProductEligibilityListServiceResponse();
			response.setSuccess(true);
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();
			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName("PUNE_INDIA");
			CustomerAccountDTO.setAccountStatus("ENABLED");
			CustomerAccountDTO.setAccountNumber("9000000008");
			CustomerAccountDTO.setAsset(new BigDecimal(300));
			acctList.add(CustomerAccountDTO);

			response.setAccountList(acctList);


		return response;
	}
	  protected ProductEligibilityListServiceResponse getProductEligibilityOperationResponseMockForError() {
		  ProductEligibilityListServiceResponse response = new ProductEligibilityListServiceResponse();
			response.setSuccess(false);
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();
			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName(null);
			CustomerAccountDTO.setAccountStatus(null);
			CustomerAccountDTO.setAccountNumber(null);
			CustomerAccountDTO.setAsset(null);
			acctList.add(CustomerAccountDTO);

			response.setAccountList(acctList);


		return response;
	}
	  protected AllAccountServiceResponse getRetrieveAllAcctListServiceResponseeMockForErro() {
		  AllAccountServiceResponse response = new AllAccountServiceResponse();
			response.setSuccess(true);
			List<? extends CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();
			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName(null);
			CustomerAccountDTO.setAccountStatus("ENABLED");
			CustomerAccountDTO.setAccountNumber(null);
			CustomerAccountDTO.setAsset(null);
			response.setAccountList(acctList);


		return response;
	}

	  protected TransactionLimitServiceResponse getTransactionLimitServiceResponseMock() {
		  TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
			response.setSuccess(true);
			response.setAuthRequired(true);
			response.setTransactionLimit(new BigDecimal(300));
			response.setAValidDailyLimit(new BigDecimal(300));


		return response;
	}

}