package com.barclays.bmg.dto;

import java.util.ArrayList;
import java.util.List;

public class PaymentRsonDtlsDTO {

    private PaymentReasonDTO paymentReasonDTO;
    private List<PaymentDetailsDTO> paymentDtlsLst;

    public PaymentRsonDtlsDTO() {
	paymentDtlsLst = new ArrayList<PaymentDetailsDTO>();
    }

    public PaymentReasonDTO getPaymentReasonDTO() {
	return paymentReasonDTO;
    }

    public void setPaymentReasonDTO(PaymentReasonDTO paymentReasonDTO) {
	this.paymentReasonDTO = paymentReasonDTO;
    }

    public List<PaymentDetailsDTO> getPaymentDtlsLst() {
	return paymentDtlsLst;
    }

    public void setPaymentDtlsLst(List<PaymentDetailsDTO> paymentDtlsLst) {
	this.paymentDtlsLst = paymentDtlsLst;
    }

    public void add(PaymentDetailsDTO paymentDetailsDTO) {
	paymentDtlsLst.add(paymentDetailsDTO);
    }

}
