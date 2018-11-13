package com.barclays.bmg.json.model;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.dto.TermDepositDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class TDAccountJSONModel extends AccountJSONModel {

	private static final long serialVersionUID = -7189251239616060010L;

	private AmountJSONModel curBal;
	private String depNo = "";

	private String depDt = "";
	private String matDt = "";
	private String intRt = "";
	private String tnr = "";
	private AmountJSONModel matAmt;
	private AmountJSONModel prnAmt;
	private String matInst = "";


	public TDAccountJSONModel(TdAccountDTO accountDTO) {
		super(accountDTO);

		String currency = accountDTO.getCurrency();
		TermDepositDTO depositDTO = accountDTO.getDepositDTO();

		this.curBal = BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(accountDTO.getCurrentBalance()));
		this.depNo = depositDTO.getDepositNumber();

		this.depDt = BMGFormatUtility.getShortDate(depositDTO.getDepositDate());
		this.matDt = BMGFormatUtility.getShortDate(depositDTO.getMaturityDate());

		//TODO
//		this.intRt = (String)depositDTO.getInterestRate());
//		this.tnr = BMGFormatUtility.getFormattedAmount(depositDTO.getTenureTerm());
		this.matAmt = BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(depositDTO.getMaturityAmount()));
		this.prnAmt = BMGFormatUtility.getJSONAmount(currency,BMGFormatUtility.getFormattedAmount(depositDTO.getDepositAmount().getAmount()));
		this.matInst = depositDTO.getMaturityInstruction();

	}


	public String getDepNo() {
		return depNo;
	}

	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}

	public String getDepDt() {
		return depDt;
	}

	public void setDepDt(String depDt) {
		this.depDt = depDt;
	}

	public String getMatDt() {
		return matDt;
	}

	public void setMatDt(String matDt) {
		this.matDt = matDt;
	}

	public String getIntRt() {
		return intRt;
	}

	public void setIntRt(String intRt) {
		this.intRt = intRt;
	}

	public String getTnr() {
		return tnr;
	}

	public void setTnr(String tnr) {
		this.tnr = tnr;
	}


	public String getMatInst() {
		return matInst;
	}

	public void setMatInst(String matInst) {
		this.matInst = matInst;
	}

	public AmountJSONModel getCurBal() {
		return curBal;
	}


	public void setCurBal(AmountJSONModel curBal) {
		this.curBal = curBal;
	}


	public AmountJSONModel getMatAmt() {
		return matAmt;
	}


	public void setMatAmt(AmountJSONModel matAmt) {
		this.matAmt = matAmt;
	}


	public AmountJSONModel getPrnAmt() {
		return prnAmt;
	}


	public void setPrnAmt(AmountJSONModel prnAmt) {
		this.prnAmt = prnAmt;
	}


	@Override
	public String getTyp() {
		// TODO Auto-generated method stub
		return AccountConstants.TD_ACCOUNTS;
	}

}
