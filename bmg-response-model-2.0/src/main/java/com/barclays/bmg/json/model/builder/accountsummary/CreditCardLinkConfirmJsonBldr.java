package com.barclays.bmg.json.model.builder.accountsummary;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.DebitCardDTO;
import com.barclays.bmg.dto.InsuranceAccountDTO;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.dto.LoanAccountDTO;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.AccountJSONModel;
import com.barclays.bmg.json.model.AccountSummaryJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.CreditCardAccountJSONModel;
import com.barclays.bmg.json.model.CreditcardLinkConfirmationJsonModel;
import com.barclays.bmg.json.model.InsuranceAccountJSONModel;
import com.barclays.bmg.json.model.InvestmentAccountJSONModel;
import com.barclays.bmg.json.model.LoanAccountJSONModel;
import com.barclays.bmg.json.model.TDAccountJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.accounts.request.CreditCardLinkConfirmOperationResponse;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.ussd.auth.operation.response.RetrieveindividualCustCardListOperationResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CreditCardLinkConfirmJsonBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {

    	if (responseContext instanceof CreditCardLinkConfirmOperationResponse) {
    		CreditCardLinkConfirmOperationResponse response = (CreditCardLinkConfirmOperationResponse) responseContext;

    	    BMBPayload bmbPayload = new BMBPayload(createHeader(response));

    	    populatePayLoad(response, bmbPayload);

    	    return bmbPayload;
    	} else {
    	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
    	}

    }
    /**
     * This method createHeader has the purpose to create header for JSON response.
     *
     * @param CreditCardLinkConfirmOperationResponse
     * @return BMBPayloadHeader
     */
    protected BMBPayloadHeader createHeader(CreditCardLinkConfirmOperationResponse response) {

	BMBPayloadHeader header = new BMBPayloadHeader();
	if (response != null && response.isSuccess()) {
	    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
	    header.setResMsg("Succes CreditcardLink");

	} else if (response != null && !response.isSuccess()) {
	    header.setResCde(response.getResCde());
	    header.setResMsg(response.getResMsg());
	}
	header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

	return header;
    }

    /**
     * This method populatePayLoad has the purpose to create data for JSON response.
     *
     * @param CreditCardLinkConfirmOperationResponse
     * @param BMBPayload
     * @return void
     */
    protected void populatePayLoad(CreditCardLinkConfirmOperationResponse response, BMBPayload bmbPayload) {
    	CreditcardLinkConfirmationJsonModel responseModel = null;
    	if (response != null && response.isSuccess()) {
    		responseModel = new CreditcardLinkConfirmationJsonModel();
    		if(response.getCaseNumber()!=null){
    			responseModel.setCaseNumber(response.getCaseNumber());
    		}
    	}
    	bmbPayload.setPayData(responseModel);
    }


}
