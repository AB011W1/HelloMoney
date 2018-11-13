package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.dto.LeadGenProductDTO;
import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;

public interface UssdBranchLocatorLookUpDAO {

    public List<UssdBranchLocatorLookUpDTO> getCityList(String businessId, String cityLetter);

    public List<UssdBranchLocatorLookUpDTO> getAreaList(String businessId, String cityName, String areaLetter);

    public List<UssdBranchLocatorLookUpDTO> getBranchList(String businessId, String branchLetter);

    public List<LeadGenProductDTO> getLeadProductList(String businessId,String langId);

	public List<LeadGenProductDTO> getLeadProductSubList(String businessId,String prodName,String langId);
}
