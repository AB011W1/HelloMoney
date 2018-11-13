package com.barclays.bmg.service.impl.pesalink;

import com.barclays.bmg.dao.ReportProblemDAO;
import com.barclays.bmg.dao.pesalink.SearchIndividualCustforDeDupCheckDAO;
import com.barclays.bmg.service.pesalink.SearchIndividualCustforDeDupCheckService;
import com.barclays.bmg.service.request.pesalink.SearchIndividualCustforDeDupCheckServiceRequest;
import com.barclays.bmg.service.response.pesalink.SearchIndividualCustforDeDupCheckServiceResponse;



public class SearchIndividualCustforDeDupCheckServiceImpl  implements SearchIndividualCustforDeDupCheckService{

	private SearchIndividualCustforDeDupCheckDAO searchIndividualCustforDeDupCheckDAO;

	@Override
	public SearchIndividualCustforDeDupCheckServiceResponse retrieveCustomerInfo(SearchIndividualCustforDeDupCheckServiceRequest request) {


		SearchIndividualCustforDeDupCheckServiceResponse searchIndividualCustforDeDupCheckServiceResponse=searchIndividualCustforDeDupCheckDAO.retrieveCustomerInfo(request);


		return searchIndividualCustforDeDupCheckServiceResponse;
	}

	public SearchIndividualCustforDeDupCheckDAO getSearchIndividualCustforDeDupCheckDAO() {
		return searchIndividualCustforDeDupCheckDAO;
	}

	public void setSearchIndividualCustforDeDupCheckDAO(
			SearchIndividualCustforDeDupCheckDAO searchIndividualCustforDeDupCheckDAO) {
		this.searchIndividualCustforDeDupCheckDAO = searchIndividualCustforDeDupCheckDAO;
	}




}
