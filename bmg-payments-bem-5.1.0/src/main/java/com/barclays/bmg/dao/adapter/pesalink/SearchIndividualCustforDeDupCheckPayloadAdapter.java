package com.barclays.bmg.dao.adapter.pesalink;


import com.barclays.bem.SearchIndividualCustInformation.IndividualCustomerSearch;
import com.barclays.bem.SearchIndividualCustInformation.SearchIndividualCustomerInformationRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.pesalink.SearchIndividualCustforDeDupCheckServiceRequest;


public class SearchIndividualCustforDeDupCheckPayloadAdapter {


		public IndividualCustomerSearch adaptPayload(WorkContext workContext){

			DAOContext daoContext = (DAOContext)workContext;
			Object[] args = daoContext.getArguments();

			SearchIndividualCustforDeDupCheckServiceRequest request=
				(SearchIndividualCustforDeDupCheckServiceRequest)args[0];

			SearchIndividualCustomerInformationRequest requestBody = new SearchIndividualCustomerInformationRequest();

			IndividualCustomerSearch individualCustomerSearch=new IndividualCustomerSearch();
			String mobileNumber=request.getMobileNumber();

			String countryCode="254";

			if(mobileNumber.startsWith("0"))
			{
				mobileNumber=mobileNumber.substring(1);
			}
				mobileNumber=countryCode+mobileNumber;


			individualCustomerSearch.setMobileNumber(mobileNumber);


			return individualCustomerSearch;

		}




}
