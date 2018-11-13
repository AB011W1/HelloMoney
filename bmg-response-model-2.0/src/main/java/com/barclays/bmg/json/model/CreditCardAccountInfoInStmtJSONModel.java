package com.barclays.bmg.json.model;

import java.io.Serializable;

import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CreditCardAccountInfoInStmtJSONModel implements Serializable {

	private static final long serialVersionUID = -2659880567306916002L;
//	private String crdNo = "";
	private String pmtDueDt = "";
	private AmountJSONModel minDueAmt;

	public CreditCardAccountInfoInStmtJSONModel(CreditCardAccountDTO accountDTO) {
//		super(accountDTO);
		String currency = accountDTO.getCurrency();

//		this.crdNo = accountDTO.getPrimary().getCardNumber();

		this.pmtDueDt = BMGFormatUtility.getShortDate(accountDTO
				.getPaymentDueDate());
		this.minDueAmt = BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(accountDTO
						.getMinPaymentAmount()));
	}

//	public String getCrdNo() {
//		return crdNo;
//	}
//
//	public void setCrdNo(String crdNo) {
//		this.crdNo = crdNo;
//	}

	public AmountJSONModel getMinDueAmt() {
		return minDueAmt;
	}

	public void setMinDueAmt(AmountJSONModel minDueAmt) {
		this.minDueAmt = minDueAmt;
	}

	public String getPmtDueDt() {
		return pmtDueDt;
	}

	public void setPmtDueDt(String pmtDueDt) {
		this.pmtDueDt = pmtDueDt;
	}

//	@Override
//	public String getTyp() {
//
//		return AccountConstants.CC_ACCOUNTS;
//	}
}
