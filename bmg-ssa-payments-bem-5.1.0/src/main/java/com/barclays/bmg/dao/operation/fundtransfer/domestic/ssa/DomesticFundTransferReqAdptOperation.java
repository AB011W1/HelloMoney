package com.barclays.bmg.dao.operation.fundtransfer.domestic.ssa;

import java.util.Map;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.OverrideDetails;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bem.MakeDomesticFundTransfer.MakeDomesticFundTransferRequest;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.adapter.fundtransfer.domestic.ssa.DomesticFundTransferPayloadAdapter;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;

public class DomesticFundTransferReqAdptOperation extends AbstractReqAdptOperation {
	private DomesticFundTransferPayloadAdapter domesticFundTransferPayloadAdapter;

	public MakeDomesticFundTransferRequest adaptRequestForFundTransfer(WorkContext workContext){

		MakeDomesticFundTransferRequest fundTransferRequest = new MakeDomesticFundTransferRequest();

		BEMReqHeader reqHeader = buildRequestHeader(workContext, ServiceIdConstants.SERVICE_MAKE_DOMESTIC_FUND_TRANSFER);

	 	DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		DomesticFundTransferServiceRequest domesticFTRequest =(DomesticFundTransferServiceRequest)args[0];

		if (domesticFTRequest.getSourceAcct() instanceof CreditCardAccountDTO) {
		//Setting staff id as IFM
		RequestContext request = (RequestContext) args[0];
		Context context = request.getContext();
	    Map<String, Object> contextMap = context.getContextMap();
	    reqHeader.getBankUserContext().setStaffID((contextMap.get(SystemParameterConstant.SERVICE_HEADER_STAFF_ID_CC).toString()));
	    reqHeader.getConsumerContext().setBranchCode("021");
	    reqHeader.setOverrideList(createOverrideList(context));
	    //Set VP as routing indicator
		RoutingIndicator routInd = new RoutingIndicator();
		routInd.setIndicator(CommonConstants.ROUTING_INDICATOR);
		reqHeader.setRoutingIndicator(routInd);

	    }

		fundTransferRequest.setRequestHeader(reqHeader);
		fundTransferRequest.setDomesticFundTransferInfo(domesticFundTransferPayloadAdapter.adaptPayload(workContext));

		return fundTransferRequest;
	}

	public DomesticFundTransferPayloadAdapter getDomesticFundTransferPayloadAdapter() {
		return domesticFundTransferPayloadAdapter;
	}

	public void setDomesticFundTransferPayloadAdapter(
			DomesticFundTransferPayloadAdapter domesticFundTransferPayloadAdapter) {
		this.domesticFundTransferPayloadAdapter = domesticFundTransferPayloadAdapter;
	}

	private OverrideDetails[] createOverrideList(Context context) {

	    	OverrideDetails[] overrideDetailsArray= new OverrideDetails[1];
	    	OverrideDetails overrideDetails = new OverrideDetails();
	    	overrideDetails.setCode("FT");
	    	overrideDetails.setDetails("Kadi Kope CC to CASA");
	    	overrideDetails.setSource("SHM");
	    	overrideDetailsArray[0]=overrideDetails;

	    	return overrideDetailsArray;
	 }

}
