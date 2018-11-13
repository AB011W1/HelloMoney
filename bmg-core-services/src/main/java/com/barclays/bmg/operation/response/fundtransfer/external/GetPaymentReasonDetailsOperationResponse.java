package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.PaymentRsonDtlsDTO;

public class GetPaymentReasonDetailsOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -1517073755249295422L;

    List<PaymentRsonDtlsDTO> paymentRsonDtlsLst;

    public List<PaymentRsonDtlsDTO> getPaymentRsonDtlsLst() {
	return paymentRsonDtlsLst;
    }

    public void setPaymentRsonDtlsLst(List<PaymentRsonDtlsDTO> paymentRsonDtlsLst) {
	this.paymentRsonDtlsLst = paymentRsonDtlsLst;
    }

}
