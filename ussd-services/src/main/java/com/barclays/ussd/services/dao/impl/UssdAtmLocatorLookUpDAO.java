package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;

public interface UssdAtmLocatorLookUpDAO {

    public List<UssdBranchLocatorLookUpDTO> getCityList(String businessId, String cityLetter);

    public List<UssdBranchLocatorLookUpDTO> getAreaList(String businessId, String cityName, String areaLetter);
}
