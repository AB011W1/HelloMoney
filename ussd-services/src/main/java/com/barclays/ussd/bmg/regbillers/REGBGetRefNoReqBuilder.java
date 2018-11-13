package com.barclays.ussd.bmg.regbillers;

import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.SrcAccount;

public class REGBGetRefNoReqBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	List<BillersListDO> blrslist = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getTranId());
	BillersListDO billersListDO = blrslist.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getParamName())) - 1);
	ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().put(USSDConstants.SELECTED_BILLER_REGB, billersListDO.getBillerId());

	//CR-57
	if (ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDConstants.SELECTED_BILLER_REGB).equalsIgnoreCase("DSTVZIM-2")
			&& ussdSessionMgmt.getBusinessId().equalsIgnoreCase("ZWBRB")) {
		String userInput = userInputMap.get(USSDInputParamsEnum.REG_BILLER_DSTV_TYPE.getParamName());
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		List<String> dstvLst = (List<String>) txSessions.get(USSDInputParamsEnum.REG_BILLER_DSTV_TYPE.getTranId());
		String selectedFrmDstvtype = dstvLst.get(Integer.parseInt(userInput) - 1);
		userInputMap.put(USSDConstants.SELECTED_DSTV_BILLER_TYPE, selectedFrmDstvtype);

	}

	// WUC change for Botswana - 20/06/2017
	String userInput = userInputMap.get(USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getParamName());
	String billerCategoryID = blrslist.get(Integer.parseInt(userInput) - 1).getBillerId();
	if(billerCategoryID !=null && billerCategoryID.equalsIgnoreCase("WUC-2")){
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		txSessions.put(USSDConstants.WUC_BILLER_CATEGORY, billerCategoryID);
		ussdSessionMgmt.setTxSessions(txSessions);
	}

	return request;
    }
}
