package com.barclays.bmg.json.response;

import java.io.Serializable;

public class BMBHeader implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -1291658559237528242L;
	String resHdrCde;
	String resHdrMsg;

	public BMBHeader() {
		// TODO Auto-generated constructor stub
	}

	public BMBHeader(String resHdrCde) {
		// TODO Auto-generated constructor stub
		this.resHdrCde = resHdrCde;
	}

	public BMBHeader(String resHdrCde, String resHdrMsg) {
		// TODO Auto-generated constructor stub
		this.resHdrCde = resHdrCde;
		this.resHdrMsg = resHdrMsg;
	}


	public String getResHdrCde() {
		return resHdrCde;
	}
	public void setResHdrCde(String resHdrCde) {
		this.resHdrCde = resHdrCde;
	}
	public String getResHdrMsg() {
		return resHdrMsg;
	}
	public void setResHdrMsg(String resHdrMsg) {
		this.resHdrMsg = resHdrMsg;
	}


}
