package com.barclays.bmg.json.model.builder.pesalink;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.pesalink.BankOpRes;
import com.barclays.bmg.operation.response.pesalink.SearchIndividualCustforDeDupCheckOperationResponse;
import java.util.ArrayList;
import java.util.List;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.BankModel;
import com.barclays.bmg.json.model.SearchIndividualCustforDeDupCheckJsonModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;

/**
 * @author BTCI JSON builder class for View Txn History Details Response
 */
public class SearchIndividualCustforDeDupCheckJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder{


	    @Override
	    public Object createJSONResponse(ResponseContext responseContext) {

	    	if (responseContext instanceof SearchIndividualCustforDeDupCheckOperationResponse) {
	    		SearchIndividualCustforDeDupCheckOperationResponse response = (SearchIndividualCustforDeDupCheckOperationResponse) responseContext;

	    	    BMBPayload bmbPayload = new BMBPayload(createHeader(response));

	    	   if((response.getContext().getActivityId().equals("KITS_DEREGISTRATION_LOOKUP")
	    			   || response.getContext().getActivityId().equals("KITS_SENDTOPHONE_LOOKUP"))&& response.isSuccess())
	    	   {
	    	   populatePayLoad(response, bmbPayload);
	    	   }

	    	    return bmbPayload;
	    	} else {
	    	    throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
	    	}

	    }
	    /**
	     * This method createHeader has the purpose to create header for JSON response.
	     *
	     * @param CreditCardLinkConfirmOperationResponse
	     * @return BMBPayloadHeader
	     */
	    protected BMBPayloadHeader createHeader(SearchIndividualCustforDeDupCheckOperationResponse response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
		    header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		    header.setResMsg("Succes KITS Lookup");

		} else if (response != null && !response.isSuccess()) {
		    header.setResCde(response.getResCde());
		    header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);

		return header;
	    }

	    /**
	     * This method populatePayLoad has the purpose to create data for JSON response.
	     *
	     * @param CreditCardLinkConfirmOperationResponse
	     * @param BMBPayload
	     * @return void
	     */
	  protected void populatePayLoad(SearchIndividualCustforDeDupCheckOperationResponse response, BMBPayload bmbPayload) {
	    	SearchIndividualCustforDeDupCheckJsonModel responseModel = null;
	    	if (response != null && response.isSuccess()) {
	    		responseModel = new SearchIndividualCustforDeDupCheckJsonModel();
	    		if(response.getContext().getActivityId().equals("KITS_DEREGISTRATION_LOOKUP"))
	    		{
	    		if(response.getIndividualCustAdditionalInfoOpRes()!=null){

	    			responseModel.setCustomerAccountNumber(response.getIndividualCustAdditionalInfoOpRes().getCustomerAccountNumber());
	    			responseModel.setPrimaryFlag(response.getIndividualCustAdditionalInfoOpRes().getPrimaryFlag());

	    		}
	    		}else if(response.getContext().getActivityId().equals("KITS_SENDTOPHONE_LOOKUP"))
	    		{
	    			List<BankOpRes> list = response.getIndividualCustomerBasicOpResList().get(0).getBankOpResList();
	    			responseModel = new SearchIndividualCustforDeDupCheckJsonModel();
	    			// Added by g01022861 on 30/09/2016
	    			responseModel.setIndividualName(response.getIndividualCustomerBasicOpResList().get(0).getIndividualName());
	    			//Ended
	    			if (list != null && list.size() > 0) {
	    				List<BankModel> bankModelList = new ArrayList<BankModel>();
	    				for (BankOpRes bankDTO : list) {
	    					BankModel bankModel = new BankModel();
	    					bankModel.setBankCode(bankDTO.getBankCode());
	    					bankModel.setBankName(bankDTO.getBankName());

	    					bankModelList.add(bankModel);
	    				}
	    				responseModel.setBankResList(bankModelList);
	    			}

	    		}

	    	}
	    	bmbPayload.setPayData(responseModel);
	    }


	}



