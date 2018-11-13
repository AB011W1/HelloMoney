package com.barclays.bmg.mvc.controller.bankdraft;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BankDraftTransactionDTO;
import com.barclays.bmg.dto.ContactInfoDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.dto.PostalAddressDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.BankDraftPayInfoJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.mvc.operation.bankdraft.PurchaseBankDraftOperation;
import com.barclays.bmg.operation.request.bankdraft.PurchaseBankDraftOperationRequest;
import com.barclays.bmg.operation.response.bankdraft.PurchaseBankDraftOperationResponse;
import com.barclays.bmg.operation.OTPAuthenticationOperation;
import com.barclays.bmg.operation.SQAAuthenticationOperation;
import com.barclays.bmg.operation.request.OTPGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.request.SQAGenerateAuthenticationOperationRequest;
import com.barclays.bmg.operation.response.OTPGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.SQAGenerateAuthenticationOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthOTPOperationResponse;
import com.barclays.bmg.operation.response.secondauth.TxnSecondAuthSQAOperationResponse;
import com.barclays.bmg.service.product.impl.ListValueResServiceImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class BankDraftExecuteController extends BMBAbstractController {

	private ListValueResServiceImpl listValueResServiceImpl;

	private PurchaseBankDraftOperation purchaseBankDraftOperation;
	private OTPAuthenticationOperation otpAuthenticationOperation;
	private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
	private SQAAuthenticationOperation sqaAuthenticationOperation;
	private BMBJSONBuilder txnSQASecondAuthJSONBldr;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Override
	protected String getActivityId() {
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setLastStep(request);
		BMBPayload responseModel = null;

		// Get the TransactionDTO
		BankDraftTransactionDTO transactionDTO = (BankDraftTransactionDTO) getFromProcessMap(
				request, BMGProcessConstants.BANK_DRAFT_PROCESS_KEY,
				FundTransferConstants.BANK_DRAFT_DTO);

		if (transactionDTO != null) {

			// Check if second level auth is needed.
			PurchaseBankDraftOperationRequest purchaseBankDraftOperationRequest = createPurchaseBankDraftOperationRequest(
					request, transactionDTO);
			if (!transactionDTO.isScndLvlauthReq()
					|| "NON".equalsIgnoreCase(transactionDTO
							.getScndLvlAuthTyp())) {

				PurchaseBankDraftOperationResponse purchaseBankDraftOperationResponse = purchaseBankDraftOperation
						.purchaseBankDraft(purchaseBankDraftOperationRequest);

				if (purchaseBankDraftOperationResponse.isSuccess()) {
					// Remove the process data
					cleanProcess(request,
							BMGProcessConstants.BANK_DRAFT_PROCESS_KEY);
					responseModel = getJSONModel(
							purchaseBankDraftOperationRequest.getContext(),
							transactionDTO);
					BankDraftPayInfoJSONResponseModel bankDraftPayInfoJSONResponseModel = (BankDraftPayInfoJSONResponseModel) responseModel
							.getPayData();

					bankDraftPayInfoJSONResponseModel
							.setTxnRefNo(purchaseBankDraftOperationResponse
									.getTransactionRefNo());
					bankDraftPayInfoJSONResponseModel
							.setTxnDtTm(BMGFormatUtility
									.getLongDate(purchaseBankDraftOperationResponse
											.getTransactionDate()));

					bankDraftPayInfoJSONResponseModel
							.setTxnMsg(purchaseBankDraftOperationResponse
									.getTxnMsg());
				}
			} else {
				// Set the transactiondto into the process map
				setIntoProcessMap(request,
						BMGProcessConstants.BANK_DRAFT_PROCESS_KEY,
						FundTransferConstants.BANK_DRAFT_DTO, transactionDTO);
				// Check the type of auth that is needed and return the JSON

				String authType = transactionDTO.getScndLvlAuthTyp();

				if (BillPaymentConstants.AUTH_TYPE_OTP.equals(authType)) {

					return generateOTP(purchaseBankDraftOperationRequest,
							request);
				} else if (BillPaymentConstants.AUTH_TYPE_SQA.equals(authType)) {
					return generateSQA(purchaseBankDraftOperationRequest,
							request);
				}

			}

		}

		return responseModel;
	}

	private BMBPayload getJSONModel(Context context,
			BankDraftTransactionDTO transactionDTO) {

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = new GetSelectedAccountOperationResponse();
		getSelectedAccountOperationResponse.setContext(context);
		getSelectedAccountOperationResponse.setSelectedAcct(transactionDTO
				.getSourceAcct());

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = new RetrievePayeeInfoOperationResponse();
		retrievePayeeInfoOperationResponse.setContext(context);
		retrievePayeeInfoOperationResponse.setBeneficiaryDTO(transactionDTO
				.getBeneficiaryDTO());

		BMBPayload bmbPayload = (BMBPayload) bmbJSONBuilder
				.createMultipleJSONResponse(
						getSelectedAccountOperationResponse,
						retrievePayeeInfoOperationResponse);

		BankDraftPayInfoJSONResponseModel bankDraftPayInfoJSONResponseModel = (BankDraftPayInfoJSONResponseModel) bmbPayload
				.getPayData();

		AmountJSONModel txnJSONAmt = new AmountJSONModel();
		txnJSONAmt.setAmt(BMGFormatUtility.getFormattedAmount(transactionDTO
				.getTxnAmt().getAmount()));
		txnJSONAmt.setCurr(transactionDTO.getTxnAmt().getCurrency());
		bankDraftPayInfoJSONResponseModel.setAmount(txnJSONAmt);
		bankDraftPayInfoJSONResponseModel.setDelTypSel(transactionDTO
				.getDeliveryType());
		bankDraftPayInfoJSONResponseModel.setDrfTypSel(transactionDTO
				.getDraftType());

		bankDraftPayInfoJSONResponseModel.setCntrLst(null);
		bankDraftPayInfoJSONResponseModel.setCurrLst(null);

		return bmbPayload;
	}

	private PurchaseBankDraftOperationRequest createPurchaseBankDraftOperationRequest(
			HttpServletRequest request,
			BankDraftTransactionDTO bankDraftTransactionDTO) {
		Context requestCtx = createContext(request);
		requestCtx.setActivityId(getActivityId());
		PurchaseBankDraftOperationRequest bankDraftOperationRequest = new PurchaseBankDraftOperationRequest();
		bankDraftOperationRequest.setContext(requestCtx);
		bankDraftOperationRequest
				.setBankDraftTransactionDTO(bankDraftTransactionDTO);
		// Get CustomerDTO
		Map<String, Object> userMap = getUserMapFromSession(request);
		CustomerDTO customerDTO = (CustomerDTO) userMap
				.get(SessionConstant.SESSION_CUSTOMER_DTO);

		ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
		contactInfoDTO.setFullName(customerDTO.getFullName());
		contactInfoDTO.setContactNo(customerDTO.getMobilePhone());
		List<PostalAddressDTO> postalAddressDTOList = customerDTO
				.getPostalAddresses();
		String addr1 = "";
		String addr2 = "";
		String addr3 = "";
		if (postalAddressDTOList != null && postalAddressDTOList.size() > 0
				&& postalAddressDTOList.get(0) != null) {
			addr1 = postalAddressDTOList.get(0).getAddressLine1();
			addr2 = postalAddressDTOList.get(0).getAddressLine2();
			addr3 = postalAddressDTOList.get(0).getAddressLine3();
		}
		contactInfoDTO.setAddressLineOne(addr1);
		contactInfoDTO.setAddressLineTwo(addr2);
		contactInfoDTO.setAddressLineThree(addr3);
		bankDraftTransactionDTO.setRemitterContactInfoDTO(contactInfoDTO);
		bankDraftTransactionDTO.setPayableAtCode(getLabel(requestCtx,
				requestCtx.getCountryCode(), "CMN_COUNTRY_SHORTNAME"));

		return bankDraftOperationRequest;
	}

	private String getLabel(Context requestCtx, String key, String group) {

		String label = "";
		ListValueResServiceRequest listReq = new ListValueResServiceRequest();
		listReq.setContext(requestCtx);
		listReq.setGroup(group);
		listReq.setListValueKey(key);
		ListValueResServiceResponse listResp = listValueResServiceImpl
				.getListValueRes(listReq);
		if (listResp != null) {
			label = listResp.getListValueLabel();
		}
		return label;
	}

	/**
	 * @param request
	 * @param httprequest
	 * @param txnRefNo
	 * @return It will generate OTP and return the response model.
	 */
	private BMBPayload generateOTP(RequestContext request,
			HttpServletRequest httprequest) {
		OTPGenerateAuthenticationOperationRequest otpAuthenticationOperationRequest = new OTPGenerateAuthenticationOperationRequest();
		Context context = request.getContext();
		otpAuthenticationOperationRequest.setContext(context);
		otpAuthenticationOperationRequest
				.setCustomerId(context.getCustomerId());
		otpAuthenticationOperationRequest.setMobilePhone(context
				.getMobilePhone());
		String[] smsParams = (String[]) getSessionAttribute(httprequest,
				SessionConstant.SESSION_SMS_PARAMS);
		otpAuthenticationOperationRequest.setSmsParams(smsParams);
		OTPGenerateAuthenticationOperationResponse otpAuthenticationOperationResponse = otpAuthenticationOperation
				.generate(otpAuthenticationOperationRequest);
		setProcessMapIntoSession(httprequest,
				SessionConstant.SESSION_CHALLENGE_ID,
				otpAuthenticationOperationResponse.getChallengeId());
		TxnSecondAuthOTPOperationResponse response = new TxnSecondAuthOTPOperationResponse();
		response.setOtpResponse(otpAuthenticationOperationResponse);
		return (BMBPayload) txnOTPSecondAuthJSONBldr
				.createJSONResponse(response);
	}

	/**
	 * @param request
	 * @param httprequest
	 * @param txnRefNo
	 * @return It will generate SQA and return the response model
	 */
	private BMBPayload generateSQA(RequestContext request,
			HttpServletRequest httprequest) {
		SQAGenerateAuthenticationOperationRequest sqaAuthenticationOperationRequest = new SQAGenerateAuthenticationOperationRequest();
		sqaAuthenticationOperationRequest.setContext(request.getContext());
		SQAGenerateAuthenticationOperationResponse sqaAuthenticationOperationResponse = sqaAuthenticationOperation
				.generate(sqaAuthenticationOperationRequest);

		setProcessMapIntoSession(httprequest,
				SessionConstant.SESSION_QUESTION_ID,
				sqaAuthenticationOperationResponse.getQuestionId());
		TxnSecondAuthSQAOperationResponse response = new TxnSecondAuthSQAOperationResponse();
		response.setSqaResponse(sqaAuthenticationOperationResponse);
		return (BMBPayload) txnSQASecondAuthJSONBldr
				.createJSONResponse(response);
	}

	public ListValueResServiceImpl getListValueResServiceImpl() {
		return listValueResServiceImpl;
	}

	public void setListValueResServiceImpl(
			ListValueResServiceImpl listValueResServiceImpl) {
		this.listValueResServiceImpl = listValueResServiceImpl;
	}

	public PurchaseBankDraftOperation getPurchaseBankDraftOperation() {
		return purchaseBankDraftOperation;
	}

	public void setPurchaseBankDraftOperation(
			PurchaseBankDraftOperation purchaseBankDraftOperation) {
		this.purchaseBankDraftOperation = purchaseBankDraftOperation;
	}

	public OTPAuthenticationOperation getOtpAuthenticationOperation() {
		return otpAuthenticationOperation;
	}

	public void setOtpAuthenticationOperation(
			OTPAuthenticationOperation otpAuthenticationOperation) {
		this.otpAuthenticationOperation = otpAuthenticationOperation;
	}

	public BMBJSONBuilder getTxnOTPSecondAuthJSONBldr() {
		return txnOTPSecondAuthJSONBldr;
	}

	public void setTxnOTPSecondAuthJSONBldr(
			BMBJSONBuilder txnOTPSecondAuthJSONBldr) {
		this.txnOTPSecondAuthJSONBldr = txnOTPSecondAuthJSONBldr;
	}

	public SQAAuthenticationOperation getSqaAuthenticationOperation() {
		return sqaAuthenticationOperation;
	}

	public void setSqaAuthenticationOperation(
			SQAAuthenticationOperation sqaAuthenticationOperation) {
		this.sqaAuthenticationOperation = sqaAuthenticationOperation;
	}

	public BMBJSONBuilder getTxnSQASecondAuthJSONBldr() {
		return txnSQASecondAuthJSONBldr;
	}

	public void setTxnSQASecondAuthJSONBldr(
			BMBJSONBuilder txnSQASecondAuthJSONBldr) {
		this.txnSQASecondAuthJSONBldr = txnSQASecondAuthJSONBldr;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

}
