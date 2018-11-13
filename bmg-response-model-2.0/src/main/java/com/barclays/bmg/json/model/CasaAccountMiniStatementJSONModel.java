package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.sort.CASATransactionActivityComparator;

public class CasaAccountMiniStatementJSONModel extends BMBPayloadData implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3705315988465277685L;

	private CasaAccountJSONModel actDetls = new CasaAccountJSONModel();

	private List<AccountActivityJSONBean> actActvLst = new ArrayList<AccountActivityJSONBean>();

	protected String dispNoHistFnd = "No";

	private String opnBal = "";
	private String clsBal = "";

	public CasaAccountMiniStatementJSONModel(CASAAccountDTO accountDTO,
			List<AccountActivityDTO> accountAccList) {

		int recentTransaction = Integer
				.parseInt(CommonConstants.RECENT_TRANSACTION);

		this.actDetls = new CasaAccountJSONModel(accountDTO);

		if (accountAccList != null && accountAccList.size() > 0) {

			CASATransactionActivityComparator comparator = new CASATransactionActivityComparator();
			Collections.sort(accountAccList, comparator);

			int i = 1;
			for (AccountActivityDTO accountActivityDTO : accountAccList) {
				actActvLst.add(new AccountActivityJSONBean(accountActivityDTO));

				if (i == recentTransaction) {
					break;
				}
				i++;
			}

		}
	}

	public CasaAccountMiniStatementJSONModel() {
		super();
	}

	public CasaAccountJSONModel getActDetls() {
		return actDetls;
	}

	public void setActDetls(CasaAccountJSONModel actDetls) {
		this.actDetls = actDetls;
	}

	public List<AccountActivityJSONBean> getActActvLst() {
		return actActvLst;
	}

	public void setActActvLst(List<AccountActivityJSONBean> actActLst) {
		this.actActvLst = actActLst;
	}

	public String checkAccountFound() {
		if (actActvLst == null || actActvLst.size() == 0) {
			dispNoHistFnd = "Yes";
		}
		return dispNoHistFnd;

	}

	public String getDispNoHistFnd() {
		if (actActvLst == null || actActvLst.size() == 0) {
			dispNoHistFnd = "Yes";
		}
		return dispNoHistFnd;
	}

	public String getOpnBal() {
		return opnBal;
	}

	public void setOpnBal(String opnBal) {
		this.opnBal = opnBal;
	}

	public String getClsBal() {
		return clsBal;
	}

	public void setClsBal(String clsBal) {
		this.clsBal = clsBal;
	}

	public void setDispNoHistFnd(String dispNoHistFnd) {

		// this.displayNoHistoryFound = displayNoHistoryFound;
	}
}
