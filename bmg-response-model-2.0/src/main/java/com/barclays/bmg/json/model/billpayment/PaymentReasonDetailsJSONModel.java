package com.barclays.bmg.json.model.billpayment;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.PaymentDetailsDTO;
import com.barclays.bmg.dto.PaymentReasonDTO;
import com.barclays.bmg.dto.PaymentRsonDtlsDTO;

public class PaymentReasonDetailsJSONModel {

	private String payRsonKey;
	private String payRsonVal;
	private List<PaymentDetailsJSONModel> payDtlsLst;

	public List<PaymentDetailsJSONModel> getPayDtlsLst() {
		return payDtlsLst;
	}
	public void setPayDtlsLst(List<PaymentDetailsJSONModel> payDtlsLst) {
		this.payDtlsLst = payDtlsLst;
	}
	public String getPayRsonKey() {
		return payRsonKey;
	}
	public void setPayRsonKey(String payRsonKey) {
		this.payRsonKey = payRsonKey;
	}
	public String getPayRsonVal() {
		return payRsonVal;
	}
	public void setPayRsonVal(String payRsonVal) {
		this.payRsonVal = payRsonVal;
	}

	public PaymentReasonDetailsJSONModel(PaymentRsonDtlsDTO paymentRsonDtlsDTO){
		PaymentReasonDTO paymentReasonDTO = paymentRsonDtlsDTO.getPaymentReasonDTO();
		payRsonKey = paymentReasonDTO.getPayRsonKey();
		payRsonVal = paymentReasonDTO.getPayRsonValue();
		List<PaymentDetailsDTO> paymentDtlsLst =paymentRsonDtlsDTO.getPaymentDtlsLst();
		if(paymentDtlsLst!=null && !paymentDtlsLst.isEmpty()){
			payDtlsLst = new ArrayList<PaymentDetailsJSONModel>();
			for(PaymentDetailsDTO paymentDetailsDTO : paymentDtlsLst){
				PaymentDetailsJSONModel paymentDetailsJSONModel = new PaymentDetailsJSONModel();
				paymentDetailsJSONModel.setPayDtlsKey(paymentDetailsDTO.getPayDtlsKey());
				paymentDetailsJSONModel.setPayDtlsVal(paymentDetailsDTO.getPayDtlsValue());
				payDtlsLst.add(paymentDetailsJSONModel);
			}

		}
	}
}
