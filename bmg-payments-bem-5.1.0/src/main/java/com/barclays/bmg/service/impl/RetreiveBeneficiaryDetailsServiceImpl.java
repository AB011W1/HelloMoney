package com.barclays.bmg.service.impl;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.dao.RetreiveIndividualBeneficiaryDetailsDAO;
import com.barclays.bmg.dao.RetreiveOrganizationBeneficiaryDetailsDAO;
import com.barclays.bmg.service.RetreiveBeneficiaryDetailsService;
import com.barclays.bmg.service.request.RetreiveBeneficiaryDetailsServiceRequest;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;

public class RetreiveBeneficiaryDetailsServiceImpl implements RetreiveBeneficiaryDetailsService {

	private RetreiveOrganizationBeneficiaryDetailsDAO retreiveOrganizationBeneficiaryDetailsDAO;
	private RetreiveIndividualBeneficiaryDetailsDAO retreiveIndividualBeneficiaryDetailsDAO;
	@Override
	public RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetails(
			RetreiveBeneficiaryDetailsServiceRequest request) {

		RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetailsServiceResponse = null;
		if(request.getPayeeType() !=null && BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT.equals(request.getPayeeType())
				||BillPaymentConstants.PAYEE_TYPE_MOBILE_TOPUP.equals(request.getPayeeType())){
			retreiveBeneficiaryDetailsServiceResponse =
				retreiveOrganizationBeneficiaryDetails(request);
		}else{
			retreiveBeneficiaryDetailsServiceResponse =
				retreiveIndividualBeneficiaryDetails(request);
		}

		return retreiveBeneficiaryDetailsServiceResponse;
	}

	private RetreiveBeneficiaryDetailsServiceResponse retreiveOrganizationBeneficiaryDetails(
			RetreiveBeneficiaryDetailsServiceRequest request){
		return retreiveOrganizationBeneficiaryDetailsDAO.retreiveOrganizationBeneficiaryDetails(request);
	}


	private RetreiveBeneficiaryDetailsServiceResponse retreiveIndividualBeneficiaryDetails(
			RetreiveBeneficiaryDetailsServiceRequest request){
		return retreiveIndividualBeneficiaryDetailsDAO.retreiveIndividualBeneficiaryDetails(request);
	}


	public RetreiveOrganizationBeneficiaryDetailsDAO getRetreiveOrganizationBeneficiaryDetailsDAO() {
		return retreiveOrganizationBeneficiaryDetailsDAO;
	}

	public void setRetreiveOrganizationBeneficiaryDetailsDAO(
			RetreiveOrganizationBeneficiaryDetailsDAO retreiveOrganizationBeneficiaryDetailsDAO) {
		this.retreiveOrganizationBeneficiaryDetailsDAO = retreiveOrganizationBeneficiaryDetailsDAO;
	}

	public RetreiveIndividualBeneficiaryDetailsDAO getRetreiveIndividualBeneficiaryDetailsDAO() {
		return retreiveIndividualBeneficiaryDetailsDAO;
	}

	public void setRetreiveIndividualBeneficiaryDetailsDAO(
			RetreiveIndividualBeneficiaryDetailsDAO retreiveIndividualBeneficiaryDetailsDAO) {
		this.retreiveIndividualBeneficiaryDetailsDAO = retreiveIndividualBeneficiaryDetailsDAO;
	}

}
