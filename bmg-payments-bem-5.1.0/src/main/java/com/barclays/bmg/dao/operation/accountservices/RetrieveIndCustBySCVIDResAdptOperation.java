package com.barclays.bmg.dao.operation.accountservices;

import java.util.HashMap;
import java.util.Map;

import com.barclays.bem.RetrieveIndividualCustBySCVID.CustomerIdentificationType;
import com.barclays.bem.RetrieveIndividualCustBySCVID.IndividualCustomerDetailsResponse;
import com.barclays.bem.RetrieveIndividualCustBySCVID.ProductProcessorDetails;
import com.barclays.bem.RetrieveIndividualCustBySCVID.RetrieveIndividualCustomerBySCVIDResponse;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;

public class RetrieveIndCustBySCVIDResAdptOperation {
	public static final String VALID_DOC_TYPE_CODE = "IP";

    public RetrieveIndCustBySCVIDServiceResponse adaptResponse(WorkContext workContext, Object obj) throws Exception {

	RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVIDServiceResponse = new RetrieveIndCustBySCVIDServiceResponse();
	ProductProcessorDetails[] productProcessorDetailArray = null;
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();
	RetrieveIndCustBySCVIDServiceRequest retrieveIndCustBySCVIDServiceRequest = (RetrieveIndCustBySCVIDServiceRequest) args[0];
	RetrieveIndividualCustomerBySCVIDResponse retrieveIndividualCustomerBySCVIDResponse = (RetrieveIndividualCustomerBySCVIDResponse) obj;
	Context context = retrieveIndCustBySCVIDServiceRequest.getContext();

	if (retrieveIndividualCustomerBySCVIDResponse != null
		&& retrieveIndividualCustomerBySCVIDResponse.getIndividualCustomerDetailsResponse() != null) {
	    IndividualCustomerDetailsResponse individualCustomerDetailsResponse = retrieveIndividualCustomerBySCVIDResponse
		    .getIndividualCustomerDetailsResponse();

	    productProcessorDetailArray = individualCustomerDetailsResponse.getProductProcessorDetails();
	    String firstName = individualCustomerDetailsResponse.getFirstName();
	    firstName = firstName == null ? "" : firstName;
	    String lastName = individualCustomerDetailsResponse.getLastName();
	    lastName = lastName == null ? "" : lastName;
	    // change made according to audit report data
	    if (null!=context  && ("GHBRB").equalsIgnoreCase(context.getBusinessId())&& ("Y").equals(context.getContextMap().get(SystemParameterConstant.isGHIPS2Flag))
				&& context.getActivityId().equals(ActivityConstant.MOBILE_WALLET_PAYEE_ACTIVITY_ID) && !context.getIsFreeDialUssdFlow().equalsIgnoreCase("TRUE")) {
	    	context.setFullName(firstName+" "+lastName);
	    }
	    else {
	    	context.setFullName(lastName);
	    }
	    retrieveIndCustBySCVIDServiceResponse.setFirstName(firstName);
	    retrieveIndCustBySCVIDServiceResponse.setLastName(lastName);

	    retrieveIndCustBySCVIDServiceResponse.setContext(context);
	}
	Map<String, String> ppMap = new HashMap<String, String>();

	// added on 17-2-2017 start

	ppMap.put("FirstName", retrieveIndCustBySCVIDServiceResponse.getFirstName());
	ppMap.put("LastName", retrieveIndCustBySCVIDServiceResponse.getLastName());
	CustomerIdentificationType cidTypeArr[]= null;
	if(null != retrieveIndividualCustomerBySCVIDResponse)
		cidTypeArr = retrieveIndividualCustomerBySCVIDResponse.getIndividualCustomerDetailsResponse().getCustomerIdentificationType();
	CustomerIdentificationType customerIdentificationType = null;
	if(cidTypeArr!=null){
		for(int i=0;i<cidTypeArr.length;i++){
			if(VALID_DOC_TYPE_CODE.contains(cidTypeArr[i].getCustomerIdentificationDocTypeCode())){
				customerIdentificationType=cidTypeArr[i];
				break;
			}
		}
	}
	String docType=null, docCode=null ;
	if(customerIdentificationType!=null){
		String dt=customerIdentificationType.getCustomerIdentificationDocTypeCode();
		docType=dt == null ? "" : dt;

		String dc=customerIdentificationType.getCustomerIdentificationNumber();
		docCode = dc == null ? "" : dc;
	}
	ppMap.put("docType", docType);
	ppMap.put("docCode", docCode);

	//TO Disable to Enable Credit Card in Application
	String disableCCFlag = (String) context.getContextMap().get(SystemParameterConstant.CREDIT_CARD_DISABLED_ALL);
	// End
	if (productProcessorDetailArray != null && productProcessorDetailArray.length > 0) {
	    for (int i = 0; i < productProcessorDetailArray.length; i++) {
	    	String ppType = productProcessorDetailArray[i].getProductProcessorTypeCode().getValue();
	    	if("Y".equalsIgnoreCase(disableCCFlag) && "CC".equalsIgnoreCase(ppType)) {
	    		continue;
	    	} else {
	    		ppMap.put(productProcessorDetailArray[i].getProductProcessorTypeCode().getValue(), productProcessorDetailArray[i]
	    				.getProductProcessorId());
	    	}
	    }
	    retrieveIndCustBySCVIDServiceResponse.setPpMap(ppMap);

	}

	return retrieveIndCustBySCVIDServiceResponse;

    }

}