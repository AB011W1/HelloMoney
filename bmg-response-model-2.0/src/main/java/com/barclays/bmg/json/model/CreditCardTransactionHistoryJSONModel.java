package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class CreditCardTransactionHistoryJSONModel extends BMBPayloadData
		implements Serializable {

	private static final long serialVersionUID = 5436179919334802597L;

	private String crdNo;
	private String mkdCrdNo;
	private String crdHlds;
	private String crdTyp;

	private List<CreditCardAccountActivityJSONBean> actActvLst = new ArrayList<CreditCardAccountActivityJSONBean>();

	private String dispNoHistFnd = "No";

	public CreditCardTransactionHistoryJSONModel(
			CreditCardTransactionHistoryDTO ccTrxHistory, String actCurrency) {
		this.crdNo = ccTrxHistory.getCardNumber();
		this.crdHlds = ccTrxHistory.getCardHolder();
		this.crdTyp = ccTrxHistory.getCardType();

		List<CreditCardActivityDTO> ccAccList = ccTrxHistory
				.getCreditCardActivityList();

		if (ccAccList!=null && ccAccList.size() > 0) {
			ccAccList = sortListInReverseOrder(ccAccList);
		}

		if (ccAccList != null && ccAccList.size() > 0) {

			for (CreditCardActivityDTO accountActivityDTO : ccAccList) {
				if (accountActivityDTO != null) {
					actActvLst.add(new CreditCardAccountActivityJSONBean(
							accountActivityDTO, actCurrency));
				}
			}
		}
	}

	CreditCardTransactionHistoryJSONModel() {

	}

	public String getCrdNo() {
		return crdNo;
	}

	public void setCrdNo(String crdNo) {
		this.crdNo = crdNo;
	}

	public String getCrdHlds() {
		return crdHlds;
	}

	public void setCrdHlds(String crdHlds) {
		this.crdHlds = crdHlds;
	}

	public List<CreditCardAccountActivityJSONBean> getActActvLst() {
		return actActvLst;
	}

	public void setActActvLst(List<CreditCardAccountActivityJSONBean> actActvLst) {
		this.actActvLst = actActvLst;
	}

	public String getMkdCrdNo() {
		return mkdCrdNo;
	}

	public void setMkdCrdNo(String mkdCrdNo) {
		this.mkdCrdNo = mkdCrdNo;
	}

	public String getCrdTyp() {
		return crdTyp;
	}

	public void setCrdTyp(String crdTyp) {
		this.crdTyp = crdTyp;
	}

	public void setDispNoHistFnd(String dispNoHistFnd) {
		this.dispNoHistFnd = dispNoHistFnd;
	}

	public String getDispNoHistFnd() {
		if (actActvLst == null || actActvLst.size() == 0) {
			dispNoHistFnd = "Yes";
		}
		return dispNoHistFnd;
	}

	private List<CreditCardActivityDTO> sortListInReverseOrder(
			List<CreditCardActivityDTO> ccAccList) {

		List<CreditCardActivityDTO> ccAccListReverseOrder = new ArrayList<CreditCardActivityDTO>();

		int j = ccAccList.size() - 1;
		for (int i = 0; i < ccAccList.size(); i++) {
			ccAccListReverseOrder.add(ccAccList.get(j));
			j--;
		}

		return ccAccListReverseOrder;

	}

}
