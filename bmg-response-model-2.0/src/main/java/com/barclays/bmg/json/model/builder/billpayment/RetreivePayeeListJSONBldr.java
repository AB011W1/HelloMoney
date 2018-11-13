package com.barclays.bmg.json.model.builder.billpayment;

import java.util.Collections;
import java.util.List;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransferFacadeDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.CategorizedPayeeJSONModel;
import com.barclays.bmg.json.model.fundtransfer.PayeeJSONModel;
import com.barclays.bmg.json.model.fundtransfer.PayeeListJSONModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;
import com.barclays.bmg.sort.PayeeJSONModelComparator;

public class RetreivePayeeListJSONBldr extends BMBMultipleResponseJSONBuilder {

    @Override
    public Object createMultipleJSONResponse(ResponseContext... responseContexts) {
	BMBPayloadHeader bmbPayloadHeader = null;
	BMBPayload bmbPayload = null;
	for (ResponseContext response : responseContexts) {
	    if (response != null && !response.isSuccess()) {
		bmbPayloadHeader = createHeader(response, getResponseId(response.getTxnTyp()));
		break;
	    }
	}

	if (bmbPayloadHeader != null) {
	    bmbPayload = new BMBPayload(bmbPayloadHeader);
	} else {
	    ResponseContext response = responseContexts[0];
	    bmbPayload = new BMBPayload(createHeader(response, getResponseId(response.getTxnTyp())));
	    populatePayLoad(bmbPayload, responseContexts);
	}

	return bmbPayload;
    }

    protected void populatePayLoad(BMBPayload bmbPayload, ResponseContext... responseContexts) {
	PayeeListJSONModel payeeListJSONModel = null;
	RetrievePayeeListOperationResponse retrievePayeeListOperationResponse = (RetrievePayeeListOperationResponse) responseContexts[0];
	if (retrievePayeeListOperationResponse.isSuccess()) {
	    payeeListJSONModel = new PayeeListJSONModel();
	    List<CategorizedPayeeListDTO> categorizedPayeeList = retrievePayeeListOperationResponse.getCategorizedPayeeList();

	    for (CategorizedPayeeListDTO categorizedPayee : categorizedPayeeList) {
		CategorizedPayeeJSONModel categorizedPayeeJSONModel = new CategorizedPayeeJSONModel();
		categorizedPayeeJSONModel.setPayCat(categorizedPayee.getPayeeCategory());

		List<BeneficiaryDTO> payeeList = categorizedPayee.getPayeeList();
		if (payeeList != null) {
		    for (BeneficiaryDTO payee : payeeList) {
			PayeeJSONModel beneficiary = new PayeeJSONModel();
			beneficiary.setDisLbl(payee.getPayeeNickname());
			beneficiary.setAreaCode(payee.getBillRefNo2());
			beneficiary.setBillerId(payee.getBillerId());
			TransferFacadeDTO facadeDTO = new TransferFacadeDTO();
			facadeDTO.setBeneficiary(payee);
			facadeDTO.setTransactionFacadeRoutineType(categorizedPayee.getTransactionFacadeRoutineType());

			beneficiary.setTransferFacadeDTO(facadeDTO);
			categorizedPayeeJSONModel.add(beneficiary);
		    }
		}

		List<? extends CustomerAccountDTO> destAccountList = categorizedPayee.getDestAccountList();
		if (destAccountList != null) {
		    for (CustomerAccountDTO destAcct : destAccountList) {
			if (destAcct instanceof CreditCardAccountDTO) {

			    PayeeJSONModel beneficiary = new PayeeJSONModel();
			    beneficiary.setDisLbl(getMaskedCardNumber(((CreditCardAccountDTO) destAcct).getPrimary().getCardNumber())); // TODO Should
			    // be Masked
			    // Account
			    // Number
			    TransferFacadeDTO facadeDTO = new TransferFacadeDTO();
			    facadeDTO.setToAccount(destAcct);
			    facadeDTO.setTransactionFacadeRoutineType(categorizedPayee.getTransactionFacadeRoutineType());
			    beneficiary.setTransferFacadeDTO(facadeDTO);
			    categorizedPayeeJSONModel.add(beneficiary);
			}
		    }
		}
		Collections.sort(categorizedPayeeJSONModel.getBnfLst(), new PayeeJSONModelComparator());
		payeeListJSONModel.addCategorizedPayeeBean(categorizedPayeeJSONModel);
	    }
	}

	bmbPayload.setPayData(payeeListJSONModel);
    }

    /**
     * @param payeeType
     * @return Get the response id as per payee type
     */
    private String getResponseId(String payeeType) {

	String resId = ResponseIdConstants.RETRIEVE_BP_PAYEE_LIST_RESPONSE_ID;
	if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(payeeType)) {
	    resId = ResponseIdConstants.RETRIEVE_BP_PAYEE_LIST_RESPONSE_ID;
	}
	if (BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(payeeType)) {
	    resId = ResponseIdConstants.RETRIEVE_MTP_PAYEE_LIST_RESPONSE_ID;
	}

	if (BillPaymentConstants.PAYEE_TYPE_BARCLAY_CARD.equals(payeeType)) {
	    resId = ResponseIdConstants.RETRIEVE_BCD_PAYEE_LIST_RESPONSE_ID;
	}

	return resId;
    }

}
