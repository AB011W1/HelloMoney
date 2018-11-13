package com.barclays.bmg.json.model;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.dto.LoanAccountDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class LoanAccountJSONModel extends AccountJSONModel {


	private static final long serialVersionUID = 6342525364100857839L;

	private AmountJSONModel amt ;
	private String matDt = "";

	public LoanAccountJSONModel(LoanAccountDTO accountDTO) {
		super(accountDTO);
		String currency = accountDTO.getCurrency();

		this.amt = BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(accountDTO.getOutstandingLoanAmount()));
		this.matDt = BMGFormatUtility.getShortDate(accountDTO.getLoanMaturityDate());
	}


	public AmountJSONModel getAmt() {
		return amt;
	}


	public void setAmt(AmountJSONModel amt) {
		this.amt = amt;
	}


	public String getMatDt() {
		return matDt;
	}

	public void setMatDt(String matDt) {
		this.matDt = matDt;
	}

	@Override
	public String getTyp() {
		// TODO Auto-generated method stub
		return AccountConstants.LOAN_ACCOUNTS;
	}
}
