package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.RetrieveBillDetailsDAO;
import com.barclays.bmg.service.RetrieveBillDetailsService;
import com.barclays.bmg.service.request.RetrieveBillDetailsServiceRequest;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;

public class RetrieveBillDetailsServiceImpl implements RetrieveBillDetailsService{

	private RetrieveBillDetailsDAO retrieveBillDetailsDAO;

	@Override
	public RetrieveBillDetailsServiceResponse retreiveBillDetails(RetrieveBillDetailsServiceRequest retreivePayeeListServiceRequest) {

		return retrieveBillDetailsDAO.retreiveBillDetails(retreivePayeeListServiceRequest);

	}

	/**
	 * @return the retrieveBillDetailsDAO
	 */
	public RetrieveBillDetailsDAO getRetrieveBillDetailsDAO() {
		return retrieveBillDetailsDAO;
	}

	/**
	 * @param retrieveBillDetailsDAO the retrieveBillDetailsDAO to set
	 */
	public void setRetrieveBillDetailsDAO(RetrieveBillDetailsDAO retrieveBillDetailsDAO) {
		this.retrieveBillDetailsDAO = retrieveBillDetailsDAO;
	}

}
