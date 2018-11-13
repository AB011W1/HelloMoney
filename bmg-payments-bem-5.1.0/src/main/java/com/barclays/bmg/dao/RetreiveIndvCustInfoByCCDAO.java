package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.RetreiveIndvCustInfoServiceRequest;
import com.barclays.bmg.service.response.RetreiveIndvCustInfoServiceResponse;

public interface RetreiveIndvCustInfoByCCDAO {

	public RetreiveIndvCustInfoServiceResponse retreiveIndvCustByCCNumber(
			RetreiveIndvCustInfoServiceRequest request);


}
