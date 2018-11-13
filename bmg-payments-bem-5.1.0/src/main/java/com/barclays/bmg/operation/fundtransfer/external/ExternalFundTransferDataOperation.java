package com.barclays.bmg.operation.fundtransfer.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.ExternalFundTransferDataOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFundTransferDataOperationResponse;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;

public class ExternalFundTransferDataOperation extends BMBCommonOperation {

	//private ListValueResService listValueResService;
	private String currLstActId;
	private String currLstKey;

	public ExternalFundTransferDataOperationResponse getExternalFundTransferData(ExternalFundTransferDataOperationRequest request){
		ExternalFundTransferDataOperationResponse response = new ExternalFundTransferDataOperationResponse();
		response.setContext(request.getContext());
		response.setCurrLst(retrieveCurrencyList(request));
		response.setChargeDesc(getChargesDesc(request));

		return response;
	}

	public List<String> retrieveCurrencyList(ExternalFundTransferDataOperationRequest request){

		/*Context context = request.getContext();
		List<String> currLst = getSysParamListById(context,currLstKey,
				currLstActId);*/

		List<String> currLst =	getFilteredCurrList(request,currLstKey,
				currLstActId);

		return currLst;
	}

	private Map<String, String > getChargesDesc(RequestContext request){

		Map<String,String> chargeDescMap = null;
		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setGroup("FT_FEE_TYPE");
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = getListValueResService().getListValueByGroup(listValueResServiceRequest);

		if(listValueResByGroupServiceResponse!=null && listValueResByGroupServiceResponse.getListValueCahceDTO() !=null){
			chargeDescMap = new HashMap<String, String>();
			List<ListValueCacheDTO> chargeDescLst = listValueResByGroupServiceResponse.getListValueCahceDTO();
			for(ListValueCacheDTO chargeDesc :chargeDescLst ){
				chargeDescMap.put(chargeDesc.getKey(), chargeDesc.getLabel());
			}
		}

		return chargeDescMap;
	}

	/*public ListValueResService getListValueResService() {
		return listValueResService;
	}

	public void setListValueResService(ListValueResService listValueResService) {
		this.listValueResService = listValueResService;
	}*/

	public String getCurrLstActId() {
		return currLstActId;
	}

	public void setCurrLstActId(String currLstActId) {
		this.currLstActId = currLstActId;
	}

	public String getCurrLstKey() {
		return currLstKey;
	}

	public void setCurrLstKey(String currLstKey) {
		this.currLstKey = currLstKey;
	}

}
