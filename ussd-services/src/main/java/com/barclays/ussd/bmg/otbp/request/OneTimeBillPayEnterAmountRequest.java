/**
 *
 */
package com.barclays.ussd.bmg.otbp.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillerArea;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.common.services.IBillersLstService;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI
 *
 */
public class OneTimeBillPayEnterAmountRequest implements BmgBaseRequestBuilder {

    private static final String BILLER_TYPE = "billerType";

    @Autowired
	private IBillersLstService billersLstService;

	@Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

    	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
    	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

    	// set the biller id in the request for bmg call.
    	List<BillersListDO> billersList = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
    		USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
    	String enteredBillerIdx = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName());

    	//MasterPassQR Biller
    	BillersListDO billerDO = new BillersListDO();
    	String business_id=ussdSessionMgmt.getBusinessId();
		    String tranid=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId();
		    if((business_id!=null && "TZBRB".equals(business_id))  &&  (tranid!=null && "TZ_MASTERPASS_QR_BILLER".equals(tranid)))
		    {
		    	billerDO = this.billersLstService.getBillerInfo(ussdSessionMgmt
						.getCountryCode(), "MPQR-3", ussdSessionMgmt
						.getMsisdnNumber(), ussdSessionMgmt.getBusinessId());
			}
		    else{
		    	billerDO = billersList.get(Integer.parseInt(enteredBillerIdx) - 1);
		    }
    	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
    	Map<String, String> requestParamMap = new HashMap<String, String>();
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
    	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName(), billerDO.getBillerId());

    	List<BillerArea> billerArea = (List<BillerArea>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
    		    .get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId());
    	if(billerArea != null){
    	    int selectedAreaInput = Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName()));

    	    String selectArea = billerArea.get(selectedAreaInput - 1).getAreaName();
    	    requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName(), selectArea);
    	}
    	ussdBaseRequest.setRequestParamMap(requestParamMap);
    	return ussdBaseRequest;
    }




}