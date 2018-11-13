package com.barclays.bmg.ussd.dao;

import com.barclays.bmg.service.request.RetrieveindividualCustCardListServiceRequest;
import com.barclays.bmg.service.response.RetrieveindividualCustCardListServiceResponse;

public interface RetrieveindividualCustCardListDAO {
	 public RetrieveindividualCustCardListServiceResponse retrieveCustCardList(RetrieveindividualCustCardListServiceRequest request);
}
