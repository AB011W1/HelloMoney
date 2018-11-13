/**
 *
 */
package com.barclays.ussd.bmg.otbp.request;

import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI
 *
 */
public class OneTimeBillPayBillRefIdRequest implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	String userInput = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName());
	List<BillersListDO> blrsLst = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
	userInputMap.put(USSDConstants.SELECTED_BILLER_OTBP, blrsLst.get(Integer.parseInt(userInput) - 1).getBillerId());
	//CR-57
	if (userInputMap.get(USSDConstants.SELECTED_BILLER_OTBP).equalsIgnoreCase("DSTVZIM-2")
			&& ussdSessionMgmt.getBusinessId().equalsIgnoreCase("ZWBRB")) {
		userInput = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_DSTVTYPE.getParamName());
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		List<String> dstvLst = (List<String>) txSessions.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_DSTVTYPE.getTranId());
		String selectedFrmDstvtype = dstvLst.get(Integer.parseInt(userInput) - 1);
		userInputMap.put(USSDConstants.SELECTED_DSTV_BILLER_TYPE, selectedFrmDstvtype);

	}

	// WUC change for Botswana - 16/06/2017
	String billerCategoryID = blrsLst.get(Integer.parseInt(userInput) - 1).getBillerId();
	if(billerCategoryID !=null && billerCategoryID.equalsIgnoreCase("WUC-2")){
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		txSessions.put(USSDConstants.WUC_BILLER_CATEGORY, billerCategoryID);
		ussdSessionMgmt.setTxSessions(txSessions);
	}

	return new USSDBaseRequest();
    }
}
