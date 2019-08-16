package com.barclays.bmg.ussd.dao.operation;

import java.util.Map;

import com.barclays.bem.SearchIndividualCustByAcct.CustomerAccountInfo;
import com.barclays.bem.SearchIndividualCustByAcct.IndividualCustomerBasic;
import com.barclays.bem.SearchIndividualCustByAcct.SearchIndividualCustByAcctResponse;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationExecutionServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.SelfRegistrationExecutionServiceResponse;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionResAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SelfRegistrationExecutionResAdptOperation extends AbstractResAdptOperationAcct {

    /**
     * This method adaptResponseForRegistrationExecution has the purpose to adapt the response for registration execution.
     *
     * @param WorkContext
     * @param Object
     * @return SelfRegistrationExecutionServiceResponse
     */
    public SelfRegistrationExecutionServiceResponse adaptResponseForRegistrationExecution(WorkContext workContext, Object obj) {

	SelfRegistrationExecutionServiceResponse response = new SelfRegistrationExecutionServiceResponse();
	SearchIndividualCustByAcctResponse bemResponse = (SearchIndividualCustByAcctResponse) obj;

	if (bemResponse != null) {

	    String resCde = checkBEMAuthResponse(bemResponse.getResponseHeader());

	    if (ResponseCodeConstants.SUCCESS_RES_CODE.equals(resCde)) {
		IndividualCustomerBasic individualCustomerBasic[] = bemResponse.getIndividualCustomerList();

		String scvid = null;
		String bankCIF = null;
		String recipientName="";
		if (individualCustomerBasic != null && individualCustomerBasic[0] != null) {
		//Added so as to involve self registration for AUS customers in KE start
			boolean isAUSAccountPresent=false;

			DAOContext daoContext=(DAOContext)workContext;

			Object[] requestObject=daoContext.getArguments();
			SelfRegistrationExecutionServiceRequest selfRegistrationExecutionServiceRequest=(SelfRegistrationExecutionServiceRequest)requestObject[0];
			Context context=selfRegistrationExecutionServiceRequest.getContext();
			  Map<String, Object> contextMap = context.getContextMap();
			  if(context.getBusinessId().equalsIgnoreCase(CommonConstants.KEBRB_BUSINESS_ID)){
			  String ACCOUNTTYPE_AUS = contextMap.get(SystemParameterConstant.SELFREG_ACCOUNTTYPE_AUS) != null? contextMap.get(SystemParameterConstant.SELFREG_ACCOUNTTYPE_AUS).toString() : "";

			  for(IndividualCustomerBasic individualCustomerBasicObject:individualCustomerBasic){
				for(CustomerAccountInfo customerAccountInfo:individualCustomerBasicObject.getCustomerAccountInfo()){
					if(customerAccountInfo.getAccountRelationshipTypeCode().equals(ACCOUNTTYPE_AUS)){
						isAUSAccountPresent=true;
						scvid=individualCustomerBasicObject.getSCVID();
						//-------NEED TO CHECK Whether we have to put 0 or not--------
						bankCIF=individualCustomerBasicObject.getProductProcessorDetails(0).getProductProcessorId();
					}
				}
			  }
			}
			if(!isAUSAccountPresent){
				//Added so as to involve self registration for AUS customers in KE end
		    scvid = individualCustomerBasic[0].getSCVID();
		   // bankCIF = individualCustomerBasic[0].getCustomerNumber();
		    //Fetching BankCif from PPID field as Self registration not working for eBox Countries
		    if(individualCustomerBasic[0].getProductProcessorDetails()!=null)
		    	bankCIF=individualCustomerBasic[0].getProductProcessorDetails(0).getProductProcessorId();
			}

			if(context.getBusinessId().equalsIgnoreCase(CommonConstants.GHBRB_BUSINESS_ID)){
				for(IndividualCustomerBasic individualCustomerBasicObject:individualCustomerBasic){
					if(individualCustomerBasicObject.getIndividualName()!=null)
						recipientName=individualCustomerBasicObject.getIndividualName().getFullName();
				}
			}
		}

		response.setBankCIF(bankCIF);
		response.setScvid(scvid);
		response.setServiceResponse(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
		response.setRegistered(true);
		response.setSuccess(true);
		response.setResCde(resCde);
		response.setRecipientName(recipientName);

	    } else {
		response.setSuccess(false);
		response.setResCde(resCde);
	    }

	} else {
	    response.setSuccess(false);
	}

	return response;
    }

}
