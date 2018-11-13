package com.barclays.bmg.json.model;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.dto.InsuranceAccountDTO;
import com.barclays.bmg.utils.BMGFormatUtility;



public class InsuranceAccountJSONModel extends AccountJSONModel {

	private static final long serialVersionUID = -6160022424779785091L;


	private String insTyp = "";
	private String polExpDt = "";
	private String polNo = "";
	private String asOf = "";

	private AmountJSONModel sumAssu;
	private AmountJSONModel preAmt;

	public InsuranceAccountJSONModel(InsuranceAccountDTO accountDTO) {
		super(accountDTO);
		String currency = accountDTO.getCurrency();

		this.insTyp = accountDTO.getInsuranceType();
		this.sumAssu = BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(accountDTO.getSumAssured()));
		this.polExpDt = BMGFormatUtility.getShortDate(accountDTO.getPolicyExpireDate());
		this.polNo = accountDTO.getPolicyNumber();
		this.preAmt = BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(accountDTO.getPremiumAmount()));
		this.asOf = BMGFormatUtility.getShortDate(accountDTO.getAsOf());
	}

	public String getInsTyp() {
		return insTyp;
	}

	public void setInsTyp(String insTyp) {
		this.insTyp = insTyp;
	}

	public String getPolExpDt() {
		return polExpDt;
	}

	public void setPolExpDt(String polExpDt) {
		this.polExpDt = polExpDt;
	}

	public String getPolNo() {
		return polNo;
	}

	public void setPolNo(String polNo) {
		this.polNo = polNo;
	}


	public String getAsOf() {
		return asOf;
	}

	public void setAsOf(String asOf) {
		this.asOf = asOf;
	}


	public AmountJSONModel getSumAssu() {
		return sumAssu;
	}

	public void setSumAssu(AmountJSONModel sumAssu) {
		this.sumAssu = sumAssu;
	}

	public AmountJSONModel getPreAmt() {
		return preAmt;
	}

	public void setPreAmt(AmountJSONModel preAmt) {
		this.preAmt = preAmt;
	}

	@Override
	public String getTyp() {
		// TODO Auto-generated method stub
		return AccountConstants.INSURE_ACCOUNTS;
	}
}
