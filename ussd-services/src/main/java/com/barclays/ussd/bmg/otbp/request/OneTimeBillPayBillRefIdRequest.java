/**
 *
 */
package com.barclays.ussd.bmg.otbp.request;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI
 *
 */
public class OneTimeBillPayBillRefIdRequest implements BmgBaseRequestBuilder {

	private static final Logger LOGGER = Logger.getLogger(OneTimeBillPayBillRefIdRequest.class);

	@Autowired
    private SystemParameterService systemParameterService;

    @SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	    //masterpass QR
	    String business_id=ussdSessionMgmt.getBusinessId();
	    String tranid=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId();
	    LOGGER.debug("Check for MasterPass tranid: "+ tranid);
	    if((business_id!=null && "TZBRB".equals(business_id))  &&  (tranid!=null && "TZ_MASTERPASS_QR_BILLER".equals(tranid))){
	    	return new USSDBaseRequest();
	    }


		String userInput = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName());
		List<BillersListDO> blrsLst = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
			USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());

		//For GePG Billers
		BillersListDO billersListDO =  blrsLst.get(Integer.parseInt(userInput) - 1);
		if("GePG".equalsIgnoreCase(billersListDO.getBillAggregatorId())){
			SystemParameterServiceRequest sysrequest=new SystemParameterServiceRequest();
			SystemParameterDTO dto=new SystemParameterDTO();
			dto.setBusinessId(business_id);
			dto.setActivityId("COMMON");
			dto.setParameterId("gePGEnabled");
			dto.setSystemId("UB");
			sysrequest.setSystemParameterDTO(dto);
			SystemParameterServiceResponse response= systemParameterService.getSystemParameter(sysrequest);

			if (null == response || (null != response && null != response.getSystemParameterDTO()
							&& "N".equalsIgnoreCase(response.getSystemParameterDTO().getParameterValue()))) {
				LOGGER.debug("GePG Non Presentment Biller");
				throw new USSDNonBlockingException(USSDExceptions.BEM08863.getBmgCode());
			}
		}

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