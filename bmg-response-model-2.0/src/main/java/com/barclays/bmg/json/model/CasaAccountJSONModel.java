package com.barclays.bmg.json.model;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CasaAccountJSONModel extends AccountJSONModel {

	private static final long serialVersionUID = 3523628054668114895L;

	private AmountJSONModel curBal;
	private AmountJSONModel avblBal;
	private AmountJSONModel amtOnHld;
	private AmountJSONModel unclFnd;
	private AmountJSONModel odLmt;
	private AmountJSONModel minBal;
	private AmountJSONModel wdBal;
	private AmountJSONModel netBalanceAmount;
	private AmountJSONModel currentBookBalanceAmount;
	private String actHlds;
	private String ibanNo;
	private String brnCde;
	private String brnNam;
	//For groupwallet
    private String bankCif;
	private String groupWalletIndicator;


    public String getBankCif() {
		return bankCif;
	}

	public void setBankCif(String bankCif) {
		this.bankCif = bankCif;
	}

	public String getGroupWalletIndicator() {
		return groupWalletIndicator;
	}

	public void setGroupWalletIndicator(String groupWalletIndicator) {
		this.groupWalletIndicator = groupWalletIndicator;
	}
	public CasaAccountJSONModel(CASAAccountDTO accountDTO) {
		super(accountDTO);

		String currency = accountDTO.getCurrency();

		this.curBal = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility
				.getFormattedAmount(accountDTO.getCurrentBalance()));
		this.avblBal = BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(accountDTO
						.getAvailableBalance()));
		this.amtOnHld = BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(accountDTO
						.getOnHoldAmount()));
		this.unclFnd = BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(accountDTO
						.getUnclearedFunds()));
		this.odLmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility
				.getFormattedAmount(accountDTO.getOverDraftLimit()));
		this.minBal = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility
				.getFormattedAmount(accountDTO.getMinimumBalanceRequired()));
		this.wdBal = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility
				.getFormattedAmount(accountDTO.getWithdrawalBalance()));
		this.netBalanceAmount = BMGFormatUtility.getJSONAmount(currency,
				BMGFormatUtility.getFormattedAmount(accountDTO
						.getNetBalanceAmount()));
		this.currentBookBalanceAmount = BMGFormatUtility.getJSONAmount(
				currency, BMGFormatUtility.getFormattedAmount(accountDTO
						.getCurrentBookBalanceAmount()));

		this.actHlds = BMGFormatUtility.removeSpaceInBetween(accountDTO
				.getAccountHolders());

		this.ibanNo = accountDTO.getIban();
		this.brnCde = accountDTO.getBranchCode();
		this.bankCif=accountDTO.getBankCif();
		this.groupWalletIndicator=accountDTO.getGroupWalletIndicator();
	}

	public CasaAccountJSONModel() {
		super();
	}

	public String getActHlds() {
		return actHlds;
	}

	public void setActHlds(String accountHolders) {
		this.actHlds = accountHolders;
	}

	public AmountJSONModel getCurBal() {
		return curBal;
	}

	public void setCurBal(AmountJSONModel curBal) {
		this.curBal = curBal;
	}

	public AmountJSONModel getAvblBal() {
		return avblBal;
	}

	public void setAvblBal(AmountJSONModel avblBal) {
		this.avblBal = avblBal;
	}

	public AmountJSONModel getAmtOnHld() {
		return amtOnHld;
	}

	public void setAmtOnHld(AmountJSONModel amtOnHld) {
		this.amtOnHld = amtOnHld;
	}

	public AmountJSONModel getUnclFnd() {
		return unclFnd;
	}

	public void setUnclFnd(AmountJSONModel unclFnd) {
		this.unclFnd = unclFnd;
	}

	public AmountJSONModel getOdLmt() {
		return odLmt;
	}

	public void setOdLmt(AmountJSONModel odLmt) {
		this.odLmt = odLmt;
	}

	public AmountJSONModel getMinBal() {
		return minBal;
	}

	public void setMinBal(AmountJSONModel minBal) {
		this.minBal = minBal;
	}

	public AmountJSONModel getWdBal() {
		return wdBal;
	}

	public void setWdBal(AmountJSONModel wdBal) {
		this.wdBal = wdBal;
	}

	public String getIbanNo() {
		return ibanNo;
	}

	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}

	public String getBrnCde() {
		return brnCde;
	}

	public void setBrnCde(String brnCde) {
		this.brnCde = brnCde;
	}

	public String getBrnNam() {
		return brnNam;
	}

	public void setBrnNam(String brnNam) {
		this.brnNam = brnNam;
	}

	@Override
	public String getTyp() {
		// TODO Auto-generated method stub
		return AccountConstants.CASA_ACCOUNTS;
	}

	/**
	 * @return the netBalanceAmount
	 */
	@Override
	public AmountJSONModel getNetBalanceAmount() {
		return netBalanceAmount;
	}

	/**
	 * @param netBalanceAmount the netBalanceAmount to set
	 */
	@Override
	public void setNetBalanceAmount(AmountJSONModel netBalanceAmount) {
		this.netBalanceAmount = netBalanceAmount;
	}

	/**
	 * @return the currentBookBalanceAmount
	 */
	@Override
	public AmountJSONModel getCurrentBookBalanceAmount() {
		return currentBookBalanceAmount;
	}

	/**
	 * @param currentBookBalanceAmount the currentBookBalanceAmount to set
	 */
	@Override
	public void setCurrentBookBalanceAmount(AmountJSONModel currentBookBalanceAmount) {
		this.currentBookBalanceAmount = currentBookBalanceAmount;
	}

}
