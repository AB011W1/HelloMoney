package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.dto.BusinessAreaLookUpDTO;

public interface CallMeBackLookUpDAO {

    public List<BusinessAreaLookUpDTO> getBusinessAreaList(String businessId);

    public List<BusinessAreaLookUpDTO> getServiceCategoryList(String businessId, String businessArea);

}
