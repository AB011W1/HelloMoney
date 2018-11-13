package com.barclays.bmg.dao.service.impl;

import com.barclays.bmg.dao.ApplyTDDAO;
import com.barclays.bmg.service.ApplyTDService;
import com.barclays.bmg.service.request.ApplyTDServiceRequest;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;


public class ApplyTDServiceImpl implements ApplyTDService {
  private ApplyTDDAO applyTDDao;
	@Override
	public ApplyTDServiceResponse addProblem(ApplyTDServiceRequest request) {
		return  applyTDDao.addProblem(request);

	}
	public void setApplyTDDao(ApplyTDDAO applyTDDao) {
		this.applyTDDao = applyTDDao;
	}
	public ApplyTDDAO getApplyTDDao() {
		return applyTDDao;
	}}
