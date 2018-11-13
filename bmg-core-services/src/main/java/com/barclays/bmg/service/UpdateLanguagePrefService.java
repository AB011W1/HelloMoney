package com.barclays.bmg.service;

import com.barclays.bmg.service.request.UpdateLanguagePrefServiceRequest;
import com.barclays.bmg.service.response.UpdateLanguagePrefServiceResponse;

/**
 * @author BTCI
 * 
 */
public interface UpdateLanguagePrefService {

    public UpdateLanguagePrefServiceResponse updateLanguagePref(UpdateLanguagePrefServiceRequest request);

}
