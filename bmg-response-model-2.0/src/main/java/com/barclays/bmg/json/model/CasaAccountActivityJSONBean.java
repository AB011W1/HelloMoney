package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.AccountActivityListDTO;
import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.sort.CASATransactionActivityComparator;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CasaAccountActivityJSONBean extends BMBPayloadData implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2999032457369722004L;

	private List<AccountActivityJSONBean> actActvLst = new ArrayList<AccountActivityJSONBean>();

	protected String dispNoHistFnd = "No";
	private String opnBal = "";
	private String clsBal = "";

	public CasaAccountActivityJSONBean(AccountActivityListDTO activityListDTO) {
		super();
		if (activityListDTO != null) {

			List<AccountActivityDTO> list = activityListDTO.getActivityList();

			if (list != null) {

				CASATransactionActivityComparator comparator = new CASATransactionActivityComparator();
				Collections.sort(list, comparator);

				this.actActvLst = new ArrayList<AccountActivityJSONBean>(list
						.size());

				for (Iterator<AccountActivityDTO> iterator = list.iterator(); iterator
						.hasNext();) {
					AccountActivityDTO accountActivityDTO = iterator.next();
					this.actActvLst.add(new AccountActivityJSONBean(
							accountActivityDTO));
				}

				this.opnBal = BMGFormatUtility
						.getFormattedAmount(activityListDTO
								.getOpeningBalanceAmount());
				this.clsBal = BMGFormatUtility
						.getFormattedAmount(activityListDTO
								.getClosingBalanceAmount());
			}
		}
	}

	public CasaAccountActivityJSONBean() {
		super();
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

	public void setDispNoHistFnd(String displayNoHistoryFound) {

		// this.displayNoHistoryFound = displayNoHistoryFound;
	}
}
