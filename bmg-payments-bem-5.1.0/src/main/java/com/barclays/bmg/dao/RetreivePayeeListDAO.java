package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.RetreivePayeeListServiceRequest;
import com.barclays.bmg.service.response.RetreivePayeeListServiceResponse;

public interface RetreivePayeeListDAO {
	public RetreivePayeeListServiceResponse retreivePayeeList(RetreivePayeeListServiceRequest request);
}
