package com.barclays.bmg.chequebook.operation;

import java.util.List;

import com.barclays.bmg.chequebook.operation.request.ChequeBookInitOperationRequest;
import com.barclays.bmg.chequebook.operation.response.ChequeBookInitOperationResponse;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.BMBCommonOperation;

public class ChequeBookRequestInitOperation extends BMBCommonOperation {

	private List<String> chequeBookTypeCountryList;

	/**
	 * Cheque book init operation
	 *
	 * @param request
	 * @return
	 */
	public ChequeBookInitOperationResponse chequeBookInit(
			ChequeBookInitOperationRequest request) {
		ChequeBookInitOperationResponse chequeBookInitOperationResponse = new ChequeBookInitOperationResponse();

		Context context = request.getContext();

		chequeBookInitOperationResponse.setContext(context);

		// Get list of cheque book type
		/*
		 * ListValueResServiceRequest listReq = new
		 * ListValueResServiceRequest();
		 * listReq.setContext(request.getContext());
		 * listReq.setGroup("ACCT_CASA_CHEQUEBOOK_TP");
		 * ListValueResByGroupServiceResponse listResp =
		 * listValueResService.getListValueByGroup(listReq);
		 */

		if (chequeBookTypeCountryList.contains(context.getBusinessId())) {
			/*
			 * Map<String, String> chqBkTypeLst = new HashMap<String, String>();
			 *
			 * for(ListValueCacheDTO listVal : getListValueByGroup(context,
			 * "ACCT_CASA_CHEQUEBOOK_TP")){
			 * chqBkTypeLst.put(listVal.getKey(),listVal.getLabel());
			 *
			 * }
			 */

			chequeBookInitOperationResponse.setChqBkTypLst(getListValueByGroup(
					context, "ACCT_CASA_CHEQUEBOOK_TP"));
		}

		// Get list of cheque book size
		/*
		 * listReq.setGroup("ACCT_CASA_CHEQUEBOOK_SIZE"); listResp =
		 * listValueResService.getListValueByGroup(listReq);
		 */

		/*
		 * Map<String, String> chqBkSizeLst =new HashMap<String, String>();
		 *
		 * for(ListValueCacheDTO listVal : getListValueByGroup(context,
		 * "ACCT_CASA_CHEQUEBOOK_SIZE")){
		 * chqBkSizeLst.put(listVal.getKey(),listVal.getLabel());
		 *
		 * }
		 */

		chequeBookInitOperationResponse.setChqBkSizeLst(getListValueByGroup(
				context, "ACCT_CASA_CHEQUEBOOK_SIZE"));

		return chequeBookInitOperationResponse;
	}

	public List<String> getChequeBookTypeCountryList() {
		return chequeBookTypeCountryList;
	}

	public void setChequeBookTypeCountryList(
			List<String> chequeBookTypeCountryList) {
		this.chequeBookTypeCountryList = chequeBookTypeCountryList;
	}

}
