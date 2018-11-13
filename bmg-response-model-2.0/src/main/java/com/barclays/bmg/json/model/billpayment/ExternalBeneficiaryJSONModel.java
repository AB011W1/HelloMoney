package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.dto.BeneficiaryDTO;

public class ExternalBeneficiaryJSONModel implements Serializable {

	private static final long serialVersionUID = -5436515665377036572L;

	private String payId;
	private String payNckNam;
	private String beneNam;
	private String mkdActNo;
	private String actNo;
	private String addr;
	private String cntry;
	private ExternalBankJSONModel beneBank;
	private List<ExternalBankJSONModel> intBankLst;
	private String ibanFlg;
	private String resStatus;
	private String bnkCode;

	public ExternalBeneficiaryJSONModel(BeneficiaryDTO beneficiaryDTO) {
		this.payId = beneficiaryDTO.getPayeeId();
		this.payNckNam = beneficiaryDTO.getPayeeNickname();
		this.beneNam = beneficiaryDTO.getBeneficiaryName();
		this.actNo = beneficiaryDTO.getDestinationAccountNumber();
		this.bnkCode=beneficiaryDTO.getDestinationBankCode();
		StringBuffer benefiStringBuffer = null;

		String adrln1 = beneficiaryDTO.getDestinationAddressLine1();
		String adrln2 = beneficiaryDTO.getDestinationAddressLine2();

		String adrln3 = beneficiaryDTO.getDestinationAddressLine3();

		if (StringUtils.isNotEmpty(adrln1)) {
			benefiStringBuffer = new StringBuffer();
			benefiStringBuffer.append(adrln1);
		}

		if (StringUtils.isNotEmpty(adrln2)) {
			benefiStringBuffer.append(", ").append(adrln2);
		}

		if (StringUtils.isNotEmpty(adrln3)) {
			benefiStringBuffer.append(", ").append(adrln3);
		}
		if (benefiStringBuffer != null) {
			this.addr = benefiStringBuffer.toString();
		}

		this.cntry = beneficiaryDTO.getDestinationIsoCountryCode();

		ExternalBankJSONModel beneficiaBank = new ExternalBankJSONModel();
		beneficiaBank.setBnkNam(beneficiaryDTO.getDestinationBankName());
		beneficiaBank.setCntry(beneficiaryDTO
				.getDestinationBankIsoCountryCode());
		beneficiaBank.setSwftCde(beneficiaryDTO.getDestinationBankSwiftCode());
		beneficiaBank.setClrCode(beneficiaryDTO
				.getDestinationBankClearingCode());
		beneficiaBank.setClrNetCode(beneficiaryDTO
				.getDestinationBankClearingNetCode());
		beneficiaBank.setBrnchNam(beneficiaryDTO.getDestinationBranchName());
		benefiStringBuffer = null;

		adrln1 = beneficiaryDTO.getDestinationBankAddressLine1();
		adrln2 = beneficiaryDTO.getDestinationBankAddressLine2();

		if (StringUtils.isNotEmpty(adrln1)) {
			benefiStringBuffer = new StringBuffer();
			benefiStringBuffer.append(adrln1);
		}

		if (StringUtils.isNotEmpty(adrln2)) {
			benefiStringBuffer.append(", ").append(adrln2);
		}
		if (benefiStringBuffer != null) {
			beneficiaBank.setAddr(benefiStringBuffer.toString());
		}

		this.beneBank = beneficiaBank;

		if (beneficiaryDTO.getIntermediaryBankSwiftCode() != null) {
			this.intBankLst = new ArrayList<ExternalBankJSONModel>();
			// Intermidiate Banks
			ExternalBankJSONModel interBank = new ExternalBankJSONModel();
			interBank.setBnkNam(beneficiaryDTO.getIntermediaryBankName());
			interBank.setCntry(beneficiaryDTO
					.getIntermediaryBankIsoCountryCode());
			interBank.setSwftCde(beneficiaryDTO.getIntermediaryBankSwiftCode());
			interBank.setClrCode(beneficiaryDTO
					.getIntermediaryBankClearingCode());
			interBank.setClrNetCode(beneficiaryDTO
					.getIntermediaryBankClearingNetCode());

			benefiStringBuffer = null;

			adrln1 = beneficiaryDTO.getIntermediaryBankAddressLine1();
			adrln2 = beneficiaryDTO.getIntermediaryBankAddressLine2();

			if (StringUtils.isNotEmpty(adrln1)) {
				benefiStringBuffer = new StringBuffer();
				benefiStringBuffer.append(adrln1);
			}

			if (StringUtils.isNotEmpty(adrln2)) {
				benefiStringBuffer.append(", ").append(adrln2);
			}
			if (benefiStringBuffer != null) {
				interBank.setAddr(benefiStringBuffer.toString());
			}

			this.intBankLst.add(interBank);
		}
		this.ibanFlg = getValue(beneficiaryDTO.isIbanFlag());
	}

	private String getValue(boolean flag) {
		if (flag) {
			return "true";
		} else {
			return "false";
		}
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

	public String getMkdActNo() {
		return mkdActNo;
	}

	public void setMkdActNo(String mkdActNo) {
		this.mkdActNo = mkdActNo;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
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

	public ExternalBankJSONModel getBeneBank() {
		return beneBank;
	}

	public void setBeneBank(ExternalBankJSONModel beneBank) {
		this.beneBank = beneBank;
	}

	public List<ExternalBankJSONModel> getIntBankLst() {
		return intBankLst;
	}

	public void setIntBankLst(List<ExternalBankJSONModel> intBankLst) {
		this.intBankLst = intBankLst;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getIbanFlg() {
		return ibanFlg;
	}

	public void setIbanFlg(String ibanFlg) {
		this.ibanFlg = ibanFlg;
	}

	public String getResStatus() {
		return resStatus;
	}

	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}

	public String getBnkCode() {
		return bnkCode;
	}

	public void setBnkCode(String bnkCode) {
		this.bnkCode = bnkCode;
	}

}
