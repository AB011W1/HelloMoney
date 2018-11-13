package com.barclays.bmg.dao.operation.pesalink;


import org.apache.log4j.Logger;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.CreateIndividualCustomer.CreateIndividualCustomerResponse;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;
import com.barclays.bmg.service.response.pesalink.CreateIndividualCustomerServiceResponse;


public class CreateIndividualCustomerResAdptOperation extends AbstractResAdptOperation{

	    private static final Logger LOGGER = Logger.getLogger(CreateIndividualCustomerResAdptOperation.class);
		public CreateIndividualCustomerServiceResponse adaptResponse(WorkContext workContext, Object obj){
			CreateIndividualCustomerServiceResponse response = new CreateIndividualCustomerServiceResponse();
			CreateIndividualCustomerResponse bemResponse = (CreateIndividualCustomerResponse)obj;
			checkRespHeader(bemResponse.getResponseHeader(), response);

			if(response.isSuccess()){

				response.setSuccess(true);
				response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
				response.setTxnRefNo(bemResponse.getCustomerDetails().getCustomerCreateStatus().getTransactionReferenceNumber());
				LOGGER.info(" Entry CreateIndividualCustomerResAdptOperation adaptResponse checkResponseHeader");

			} else{
				response.setSuccess(false);
				if(response.getResCde().equals("09093"))
				{
					response.setResCde("BEMDEREG");
				}
				else
				response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
			}

			return response;
		}


		   private void checkRespHeader(BEMResHeader resHeader, ResponseContext response) {

		    	String resCode = resHeader.getServiceResStatus().getServiceResCode();
		    	if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
		    	    if (resHeader.getErrorList() != null && resHeader.getErrorList().length > 0) {

		    		for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {
		    		    response.setSuccess(false);
		    		    response.setResMsg(error.getErrorDesc());
		    		    response.setResCde(error.getErrorCode());

		    		    /*if (ErrorCodeConstant.BILLREG_REFNUM_EMPTY_ERR.equals(error.getErrorCode())) {
		    			response.setResCde(AddOrgBeneficiaryConstants.BILLER_REFNUM_EMPTY);
		    		    }
		    		    if (ErrorCodeConstant.BILLREG_NICK_EXISTS.equals(error.getErrorCode())) {
		    			response.setResCde(AddOrgBeneficiaryConstants.BILLER_NICKNAMEALREADY_EXISTS);
		    		    }
		    		    if (ErrorCodeConstant.BILLREG_BILLERID_REFERENCENO_EXISTS.equals(error.getErrorCode())) {
		    				response.setResCde(AddOrgBeneficiaryConstants.BILLERID_REFERENCENUMBER_ALREADYEXISTS);
		    			}*/
		    		}
		    	    }
		    	}
		    	if (response.isSuccess()) {
		    	    response.setSuccess(super.checkResponseHeader(resHeader));
		    	}
		        }





}


