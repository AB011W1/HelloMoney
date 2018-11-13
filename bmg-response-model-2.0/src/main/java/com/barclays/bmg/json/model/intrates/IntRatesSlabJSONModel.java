package com.barclays.bmg.json.model.intrates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.TDIntratesTenureJSONModel;

public class IntRatesSlabJSONModel implements Serializable{

	private static final long serialVersionUID = 1566012186131553411L;

	private AmountJSONModel from;
	private AmountJSONModel to;

	private List<TDIntratesTenureJSONModel> tenure = new ArrayList <TDIntratesTenureJSONModel>();

	//private TDIntratesTenureJSONModel tdIntratesTenureJSONModel;

	/*public IntRatesSlabJSONModel(IntrateDTO intrateDTO) {


		this.from = BMGFormatUtility.getJSONAmount(intrateDTO.getCcy(),
				BMGFormatUtility.getFormattedAmount(intrateDTO.getFrom()));

		this.to = BMGFormatUtility.getJSONAmount(intrateDTO.getCcy(),
				BMGFormatUtility.getFormattedAmount(intrateDTO.getTo()));

		this.tenure.add(new TDIntratesTenureJSONModel(intrateDTO));

		//if (intrateDTO.getFrom().equals(this.from.getAmt())) {

		//}

		// this.tenDay = intrateDTO.getTenureDay();

		this.from = BMGFormatUtility.getJSONAmount(intrateDTO.getCcy(),
				BMGFormatUtility.getFormattedAmount(intrateDTO.getFrom()));

		this.to = BMGFormatUtility.getJSONAmount(intrateDTO.getCcy(),
				BMGFormatUtility.getFormattedAmount(intrateDTO.getTo()));

		// this.intrate = intrateDTO.getIntrate().toString();


		 * if (intrateDTO.getIntvar() != null) { this.intvar = BMGFormatUtility
		 * .getJSONAmount(intrateDTO.getCcy(), BMGFormatUtility
		 * .getFormattedAmount(intrateDTO.getIntvar())); }
		 *
		 * if (intrateDTO.getPanrate() != null) { this.panrate =
		 * BMGFormatUtility.getJSONAmount(intrateDTO.getCcy(), BMGFormatUtility
		 * .getFormattedAmount(intrateDTO.getPanrate())); }
		 *
		 * if (intrateDTO.getPanvar() != null) { this.panvar = BMGFormatUtility
		 * .getJSONAmount(intrateDTO.getCcy(), BMGFormatUtility
		 * .getFormattedAmount(intrateDTO.getPanvar())); }


	}*/

	/*
	 * public String getBusID() { return busID; }
	 *
	 * public void setBusID(String busID) { this.busID = busID; }
	 */

	public AmountJSONModel getFrom() {
		return from;
	}

	public void setFrom(AmountJSONModel from) {
		this.from = from;
	}

	public AmountJSONModel getTo() {
		return to;
	}

	public void setTo(AmountJSONModel to) {
		this.to = to;
	}

	public List<TDIntratesTenureJSONModel> getTenure() {
		return tenure;
	}

	public void setTenure(List<TDIntratesTenureJSONModel> tenure) {
		this.tenure = tenure;
	}






}
