package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.UpdateLanguagePrefServiceRequest;
import com.barclays.bmg.service.response.UpdateLanguagePrefServiceResponse;

/**
 * @author BTCI
 * 
 */
public interface UpdateLanguagePrefDAO {

    public UpdateLanguagePrefServiceResponse updateLanguagePref(UpdateLanguagePrefServiceRequest request);

}
