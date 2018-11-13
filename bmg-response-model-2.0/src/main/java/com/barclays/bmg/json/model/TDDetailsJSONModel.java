package com.barclays.bmg.json.model;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.dto.TermDepositDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class TDDetailsJSONModel extends AccountJSONModel{

	private static final long serialVersionUID = -7189251239616060010L;

	private String depDt = "";
	private String matDt = "";
	private String intRt = "";
	private TenureJSONModel tenure = new TenureJSONModel();
	private AmountJSONModel matAmt = null;
	private AmountJSONModel priAmt = null;
	private MaturityInstructionJSONModel matInstr = new MaturityInstructionJSONModel();

	public TDDetailsJSONModel(TdAccountDTO accountDTO) {
		super(accountDTO);
		// this.currBal =
		// BMGFormatUtility.getFormattedAmount(accountDTO.getCurrentBalance());
		// this.depNo = accountDTO.getDepositDTO().getDepositNumber();

		TermDepositDTO  depositDTO = accountDTO.getDepositDTO();

		this.depDt = BMGFormatUtility.getShortDate(depositDTO.getValueDate());
		this.matDt = BMGFormatUtility.getShortDate(depositDTO.getMaturityDate());
		this.intRt = BMGFormatUtility.getFormattedAmount(depositDTO.getInterestRate().getAmount());

		this.tenure = new TenureJSONModel();
		if(depositDTO.getTenureTerm().getYear()!= null){
			this.tenure.setYear(depositDTO.getTenureTerm().getYear().toString());
		}
		if(depositDTO.getTenureTerm().getMonth()!= null){
			this.tenure.setMnth(depositDTO.getTenureTerm().getMonth().toString());
		}
		if(depositDTO.getTenureTerm().getDay()!= null){
			this.tenure.setDays(depositDTO.getTenureTerm().getDay().toString());
		}
		AmountJSONModel matAmt = new AmountJSONModel();
		matAmt.setAmt(BMGFormatUtility.getFormattedAmount(depositDTO.getMaturityAmount()));
		matAmt.setCurr(accountDTO.getCurrency());

		this.matAmt = matAmt;

		AmountJSONModel priAmt = new AmountJSONModel();
		priAmt.setAmt(BMGFormatUtility.getFormattedAmount(depositDTO.getTdPrincipalBalance()));
		priAmt.setCurr(accountDTO.getCurrency());
		this.priAmt = priAmt;

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


	public AmountJSONModel getMatAmt() {
		return matAmt;
	}


	public void setMatAmt(AmountJSONModel matAmt) {
		this.matAmt = matAmt;
	}


	public TenureJSONModel getTenure() {
		return tenure;
	}


	public void setTenure(TenureJSONModel tenure) {
		this.tenure = tenure;
	}


	public AmountJSONModel getPriAmt() {
		return priAmt;
	}


	public void setPriAmt(AmountJSONModel priAmt) {
		this.priAmt = priAmt;
	}


	public MaturityInstructionJSONModel getMatInstr() {
		return matInstr;
	}


	public void setMatInstr(MaturityInstructionJSONModel matInstr) {
		this.matInstr = matInstr;
	}


	@Override
	public String getTyp() {
		// TODO Auto-generated method stub
		return AccountConstants.TD_ACCOUNTS;
	}

}
