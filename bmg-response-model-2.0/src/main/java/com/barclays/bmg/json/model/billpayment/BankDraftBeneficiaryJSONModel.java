package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.dto.BeneficiaryDTO;

public class BankDraftBeneficiaryJSONModel implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = -5436515665377036572L;
	private String payId;
	private String payNckNam;
	private String beneNam;
	private String addr;
	private String cntry;
	private String contNo;

	public BankDraftBeneficiaryJSONModel(BeneficiaryDTO beneficiaryDTO){
		this.payId = beneficiaryDTO.getPayeeId();
		this.payNckNam = beneficiaryDTO.getPayeeNickname();
		this.beneNam = beneficiaryDTO.getBeneficiaryName();
		StringBuffer benefiStringBuffer = null;

		String adrln1 = beneficiaryDTO.getDestinationAddressLine1();
		String adrln2 = beneficiaryDTO.getDestinationAddressLine2();
		String adrln3 = beneficiaryDTO.getDestinationAddressLine3();

		if(StringUtils.isNotEmpty(adrln1)){
			benefiStringBuffer = new StringBuffer();
			benefiStringBuffer.append(adrln1);
		}

		if(StringUtils.isNotEmpty(adrln2)){
			benefiStringBuffer.append(", ").append(adrln2);
		}

		if(StringUtils.isNotEmpty(adrln3)){
			benefiStringBuffer.append(", ").append(adrln3);
		}
		if(benefiStringBuffer!=null){
		this.addr = benefiStringBuffer.toString();
		}

		this.cntry = beneficiaryDTO.getDestinationIsoCountryCode();

				benefiStringBuffer = null;

		adrln1 = beneficiaryDTO.getDestinationBankAddressLine1();
		adrln2 = beneficiaryDTO.getDestinationBankAddressLine2();

		if(StringUtils.isNotEmpty(adrln1)){
			benefiStringBuffer = new StringBuffer();
			benefiStringBuffer.append(adrln1);
		}

		if(StringUtils.isNotEmpty(adrln2)){
			benefiStringBuffer.append(", ").append(adrln2);
		}

		this.contNo=beneficiaryDTO.getContactNumber();


	}


	public String getPayNckNam() {
		return payNckNam;
	}
	public void setPayNckNam(String payNckNam) {
		this.payNckNam = payNckNam;
	}
	public String getBeneNam() {
		return beneNam;
	}
	public void setBeneNam(String beneNam) {
		this.beneNam = beneNam;
	}

	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCntry() {
		return cntry;
	}
	public void setCntry(String cntry) {
		this.cntry = cntry;
	}

	public String getPayId() {
		return payId;
	}


	public void setPayId(String payId) {
		this.payId = payId;
	}


	public String getContNo() {
		return contNo;
	}


	public void setContNo(String contNo) {
		this.contNo = contNo;
	}


}
