package com.barclays.bmg.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.SqlMapSystemConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.TermsOfUseDAO;
import com.barclays.bmg.dto.TermsOfUseDTO;
import com.barclays.bmg.service.request.TermsOfUseAcceptServiceRequest;
import com.barclays.bmg.service.request.TermsOfUseDetailsServiceRequest;
import com.barclays.bmg.service.response.TermsOfUseAcceptServiceResponse;
import com.barclays.bmg.service.response.TermsOfUseDetailsServiceResponse;

public class TermsOfUseDAOImpl extends BaseDAOImpl implements TermsOfUseDAO {


	/*public TermsOfUseDetailsServiceResponse retrieveTermsOfUseDetails(
			TermsOfUseDetailsServiceRequest request) {
		//TODO now reads from text
		TermsOfUseDetailsServiceResponse returnTermsOfUseServiceResp = new TermsOfUseDetailsServiceResponse();

//		String fileName = "termsofuse/TermsOfUse_"+request.getTermsOfUseVersionNo()+".txt";
		String fileName = "termsofuse/TermsOfUse_1.0.txt";
		if(request.getContext().getBusinessId()!=null){
		fileName = "termsofuse/"+request.getContext().getBusinessId()+"/TermsOfUse_1.0.txt";
		}
		StringBuffer termsDetails = new StringBuffer();
		try{

			//reads file from class path
			InputStreamReader isr = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName));
			BufferedReader frombr = new BufferedReader(isr);

//			FileReader fileReader = new FileReader(fileName);
//			BufferedReader frombr = new BufferedReader(fileReader);
			String line = "";

			while ((line = frombr.readLine()) != null) {
				termsDetails.append(line);
			}

			returnTermsOfUseServiceResp.setSuccess(true);
		}
		catch(Exception e){
			
			returnTermsOfUseServiceResp.setSuccess(false);
		}

		returnTermsOfUseServiceResp.setTermsAndCondition(termsDetails.toString());

		return returnTermsOfUseServiceResp;
	}*/

	@Override
	public TermsOfUseAcceptServiceResponse acceptTermsOfUse(
			TermsOfUseAcceptServiceRequest request) {

		TermsOfUseAcceptServiceResponse returnTermsOfUseAcceptServiceResp = new TermsOfUseAcceptServiceResponse();

		Context context = request.getContext();
		request.getTermsOfUseVersionNo();

		TermsOfUseDTO termsOfUse = new TermsOfUseDTO();
		termsOfUse.setCustomerId(context.getCustomerId());
		termsOfUse.setSystemId(context.getSystemId());
		termsOfUse.setBusinessId(context.getBusinessId());
		termsOfUse.setAcceptFlag(request.getAcceptFlag());
		termsOfUse.setTermsOfUseVersion(request.getTermsOfUseVersionNo());


		this.insert(SqlMapSystemConstants.TERMS_OF_USE_INSERT,termsOfUse);

		returnTermsOfUseAcceptServiceResp.setSuccess(true);
		return returnTermsOfUseAcceptServiceResp;
	}

	@Override
	public TermsOfUseAcceptServiceResponse checkTermsOfUseAccept(TermsOfUseAcceptServiceRequest request) {

		TermsOfUseAcceptServiceResponse returnTermsOfUseAcceptServiceResp = new TermsOfUseAcceptServiceResponse();

		Map<String, String> params = new HashMap<String, String>();

		Context context = request.getContext();
		params.put("customerId", context.getCustomerId());
		params.put("systemId", context.getSystemId());
		params.put("businessId", context.getBusinessId());
		params.put("acceptFlag", request.getAcceptFlag());
		params.put("termsOfUseVersion", request.getTermsOfUseVersionNo());

		List<TermsOfUseDTO>  termsOfUseList = this.queryForList(
				SqlMapSystemConstants.TERMS_OF_USE_BY_VERSION, params);

		if(termsOfUseList!=null && termsOfUseList.size() > 0){

				returnTermsOfUseAcceptServiceResp.setSuccess(true);
		}
		else{
			returnTermsOfUseAcceptServiceResp.setSuccess(false);
		}

		return returnTermsOfUseAcceptServiceResp;
	}



}
