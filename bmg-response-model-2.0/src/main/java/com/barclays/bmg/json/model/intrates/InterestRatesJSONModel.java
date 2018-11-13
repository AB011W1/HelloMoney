package com.barclays.bmg.json.model.intrates;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.model.AccountJSONModel;

public class InterestRatesJSONModel extends AccountJSONModel {

	private static final long serialVersionUID = 1566012186131553411L;

	private String prodCode;
	private String prodDesc;

	private List<IntRatesSlabJSONModel> intRateSlab = new ArrayList<IntRatesSlabJSONModel>();

	/*public InterestRatesJSONModel(IntrateDTO intrateDTO) {

	//	if(!intrateDTO.getProductCode().toString().equals()) {
			this.prodCode = intrateDTO.getProductCode();
			this.prodDesc = intrateDTO.getProdDesc();
		//}


		//if (this.prodCode.equals())) {
			this.intRateSlab.add(new IntRatesSlabJSONModel(intrateDTO));
		//}

		// this.tenType = intrateDTO.getTenureType();

		// this.busID = intrateDTO.getBusinessID();


		 * this.effDtm = intrateDTO.getEffectiveDtm();
		 *
		 * this.ccy = intrateDTO.getCcy();
		 *
		 * this.channel = intrateDTO.getChannel();
		 *
		 * this.custSeg = intrateDTO.getCustSeg();
		 *
		 * if (intrateDTO.getFrom() != null) {
		 * this.tenure.setTenDay(intrateDTO.getTenureDay().toString());
		 * this.tenure.setTenMonth(intrateDTO.getTenureMonth().toString());
		 * this.tenure.setIntrate(intrateDTO.getIntrate().toString());
		 * this.tenure.setTenType(intrateDTO.getTenureType().toString()); }
		 *
		 * // this.tenDay = intrateDTO.getTenureDay();
		 *
		 * this.from = BMGFormatUtility.getJSONAmount(intrateDTO.getCcy(),
		 * BMGFormatUtility.getFormattedAmount(intrateDTO.getFrom()));
		 *
		 * this.to = BMGFormatUtility.getJSONAmount(intrateDTO.getCcy(),
		 * BMGFormatUtility.getFormattedAmount(intrateDTO.getTo()));
		 *
		 * // this.intrate = intrateDTO.getIntrate().toString();
		 *
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

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public List<IntRatesSlabJSONModel> getIntRateSlab() {
		return intRateSlab;
	}

	public void setIntRateSlab(List<IntRatesSlabJSONModel> intRateSlab) {
		this.intRateSlab = intRateSlab;
	}



}
