package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.UpdateLanguagePrefDAO;
import com.barclays.bmg.service.UpdateLanguagePrefService;
import com.barclays.bmg.service.request.UpdateLanguagePrefServiceRequest;
import com.barclays.bmg.service.response.UpdateLanguagePrefServiceResponse;

/**
 * @author BTCI
 * 
 */
public class UpdateLanguagePrefServiceImpl implements UpdateLanguagePrefService {

    private UpdateLanguagePrefDAO updateLanguagePrefDAO;

    /**
     * @param updateLanguagePrefDAO
     */
    public void setUpdateLanguagePrefDAO(UpdateLanguagePrefDAO updateLanguagePrefDAO) {
	this.updateLanguagePrefDAO = updateLanguagePrefDAO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.service.UpdateLanguagePrefService#updateLanguagePref(com.barclays.bmg.service.request.UpdateLanguagePrefServiceRequest)
     */
    @Override
    public UpdateLanguagePrefServiceResponse updateLanguagePref(UpdateLanguagePrefServiceRequest request) {

	UpdateLanguagePrefServiceResponse updateLanguagePrefServiceResponse = updateLanguagePrefDAO.updateLanguagePref(request);
	return updateLanguagePrefServiceResponse;
    }

}
