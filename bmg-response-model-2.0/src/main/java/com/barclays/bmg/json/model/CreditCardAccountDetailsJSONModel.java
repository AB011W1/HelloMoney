package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardStmtPointsInfoDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.json.response.BMBPayloadData;



public class CreditCardAccountDetailsJSONModel extends BMBPayloadData
		implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8895944221524043259L;

	private CreditCardAccountJSONModel actDetls;
	private CreditCardStmtPointsInfoJSONBean rewradPts;
	private CreditCardTransactionHistoryJSONModel priCrdDetls;
	private List<CreditCardTransactionHistoryJSONModel>  replCrdDetls = new ArrayList<CreditCardTransactionHistoryJSONModel>();
	private List<CreditCardTransactionHistoryJSONModel>  suplCrdDetls = new ArrayList<CreditCardTransactionHistoryJSONModel>();

	public CreditCardAccountDetailsJSONModel(CreditCardAccountDTO accountDTO,
											CreditCardStmtPointsInfoDTO rewardPoints,
											CreditCardTransactionHistoryDTO  primaryCardHistory,
											List<CreditCardTransactionHistoryDTO>  replCardGroupedHistoryList,
											List<CreditCardTransactionHistoryDTO>  supplCardGroupedHistoryList) {

		this.actDetls = new CreditCardAccountJSONModel(accountDTO);
		//Set Product Description
		this.actDetls.setDesc(accountDTO.getProductCode());

		this.rewradPts = new CreditCardStmtPointsInfoJSONBean(rewardPoints);

		String currency = accountDTO.getCurrency();

		if (primaryCardHistory != null ) {
			this.priCrdDetls = new CreditCardTransactionHistoryJSONModel(primaryCardHistory, currency);

		}

		if (replCardGroupedHistoryList != null && replCardGroupedHistoryList.size() > 0) {
			for(CreditCardTransactionHistoryDTO each: replCardGroupedHistoryList){
				CreditCardTransactionHistoryJSONModel ccTrxHist = new CreditCardTransactionHistoryJSONModel(each, currency);
				this.replCrdDetls.add(ccTrxHist);
			}

		}

		if (supplCardGroupedHistoryList != null && supplCardGroupedHistoryList.size() > 0) {
			for(CreditCardTransactionHistoryDTO each: supplCardGroupedHistoryList){
				CreditCardTransactionHistoryJSONModel ccTrxHist = new CreditCardTransactionHistoryJSONModel(each, currency);
				this.suplCrdDetls.add(ccTrxHist);
			}

		}

	}

	public CreditCardAccountDetailsJSONModel() {
		super();
	}



	public CreditCardAccountJSONModel getActDetls() {
		return actDetls;
	}

	public void setActDetls(CreditCardAccountJSONModel actDetls) {
		this.actDetls = actDetls;
	}

	public CreditCardTransactionHistoryJSONModel getPriCrdDetls() {
		return priCrdDetls;
	}

	public void setPriCrdDetls(CreditCardTransactionHistoryJSONModel priCrdDetls) {
		this.priCrdDetls = priCrdDetls;
	}

	public List<CreditCardTransactionHistoryJSONModel> getReplCrdDetls() {
		return replCrdDetls;
	}

	public void setReplCrdDetls(
			List<CreditCardTransactionHistoryJSONModel> replCrdDetls) {
		this.replCrdDetls = replCrdDetls;
	}

	public List<CreditCardTransactionHistoryJSONModel> getSuplCrdDetls() {
		return suplCrdDetls;
	}

	public void setSuplCrdDetls(
			List<CreditCardTransactionHistoryJSONModel> suplCrdDetls) {
		this.suplCrdDetls = suplCrdDetls;
	}

	public CreditCardStmtPointsInfoJSONBean getRewradPts() {
		return rewradPts;
	}

	public void setRewradPts(CreditCardStmtPointsInfoJSONBean rewradPts) {
		this.rewradPts = rewradPts;
	}

}
