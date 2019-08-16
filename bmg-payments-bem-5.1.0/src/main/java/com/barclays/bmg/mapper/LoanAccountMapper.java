package com.barclays.bmg.mapper;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.LoanAccountSummary.LoanAccountSummary;
import com.barclays.bmg.dto.LoanAccountDTO;
import com.barclays.bmg.utils.ConvertUtils;

public class LoanAccountMapper {

    public List<LoanAccountDTO> mapCollection(LoanAccountSummary[] sourceObject) {

	List<LoanAccountDTO> loanAccountDTOList = new ArrayList<LoanAccountDTO>();
	for (LoanAccountSummary cas : sourceObject) {
	    loanAccountDTOList.add(mapBean(cas, null));

	}

	return loanAccountDTOList;

    }

    public LoanAccountDTO mapBean(LoanAccountSummary sourceObject, LoanAccountDTO destObject) {
	LoanAccountDTO destiObject = null;
	if (null == destObject) {
	    destiObject = new LoanAccountDTO();
	} else {
	    destiObject = destObject;
	}
	destiObject.setCurrency(sourceObject.getAccountCurrencyCode());
	destiObject.setAccountNumber(sourceObject.getAccountNumber());
	if (sourceObject.getDomicileBranch() != null) {
	    destiObject.setBranchCode(sourceObject.getDomicileBranch().getBranchCode());
	}

	if (sourceObject.getAccountCurrentBalance() != null) {
	    // This field will be displayed as Out Standing Loan Amount in the Loan Summary Page
	    destiObject.setLoanAmount(ConvertUtils.convertAmount(sourceObject.getAccountCurrentBalance().getAccountCCYBalance()));

	}
	// map new field for ssa

	destiObject.setOutstandingLoanAmount(ConvertUtils.convertAmount(sourceObject.getOutstandingLoanAmount()));

	if (sourceObject.getOutstandingLoanAmount() == null) {
	    destiObject.setOutstandingLoanAmount(ConvertUtils.convertAmount(sourceObject.getAccountCurrentBalance().getAccountCCYBalance()
		    .doubleValue()));
	}

	destiObject.setLoanMaturityDate(ConvertUtils.convertDate(sourceObject.getMaturityDate()));

	destiObject.setProductCode(ConvertUtils.getProductCode(sourceObject.getProduct()));
	destiObject.setDesc(sourceObject.getAccountTitle());
	// Not Required
	// destiObject.setDueAmount(ConvertUtils.convertAmount(sourceObject.getNextDueAmount()));
	destiObject.setAccountStatus(sourceObject.getAccountLifecycleStatusCode());
	destiObject.setRelationshipCode(ConvertUtils.getRelShipTpeCode(sourceObject.getJointAccountRelationship()));
	return destiObject;
    }

}
