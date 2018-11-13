package com.barclays.bmg.dao.accountdetails;

import com.barclays.bmg.service.accountdetails.request.BondDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.request.MutualFundDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.request.StructuredNoteDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.BondDetailsServiceResponse;
import com.barclays.bmg.service.accountdetails.response.MutualFundDetailsServiceResponse;
import com.barclays.bmg.service.accountdetails.response.StructuredNoteDetailsServiceResponse;

public interface InvestmentAccountDetailsDAO {

    public MutualFundDetailsServiceResponse retrieveMutualFundListByAssetClass(MutualFundDetailsServiceRequest request);

    public StructuredNoteDetailsServiceResponse retrieveStructuredNoteListByAssetClass(StructuredNoteDetailsServiceRequest request);

    public BondDetailsServiceResponse retrieveBondListByAssetClass(BondDetailsServiceRequest request);

}
