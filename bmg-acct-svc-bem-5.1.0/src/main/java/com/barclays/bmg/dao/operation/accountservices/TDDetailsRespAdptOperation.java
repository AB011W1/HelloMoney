package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveTimeDepositDetails.RetrieveTimeDepositDetailsResponse;
import com.barclays.bem.RetrieveTimeDepositDetails.TimeDepositDetails;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.Renewal;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.dto.TermDepositDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.accountdetails.request.TDDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.TDDetailsServiceResponse;
import com.barclays.bmg.type.RateAmount;
import com.barclays.bmg.utils.ConvertUtils;

public class TDDetailsRespAdptOperation {

    public TDDetailsServiceResponse adaptResponse(WorkContext workContext, Object obj) throws Exception {

	TDDetailsServiceResponse response = new TDDetailsServiceResponse();
	RetrieveTimeDepositDetailsResponse bemResponse = (RetrieveTimeDepositDetailsResponse) obj;
	String resCde = checkServiceResponseHeader(bemResponse.getResponseHeader());

	if (resCde.equals(ErrorCodeConstant.SUCCESS_CODE)) {
	    TdAccountDTO tdAccountDTO = mapToTDAccountDTO(bemResponse.getTimeDepositDetails());

	    DAOContext daoContext = (DAOContext) workContext;

	    Object[] args = daoContext.getArguments();

	    TDDetailsServiceRequest tdDetailsServiceRequest = (TDDetailsServiceRequest) args[0];

	    if (tdAccountDTO != null && tdDetailsServiceRequest.getCoreTDAcctDTO() != null) {
		TdAccountDTO coreTdAccountDTO = tdDetailsServiceRequest.getCoreTDAcctDTO();
		tdAccountDTO.setAccountNumber(coreTdAccountDTO.getAccountNumber());
		tdAccountDTO.setCurrency(coreTdAccountDTO.getCurrency());
		tdAccountDTO.getDepositDTO().setDepositNumber((coreTdAccountDTO).getDepositDTO().getDepositNumber());
		tdAccountDTO.getDepositDTO().setOriginalDepositNumber((coreTdAccountDTO).getDepositDTO().getOriginalDepositNumber());
		tdAccountDTO.setProductCode(coreTdAccountDTO.getProductCode());
		tdAccountDTO.setDesc(coreTdAccountDTO.getDesc());

		response.setTdAccountDTO(tdAccountDTO);
	    }
	} else {
	    response.setSuccess(false);
	}

	response.setResCde(resCde);
	return response;
    }

    public TdAccountDTO mapToTDAccountDTO(TimeDepositDetails source) {

	TdAccountDTO dest = new TdAccountDTO();

	TermDepositDTO termDepositDTO = new TermDepositDTO();
	// dest.setCurrency(source.getTimeDepositSummary().get);

	// No Mapping from FCR
	if (source.getTimeDepositSummary() != null) {
	    termDepositDTO.setMaturityDate(ConvertUtils.convertDate(source.getTimeDepositSummary().getMaturityDate()));
	    termDepositDTO.setValueDate(ConvertUtils.convertDate(source.getTimeDepositSummary().getValueDate()));
	    termDepositDTO.setInterestRate(new RateAmount(ConvertUtils.convertInterestRateAmount(source.getTimeDepositSummary().getInterestRate())));

	    termDepositDTO.setTenureTerm(ConvertUtils.convertTenure(source.getTimeDepositSummary().getTenorPeriod()));
	    termDepositDTO.setTdPrincipalBalance(ConvertUtils.convertAmount(source.getTimeDepositSummary().getPrincipalBalanceAmount()));
	    termDepositDTO.setMaturityAmount(ConvertUtils.convertAmount(source.getTimeDepositSummary().getMaturityAmount()));
	    // No Mapping from FCR

	    termDepositDTO.setYearToDateTax(ConvertUtils.convertAmount(source.getTimeDepositSummary().getYearToDateWithholdingTax()));
	    termDepositDTO.setLienAmount(ConvertUtils.convertAmount(source.getTimeDepositSummary().getLienAmount()));

	    // No Mapping from FCR
	    termDepositDTO.setMaturityInstruction(source.getTimeDepositSummary().getMaturityInstruction());
	    Renewal renewal = new Renewal();
	    // renewal.setRenewalType(source.getTimeDepositSummary().getMaturityOptionCode());
	    renewal.setPrincipalRenewalType(source.getTimeDepositSummary().getPrincipalRenewalInstructionType());
	    renewal.setInterestRenewalType(source.getTimeDepositSummary().getInterestRenewalInstructionType());

	    CASAAccountDTO casaAccountDTO = new CASAAccountDTO();
	    casaAccountDTO.setAccountNumber(source.getTimeDepositSummary().getBeneficiaryAccountNumberForPrincipal());
	    // renewal.setAccount(casaAccountDTO);
	    renewal.setTdPrincipleBeneficiaryAcct(casaAccountDTO);

	    CASAAccountDTO interestAccountDTO = new CASAAccountDTO();
	    interestAccountDTO.setAccountNumber(source.getTimeDepositSummary().getBeneficiaryAccountNumberForInterest());
	    // renewal.setAccount(interestAccountDTO);
	    renewal.setTdInterestRateBeneficiaryAcct(interestAccountDTO);

	    termDepositDTO.setRenewal(renewal);
	}
	if (source.getTimeDepositBalance() != null) {
	    termDepositDTO.setProjectedInterestAmount(ConvertUtils.convertAmount(source.getTimeDepositBalance().getProjectedInterestAmount()));
	    termDepositDTO.setInterestPaidToDate(ConvertUtils.convertAmount(source.getTimeDepositBalance().getInterestPaidTillDate()));
	}
	if (source.getTimeDepositPayout() != null) {
	    termDepositDTO.setNextInterestPaymentDate(ConvertUtils.convertDate(source.getTimeDepositPayout().getDateOfNextInterestPayment()));
	    termDepositDTO.setLastInterestPaymentDate(ConvertUtils.convertDate(source.getTimeDepositPayout().getDateOfLastInterestPayment()));
	}
	if (termDepositDTO.getTdPrincipalBalance() != null && termDepositDTO.getLienAmount() != null) {
	    termDepositDTO.setAvailableForRedemption(ConvertUtils.convertAmount(termDepositDTO.getTdPrincipalBalance().doubleValue()
		    - termDepositDTO.getLienAmount().doubleValue()));
	}
	dest.setDepositDTO(termDepositDTO);

	return dest;
    }

    private String checkServiceResponseHeader(BEMResHeader resHeader) throws Exception {

	String returnCode = "";

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();
	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;
	    }

	    else if (errorList != null) {
		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {

		    if (error.getErrorCode().equals(AccountErrorCodeConstant.ACCOUNT_NO_NOT_EXIST)) {
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
