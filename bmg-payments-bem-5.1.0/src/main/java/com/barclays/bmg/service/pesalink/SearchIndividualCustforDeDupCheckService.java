package com.barclays.bmg.service.pesalink;


import com.barclays.bmg.service.request.pesalink.SearchIndividualCustforDeDupCheckServiceRequest;
import com.barclays.bmg.service.response.pesalink.SearchIndividualCustforDeDupCheckServiceResponse;




public interface SearchIndividualCustforDeDupCheckService {

    public SearchIndividualCustforDeDupCheckServiceResponse retrieveCustomerInfo(SearchIndividualCustforDeDupCheckServiceRequest request);
}


