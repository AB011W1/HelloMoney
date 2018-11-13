package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.dto.AccountTrnxDTO;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.sort.CASATransactionHistoryComparator;

public class CasaAccountTrnxHistoryDetailsJSONModel extends BMBPayloadData implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3705315988465277685L;


	private List<AccountTrnxHistoryJSONBean> actActvLst = new ArrayList<AccountTrnxHistoryJSONBean>();

	private String accountNumber;


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public CasaAccountTrnxHistoryDetailsJSONModel(List<AccountTrnxDTO> accountAccList) {

		int recentTransaction = Integer
				.parseInt(CommonConstants.RECENT_TRANSACTION);

		//this.actDetls = new CasaAccountTrnxHistoryJSONModel(accountDTO);

		if (accountAccList != null && accountAccList.size() > 0) {

			CASATransactionHistoryComparator comparator = new CASATransactionHistoryComparator();
			Collections.sort(accountAccList, comparator);

			int i = 1;
			for (AccountTrnxDTO acctTrnxDTO : accountAccList) {
				actActvLst.add(new AccountTrnxHistoryJSONBean(acctTrnxDTO));

				if (i == recentTransaction) {
					break;
				}
				i++;
			}

		}
	}

	public CasaAccountTrnxHistoryDetailsJSONModel() {
		super();
	}


	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the actActvLst
	 */
	public List<AccountTrnxHistoryJSONBean> getActActvLst() {
		return actActvLst;
	}

	/**
	 * @param actActvLst the actActvLst to set
	 */
	public void setActActvLst(List<AccountTrnxHistoryJSONBean> actActvLst) {
		this.actActvLst = actActvLst;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */

		// this.displayNoHistoryFound = displayNoHistoryFound;
}
