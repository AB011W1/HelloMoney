package com.barclays.bmg.dao.adapter.fundtransfer.domestic;

import java.util.Calendar;
import java.util.Map;

import com.barclays.bem.DomesticFundTransfer.DomesticFundTransfer;
import com.barclays.bem.IndividualBeneficiary.IndividualBeneficiary;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.utils.BMBCommonUtility;

public class DomesticFundTransferPayloadAdapter {

	public DomesticFundTransfer adaptPayload(WorkContext workContext){


		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		DomesticFundTransferServiceRequest domesticFTRequest =
									(DomesticFundTransferServiceRequest)args[0];

		DomesticFundTransfer domesticFundTransfer  = adaptPayLoad(domesticFTRequest);
		return domesticFundTransfer;
	}
	private DomesticFundTransfer adaptPayLoad(DomesticFundTransferServiceRequest domesticFTRequest){

		DomesticFundTransfer   dest = new DomesticFundTransfer ();
		CustomerAccountDTO sourceAcct = domesticFTRequest.getSourceAcct();
		BeneficiaryDTO beneficiaryDTO = domesticFTRequest.getBeneficiaryDTO();
        Calendar today = Calendar.getInstance();
        TransactionAccount fromAccount= new TransactionAccount();
        //FromAccount.AccountNumber
        fromAccount.setAccountNumber(sourceAcct.getAccountNumber());
        //FromAccount.Currency
        fromAccount.setAccountCurrencyCode(sourceAcct.getCurrency());
        dest.setDebitAccount(fromAccount);

        dest.setDebitAccountTypeCode(sourceAcct.getProductCode());
        if(beneficiaryDTO.getDestinationAccount()!=null) {
            dest.setCreditAccountTypeCode(beneficiaryDTO.getDestinationAccount().getProductCode());
        }
        //Debit Amount
        if(domesticFTRequest.getFxRateDTO()!=null && domesticFTRequest.getFxRateDTO().getEquivalentAmount()!=null){
        	  dest.setDebitAmount(domesticFTRequest.getFxRateDTO().getEquivalentAmount().doubleValue());
              //Add by Li Can[Rel3] start in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
              dest.setCreditAmount(domesticFTRequest.getTxnAmt().getAmount().doubleValue());
            //Add by Li Can[Rel3] end in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
        }else{
            dest.setDebitAmount(domesticFTRequest.getTxnAmt().getAmount().doubleValue());
            //Add by Li Can[Rel3] start in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
            dest.setCreditAmount(domesticFTRequest.getTxnAmt().getAmount().doubleValue());
          //Add by Li Can[Rel3] end in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
        }

        dest.setDebitValueDate(today);
        TransactionAccount beneficiaryAccount = new TransactionAccount();

        //destinationAccountNumber
      //Add start by Li, Can to padding zero before the account number. 28Feb, 2011
        String originalAccountNumber = beneficiaryDTO.getDestinationAccountNumber();
        String newAccountNumber = "";

        IndividualBeneficiary  beneficiary = new IndividualBeneficiary();
        //if(beneficiaryDTO != null){
        	beneficiary.setIBANFlag(beneficiaryDTO.isIbanFlag());
        //}else{
        	//beneficiary.setIBANFlag(false);
        //}
        dest.setBeneficiaryInfo(beneficiary);
		if(beneficiaryDTO.isIbanFlag()){
			newAccountNumber = originalAccountNumber;
		}else{
			Map<String,Object> sysMap = domesticFTRequest.getContext().getContextMap();
			String fullLenth = (String) sysMap.get(SystemParameterConstant.SYSPARAM_FULLLENGTH_DOMESTIC_ACCOUNTNUMBER);
			newAccountNumber = BMBCommonUtility.paddingZeros(originalAccountNumber,fullLenth);
		}
		//Add by Li, Can end; IBAN Change; Feb 22 2012;
        beneficiaryAccount.setAccountNumber(newAccountNumber);
        //Add end by Li, Can to padding zero before the account number. 28Feb, 2011
        //START: Add by Li, Can in order to pass customer full name in the request, which is in order to protect and inform customer from fraud attack Aug/22/2011
        dest.setInitiatingCustomerFullName(domesticFTRequest.getContext().getFullName());
        //END: Add by Li, Can in order to pass customer full name in the request, which is in order to protect and inform customer from fraud attack Aug/22/2011
        //destination currency
        beneficiaryAccount.setAccountCurrencyCode(beneficiaryDTO.getCurrency());
        //beneficiary account
        dest.setBeneficiaryAccount(beneficiaryAccount);

        //
        IndividualName name= new IndividualName();
        name.setFullName(beneficiaryDTO.getBeneficiaryName());
        dest.setBeneficiaryName(name);

        if(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(domesticFTRequest.getTxnTyp())){
        	dest.setTransactionTypeCode(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL);
        }else{
        	dest.setTransactionTypeCode(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL);
        }
        //payment remark
        dest.setNarrationText(domesticFTRequest.getTxnNot());

        dest.setIsServiceChargeApplicable(false);
        String businessId = domesticFTRequest.getContext().getBusinessId();
    	if (businessId != null && (businessId.equalsIgnoreCase(CommonConstants.MZBRB_BUSINESS_ID)||
    			businessId.equalsIgnoreCase(CommonConstants.KEBRB_BUSINESS_ID)|| businessId.equalsIgnoreCase(CommonConstants.TZNBC_BUSINESS_ID) )) {
    	    dest.setIsServiceChargeApplicable(true);
    	}
        return dest;
    }
}
