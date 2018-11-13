package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.dto.InterestedProductDTO;
import com.barclays.ussd.dto.InterestedSubProductDTO;

public interface InterestedProductDAO {

    public List<InterestedProductDTO> getProductList(String businessId);

    public List<InterestedSubProductDTO> getSubProductList(String businessId, String productName);
}
