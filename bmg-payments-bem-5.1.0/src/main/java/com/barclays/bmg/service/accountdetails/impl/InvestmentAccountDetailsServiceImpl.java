package com.barclays.bmg.service.accountdetails.impl;

import com.barclays.bmg.dao.accountdetails.InvestmentAccountDetailsDAO;
import com.barclays.bmg.service.accountdetails.InvestmentAccountDetailsService;
import com.barclays.bmg.service.accountdetails.request.BondDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.request.MutualFundDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.request.StructuredNoteDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.BondDetailsServiceResponse;
import com.barclays.bmg.service.accountdetails.response.MutualFundDetailsServiceResponse;
import com.barclays.bmg.service.accountdetails.response.StructuredNoteDetailsServiceResponse;

public class InvestmentAccountDetailsServiceImpl implements InvestmentAccountDetailsService {

    InvestmentAccountDetailsDAO investmentAccountDetailsDAO;

    // ---- Service call for Mutual Funds
    public MutualFundDetailsServiceResponse retrieveMutualFundListByAssetClass(MutualFundDetailsServiceRequest request) {

	return investmentAccountDetailsDAO.retrieveMutualFundListByAssetClass(request);

    }

    // ---- Service call for Structured Notes
    public StructuredNoteDetailsServiceResponse retrieveStructuredNoteListByAssetClass(StructuredNoteDetailsServiceRequest request) {
	return investmentAccountDetailsDAO.retrieveStructuredNoteListByAssetClass(request);
    }

    // ---- Service call for Bonds
    public BondDetailsServiceResponse retrieveBondListByAssetClass(BondDetailsServiceRequest request) {
	return investmentAccountDetailsDAO.retrieveBondListByAssetClass(request);
    }

    public InvestmentAccountDetailsDAO getInvestmentAccountDetailsDAO() {
	return investmentAccountDetailsDAO;
    }

    public void setInvestmentAccountDetailsDAO(InvestmentAccountDetailsDAO investmentAccountDetailsDAO) {
	this.investmentAccountDetailsDAO = investmentAccountDetailsDAO;
    }

}
