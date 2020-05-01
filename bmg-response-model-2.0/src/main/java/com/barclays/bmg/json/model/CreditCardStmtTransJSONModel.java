package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class CreditCardStmtTransJSONModel extends BMBPayloadData implements Serializable {

    private static final long serialVersionUID = 6728918188544089009L;

    // private CreditCardAccountInfoInStmtJSONModel actDetls;

    private List<String> bilDt = new ArrayList<String>();

    // private CreditCardStmtBalanceInfoJSONModel balInfo;
    private List<CreditCardStmtBalanceInfoJSONModel> creditCardStmtBalanceInfoJSONModelList = new ArrayList<CreditCardStmtBalanceInfoJSONModel>();

    private CreditCardTransactionHistoryJSONModel crdDetls;

    /*
     * private CreditCardTransactionHistoryJSONModel priCrdDetls; private List<CreditCardTransactionHistoryJSONModel> replCrdDetls = new
     * ArrayList<CreditCardTransactionHistoryJSONModel>(); private List<CreditCardTransactionHistoryJSONModel> suplCrdDetls = new
     * ArrayList<CreditCardTransactionHistoryJSONModel>();
     */
    /*
     * public CreditCardStmtTransJSONModel(CreditCardAccountDTO ccAccDto,List<Calendar> stmtDates, CreditCardStmtBalanceInfoDTO ccStmtBalInfo,
     * CreditCardTransactionHistoryDTO primaryCardHistory, List<CreditCardTransactionHistoryDTO> replCardGroupedHistoryList,
     * List<CreditCardTransactionHistoryDTO> supplCardGroupedHistoryList) {
     */
    public CreditCardStmtTransJSONModel(CreditCardAccountDTO ccAccDto, List<CreditCardStmtBalanceInfoDTO> ccStmtBalInfoList) {

	// this.actDetls = new CreditCardAccountInfoInStmtJSONModel(ccAccDto);
	String currency = ccAccDto.getCurrency();
	String ccAccountNo=ccAccDto.getAccountNumber();

	/*
	 * if (stmtDates != null) { for (Calendar dt : stmtDates) { this.bilDt.add(BMGFormatUtility.getShortDate(dt.getTime())); } }
	 */
	if (ccStmtBalInfoList != null) {

	    for (CreditCardStmtBalanceInfoDTO creditCardStmtBalanceInfoDTO : ccStmtBalInfoList) {

		if (creditCardStmtBalanceInfoDTO != null) {
		    this.creditCardStmtBalanceInfoJSONModelList.add(new CreditCardStmtBalanceInfoJSONModel(creditCardStmtBalanceInfoDTO, currency, ccAccountNo));
		}

	    }

	}

	/*
	 * if (ccStmtBalInfo != null) { this.balInfo = new CreditCardStmtBalanceInfoJSONModel(ccStmtBalInfo, currency); }
	 */
	/*
	 * if (primaryCardHistory != null) { this.priCrdDetls = new CreditCardTransactionHistoryJSONModel(primaryCardHistory, currency);
	 * 
	 * }
	 * 
	 * if (replCardGroupedHistoryList != null && replCardGroupedHistoryList.size() > 0) { for (CreditCardTransactionHistoryDTO each :
	 * replCardGroupedHistoryList) { CreditCardTransactionHistoryJSONModel ccTrxHist = new CreditCardTransactionHistoryJSONModel(each,currency);
	 * this.replCrdDetls.add(ccTrxHist); }
	 * 
	 * }
	 * 
	 * if (supplCardGroupedHistoryList != null && supplCardGroupedHistoryList.size() > 0) { for (CreditCardTransactionHistoryDTO each :
	 * supplCardGroupedHistoryList) { CreditCardTransactionHistoryJSONModel ccTrxHist = new CreditCardTransactionHistoryJSONModel(each,currency);
	 * this.suplCrdDetls.add(ccTrxHist); }
	 * 
	 * }
	 */
    }

    public CreditCardStmtTransJSONModel() {
	super();
    }

    // public CreditCardAccountInfoInStmtJSONModel getActDetls() {
    // return actDetls;
    // }
    //
    // public void setActDetls(CreditCardAccountInfoInStmtJSONModel actDetls) {
    // this.actDetls = actDetls;
    // }

    /*
     * public CreditCardStmtBalanceInfoJSONModel getBalInfo() { return balInfo; }
     * 
     * public void setBalInfo(CreditCardStmtBalanceInfoJSONModel balInfo) { this.balInfo = balInfo; }
     */

    public CreditCardTransactionHistoryJSONModel getCrdDetls() {
	return crdDetls;
    }

    public void setCrdDetls(CreditCardTransactionHistoryJSONModel crdDetls) {
	this.crdDetls = crdDetls;
    }

    public List<String> getBilDt() {
	return bilDt;
    }

    public void setBilDt(List<String> bilDt) {
	this.bilDt = bilDt;
    }

    public List<CreditCardStmtBalanceInfoJSONModel> getCreditCardStmtBalanceInfoJSONModelList() {
	return creditCardStmtBalanceInfoJSONModelList;
    }

    public void setCreditCardStmtBalanceInfoJSONModelList(List<CreditCardStmtBalanceInfoJSONModel> creditCardStmtBalanceInfoJSONModelList) {
	this.creditCardStmtBalanceInfoJSONModelList = creditCardStmtBalanceInfoJSONModelList;
    }

}
