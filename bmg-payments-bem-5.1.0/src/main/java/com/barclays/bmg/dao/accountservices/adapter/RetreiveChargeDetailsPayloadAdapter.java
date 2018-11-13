package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveChargeDetails.RetrieveChargeRequestInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;

public class RetreiveChargeDetailsPayloadAdapter {

	public RetrieveChargeRequestInfo adaptPayload(WorkContext workContext){

		RetrieveChargeRequestInfo requestBody = new RetrieveChargeRequestInfo();

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		RetreiveChargeDetailsServiceRequest retreiveChargeDetailsServiceRequest=
									(RetreiveChargeDetailsServiceRequest)args[0];
		requestBody.setActivityTypeCode(retreiveChargeDetailsServiceRequest.getChargeDetailTaskCode());

		if(null != retreiveChargeDetailsServiceRequest.getCurrency()){
			requestBody.setTransactionCurrencyCode(retreiveChargeDetailsServiceRequest.getCurrency());
	          if (retreiveChargeDetailsServiceRequest.getTxnAmt() != null) {
	        	  requestBody.setTransferAmount(retreiveChargeDetailsServiceRequest.getTxnAmt().doubleValue());
	          }

	      }
	      if (retreiveChargeDetailsServiceRequest.getFrmAcct() != null)  {
	    	  requestBody.setAccountNumber(retreiveChargeDetailsServiceRequest.getFrmAcct());
	      }
	      requestBody.setCustomerSegmentCode(retreiveChargeDetailsServiceRequest.getCustSegmentCode());
	      requestBody.setIsStaff(retreiveChargeDetailsServiceRequest.isStaff());

	      // CPB change 08/05/2017
	      String billerID = null;
	      if(retreiveChargeDetailsServiceRequest.getBillerID()!=null && retreiveChargeDetailsServiceRequest.getBillerID().contains("-")){
		      String billerCode[] = retreiveChargeDetailsServiceRequest.getBillerID().split("-");
		      billerID = billerCode[0];
	      }else{
	    	  billerID = retreiveChargeDetailsServiceRequest.getBillerID();
	      }
	      if(retreiveChargeDetailsServiceRequest.getContext().getBusinessId().equalsIgnoreCase("KEBRB") &&
	    		  (retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_FT_PESALINK") ||
	    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_FT_CS") ||
	    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_BP_MOBILE_WALLET_ONETIME") ||
	    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_BP_BILLPAY_PAYEE") ||
	    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_BP_BILLPAY_ONETIME") ||
	    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_FT_INTERNAL_PAYEE") ||
	    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_FT_INTERNAL_ONETIME"))){
	    	  requestBody.setBranchCode(retreiveChargeDetailsServiceRequest.getBranchCode());

	    	  if(retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_FT_PESALINK") ||
		    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_BP_MOBILE_WALLET_ONETIME") ||
		    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_BP_BILLPAY_PAYEE") ||
		    		   retreiveChargeDetailsServiceRequest.getContext().getActivityId().equals("PMT_BP_BILLPAY_ONETIME")){
		    	  requestBody.setBillerCode(billerID);
	    	  }

	      }
	      return requestBody;
	}

}
