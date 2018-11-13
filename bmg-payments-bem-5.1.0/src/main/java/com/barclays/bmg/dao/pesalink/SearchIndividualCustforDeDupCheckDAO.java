package com.barclays.bmg.dao.pesalink;


import com.barclays.bmg.service.request.pesalink.SearchIndividualCustforDeDupCheckServiceRequest;
import com.barclays.bmg.service.response.pesalink.SearchIndividualCustforDeDupCheckServiceResponse;



public interface SearchIndividualCustforDeDupCheckDAO {


	 public SearchIndividualCustforDeDupCheckServiceResponse retrieveCustomerInfo(SearchIndividualCustforDeDupCheckServiceRequest request);

}
