package com.barclays.bmg.dao.adapter.pesalink;


import java.util.Map;

import com.barclays.bem.CreateIndividualCustomer.CreateCustomerInfo;
import com.barclays.bem.CreateIndividualCustomer.CreateIndividualCustomerRequest;
import com.barclays.bem.CreateIndividualCustomer.CustomerIdentificationDocInfo;
import com.barclays.bem.CreateIndividualCustomer.CustomerInfo;
import com.barclays.bem.CreateIndividualCustomer.IndividualName;
import com.barclays.bem.IndividualCustomer.IndividualCustomer;
import com.barclays.bem.IndividualCustomerBasic.IndividualCustAdditionalInfo;
import com.barclays.bem.IndividualIDDoc.IndividualIDDoc;
import com.barclays.bem.RetrieveIndividualCustBySCVID.CustomerIdentificationType;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.pesalink.CreateIndividualCustomerServiceRequest;


public class CreateIndividualCustomerPayloadAdapter {


		public CustomerInfo customerInfoAdaptPayload(WorkContext workContext){

			DAOContext daoContext = (DAOContext)workContext;
			Object[] args = daoContext.getArguments();

			CreateIndividualCustomerServiceRequest request=
				(CreateIndividualCustomerServiceRequest)args[0];

			CreateIndividualCustomerRequest requestBody = new CreateIndividualCustomerRequest();
			CustomerInfo customerInfo=new CustomerInfo();
			String mobileNumber=request.getMobileNumber();

			String countryCode="254";

			if(mobileNumber.startsWith("0"))
			{
				mobileNumber=mobileNumber.substring(1);
			}
				mobileNumber=countryCode+mobileNumber;
			//
				Map<String,String> ppMap=request.getContext().getPpMap();
				String fName=ppMap.get("FirstName");
				String lName=ppMap.get("LastName");
							//
			customerInfo.setMobileNumber(mobileNumber);
			IndividualName individualName=new IndividualName();
			//individualName.setFullName(request.getContext().getFullName());
			individualName.setFullName(fName+" "+lName);
			customerInfo.setIndividualName(individualName);
              return customerInfo;


		}

		public CreateCustomerInfo CustomerDetailsAdaptPayload(WorkContext workContext){

			DAOContext daoContext = (DAOContext)workContext;
			Object[] args = daoContext.getArguments();

			CreateIndividualCustomerServiceRequest request=
				(CreateIndividualCustomerServiceRequest)args[0];

			CreateCustomerInfo createCustomerInfo=new CreateCustomerInfo();
			IndividualCustomer  individualCustomer=new IndividualCustomer();
			IndividualCustAdditionalInfo individualCustAdditionalInfo=new IndividualCustAdditionalInfo();

			individualCustAdditionalInfo.setCustomerAccountNumber(request.getAccountNumber());
			individualCustAdditionalInfo.setPrimaryAccountHolderFlag(request.getPrimaryFlag());
			individualCustomer.setIndividualCustAdditionalInfo(individualCustAdditionalInfo);

			// Kits Updations
			Map<String,String> ppMap=request.getContext().getPpMap();
			String dType=ppMap.get("docType");
			String dCode=ppMap.get("docCode");

			IndividualIDDoc docTypeAndNumber=new IndividualIDDoc();
			docTypeAndNumber.setCustomerIdentificationDocTypeCode(dType);
			docTypeAndNumber.setCustomerIdentificationNumber(dCode);

			IndividualIDDoc individualIDDoc[]=new IndividualIDDoc[]{docTypeAndNumber};

			individualCustomer.setIdentificationDocument(individualIDDoc);
			//

			createCustomerInfo.setCustomer(individualCustomer);

			return createCustomerInfo;
		}



}
