package com.barclays.bmg.json.model;

import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.dto.NAV;
import com.barclays.bmg.type.Currency;
import com.barclays.bmg.utils.BMGFormatUtility;

public class InvestmentAccountJSONModel extends AccountJSONModel {

	private static final long serialVersionUID = 8996916972286450937L;

	private String prodName;
	// private String invName;
	// private String fundName;

	private String asOf = "";
	// private String astTypCde;
	private String astTypName;

	// ---totalAmountInvested
	private AmountJSONModel totAmtInv;

	// ---currentMarketValue
	private AmountJSONModel curMktVal;

	// --- No. of units
	private String noOfUnit;

	// --- redeemedUnits
	private String redmUnits;

	// ---currentNAV
	private NAV curNAV;

	// -----previousNAV
	// private NAV preNAV;

	// ---dailygrowthDecline
	private String dlyGrwDec;

	// ---monthToDateGrowth
	private String monToDteGro;

	// ---- Currency Code
	private Currency curCde;

	// ---yearToDateGrowthDecline
	private String yrToDtGrwDec;

	private String lienUnit;

	private String divIntRcvd;

	public InvestmentAccountJSONModel(InvestmentAccountDTO accountDTO) {
		super(accountDTO);
		String currency = accountDTO.getCurrency();

		if (accountDTO.getProductName() != null) {
			this.prodName = accountDTO.getProductName();
		}

		if (accountDTO.getAssetTypeName() != null) {
			this.astTypName = accountDTO.getAssetTypeName();
		} else {
			this.astTypName = accountDTO.getAssetTypeCode();
		}

		this.asOf = BMGFormatUtility.getShortDate(accountDTO.getAsOf());
		this.totAmtInv = BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(accountDTO
						.getTotalAmountInvested()));
		this.noOfUnit = BMGFormatUtility.getFormattedAmount4Decimal(accountDTO
				.getNoOfUnit());
		this.curNAV = accountDTO.getCurrentNAV();
		this.monToDteGro = BMGFormatUtility.getFormattedAmount(accountDTO
				.getMonthToDateGrowth());
		this.curCde = accountDTO.getCurrencyCode();
		this.curMktVal = BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(accountDTO
						.getCurrentMarketValue()));
		this.redmUnits = BMGFormatUtility.getFormattedAmount4Decimal(accountDTO
				.getRedeemedUnits());
		this.dlyGrwDec = BMGFormatUtility.getFormattedAmount(accountDTO
				.getDailygrowthDecline());
		this.yrToDtGrwDec = BMGFormatUtility.getFormattedAmount(accountDTO
				.getYearToDateGrowthDecline());

		this.lienUnit = BMGFormatUtility.getFormattedAmount(accountDTO
				.getLienUnits());

		this.divIntRcvd = BMGFormatUtility.getFormattedAmount(accountDTO
				.getDividendInterestReceived());
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getAstTypName() {
		return astTypName;
	}

	public void setAstTypName(String astTypName) {
		this.astTypName = astTypName;
	}

	public AmountJSONModel getTotAmtInv() {
		return totAmtInv;
	}

	public void setTotAmtInv(AmountJSONModel totAmtInv) {
		this.totAmtInv = totAmtInv;
	}

	public AmountJSONModel getCurMktVal() {
		return curMktVal;
	}

	public void setCurMktVal(AmountJSONModel curMktVal) {
		this.curMktVal = curMktVal;
	}

	public String getNoOfUnit() {
		return noOfUnit;
	}

	public void setNoOfUnit(String noOfUnit) {
		this.noOfUnit = noOfUnit;
	}

	public String getRedmUnits() {
		return redmUnits;
	}

	public void setRedmUnits(String redmUnits) {
		this.redmUnits = redmUnits;
	}

	public NAV getCurNAV() {
		return curNAV;
	}

	public void setCurNAV(NAV curNAV) {
		this.curNAV = curNAV;
	}

	public String getDlyGrwDec() {
		return dlyGrwDec;
	}

	public void setDlyGrwDec(String dlyGrwDec) {
		this.dlyGrwDec = dlyGrwDec;
	}

	public String getMonToDteGro() {
		return monToDteGro;
	}

	public void setMonToDteGro(String monToDteGro) {
		this.monToDteGro = monToDteGro;
	}

	public Currency getCurCde() {
		return curCde;
	}

	public void setCurCde(Currency curCde) {
		this.curCde = curCde;
	}

	public String getYrToDtGrwDec() {
		return yrToDtGrwDec;
	}

	public void setYrToDtGrwDec(String yrToDtGrwDec) {
		this.yrToDtGrwDec = yrToDtGrwDec;
	}

	public String getAsOf() {
		return asOf;
	}

	public void setAsOf(String asOf) {
		this.asOf = asOf;
	}

	public String getLienUnit() {
		return lienUnit;
	}

	public void setLienUnit(String lienUnit) {
		this.lienUnit = lienUnit;
	}

	public String getDivIntRcvd() {
		return divIntRcvd;
	}

	public void setDivIntRcvd(String divIntRcvd) {
		this.divIntRcvd = divIntRcvd;
	}

}
