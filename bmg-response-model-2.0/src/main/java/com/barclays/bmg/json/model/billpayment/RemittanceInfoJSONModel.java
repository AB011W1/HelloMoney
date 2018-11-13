package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class RemittanceInfoJSONModel implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -1075628793374499427L;
	private String part1;
	private String part2;
	private String part3;
	public String getPart1() {
		return part1;
	}
	public void setPart1(String part1) {
		this.part1 = part1;
	}
	public String getPart2() {
		return part2;
	}
	public void setPart2(String part2) {
		this.part2 = part2;
	}
	public String getPart3() {
		return part3;
	}
	public void setPart3(String part3) {
		this.part3 = part3;
	}

}
