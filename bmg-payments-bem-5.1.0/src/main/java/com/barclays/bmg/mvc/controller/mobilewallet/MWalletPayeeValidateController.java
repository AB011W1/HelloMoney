package com.barclays.bmg.mvc.controller.mobilewallet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.mobilewallet.MWalletValidateCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.MWalletPayeeValidateOperation;
import com.barclays.bmg.operation.request.MWalletValidateRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.service.request.MWalletValidateServiceRequest;
import com.barclays.bmg.service.response.MWalletValidateResponse;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;
import com.barclays.ussd.utils.USSDConstants;


public class MWalletPayeeValidateController extends BMBAbstractCommandController{

	
	private MWalletPayeeValidateOperation mWalletValidateOperation;
	
	
	public MWalletPayeeValidateOperation getmWalletValidateOperation() {
		return mWalletValidateOperation;
	}

	public void setmWalletValidateOperation(MWalletPayeeValidateOperation mWalletValidateOperation) {
		this.mWalletValidateOperation = mWalletValidateOperation;
	}

	/**
     * The instance variable named "MOBILE_WALLET_VALIDATE_RESP_CODE_ERROR" is created.
     */
    private String MOBILE_WALLET_VALIDATE_RESP_CODE_ERROR = "RES0651";

    /**
     * The instance variable named "activityId" is created.
     */
    private String activityId;

    /**
     * The instance variable named "bmbJSONBuilder" is created.
     */
    private BMBJSONBuilder bmbJSONBuilder;

 
    /**
     * The instance variable named "billerCatId" is created.
     */
    private String billerCatId;
   
    /**
     * Gets the activity id.
     *
     * @return the ActivityId
     */
    public String getActivityId() {
	return activityId;
    }

    /**
     * Sets values for ActivityId.
     *
     * @param activityId
     *            the activity id
     */
    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

   
    /**
     * Gets the bmb json builder.
     *
     * @return the BmbJSONBuilder
     */
    public BMBJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    /**
     * Sets values for BmbJSONBuilder.
     *
     * @param bmbJSONBuilder
     *            the bmb json builder
     */
    public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    /**
     * Gets the biller cat id.
     *
     * @return the BillerCatId
     */
    public String getBillerCatId() {
	return billerCatId;
    }

    /**
     * Sets values for BillerCatId.
     *
     * @param billerCatId
     *            the biller cat id
     */
    public void setBillerCatId(String billerCatId) {
	this.billerCatId = billerCatId;
    }

    

	

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		// TODO Auto-generated method stub
		
		Context context = buildContext(request);
		MWalletValidateServiceRequest  serviceRequest= new MWalletValidateServiceRequest();
		MWalletValidateCommand mWalletValidateCommand = (MWalletValidateCommand) command;
		MWalletValidateServiceResopnse serviceResponse = new MWalletValidateServiceResopnse();	
		serviceRequest.setPayeeNumber(request.getParameter(USSDConstants.MW_MBL_NO));
		BillerDTO biller = new BillerDTO();
		biller.setBillerId(mWalletValidateCommand.getBillerId());
		biller.setBillerCategoryId(getBillerCatId());		
		CustomerAccountDTO customerDTO = new CustomerAccountDTO();
		customerDTO.setAccountNumber(mWalletValidateCommand.getActNo());		
		serviceRequest.setBillerDTO(biller);
		serviceRequest.setCreditCard(customerDTO);
		serviceRequest.setContext(context);		
		serviceResponse = mWalletValidateOperation.validateMobileNumber(serviceRequest);
		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder).createMultipleJSONResponse(serviceResponse);
	}
	
	
	
	 /**
     * The method is written for Builds the context.
     *
     * @param httpRequest
     *            the http request
     * @return the Context's object
     */
    @SuppressWarnings("unchecked")
    protected Context buildContext(HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);

	Map<String, String> ppMap = (Map<String, String>) getSessionAttribute(httpRequest, SessionConstant.SESSION_PP_MAP);

	context.setPpMap(ppMap);

	context.setActivityId(getActivityId());

	return context;

    }

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return activityId;
	}
	

}
