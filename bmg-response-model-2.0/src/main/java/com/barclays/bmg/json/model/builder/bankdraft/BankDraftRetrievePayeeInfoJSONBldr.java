package com.barclays.bmg.json.model.builder.bankdraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.json.model.AmountJSONModel;
import com.barclays.bmg.json.model.CasaAccountJSONModel;
import com.barclays.bmg.json.model.billpayment.BankDraftBeneficiaryJSONModel;
import com.barclays.bmg.json.model.billpayment.CountryCodeJSONModel;
import com.barclays.bmg.json.model.billpayment.DeliveryTypeJSONModel;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.model.fundtransfer.BankDraftPayInfoJSONResponseModel;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.service.product.impl.ListValueResServiceImpl;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.bmg.utils.BMGFormatUtility;

public class BankDraftRetrievePayeeInfoJSONBldr extends
		BMBMultipleResponseJSONBuilder implements BMBJSONBuilder {

	private ListValueResServiceImpl listValueResServiceImpl;
	private String responseId;
	private Map<String, Boolean> businessIDMap;

	public Map<String, Boolean> getBusinessIDMap() {
		return businessIDMap;
	}

	public void setBusinessIDMap(Map<String, Boolean> businessIDMap) {
		this.businessIDMap = businessIDMap;
	}

	@Override
	public Object createMultipleJSONResponse(
			ResponseContext... responseContexts) {
		BMBPayloadHeader bmbPayloadHeader = null;
		BMBPayload bmbPayload = null;
		for (ResponseContext response : responseContexts) {
			if (response != null && !response.isSuccess()) {
				bmbPayloadHeader = createHeader(response);
				break;
			}
		}

		if (bmbPayloadHeader != null) {
			bmbPayload = new BMBPayload(bmbPayloadHeader);
		} else {
			bmbPayload = new BMBPayload(createHeader(responseContexts[0]));
			populatePayLoad(bmbPayload, responseContexts);
		}

		return bmbPayload;
	}

	protected BMBPayloadHeader createHeader(ResponseContext response) {

		BMBPayloadHeader header = new BMBPayloadHeader();
		if (response != null && response.isSuccess()) {
			header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
			header.setResMsg("");
		} else  if(response != null){
			header.setResCde(response.getResCde());
			header.setResMsg(response.getResMsg());
		}
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(getResponseId());

		return header;
	}

	protected void populatePayLoad(BMBPayload bmbPayload,
			ResponseContext... responses) {

		BankDraftPayInfoJSONResponseModel responseModel = new BankDraftPayInfoJSONResponseModel();

		GetSelectedAccountOperationResponse getSelectedAccountOperationResponse = (GetSelectedAccountOperationResponse) responses[0];

		Context responseCtx = null;
		if (getSelectedAccountOperationResponse != null) {
			CustomerAccountDTO srcAcct = getSelectedAccountOperationResponse
					.getSelectedAcct();
			responseModel.setFrmAct(getCASAAccount(srcAcct,
					getSelectedAccountOperationResponse));
			responseCtx = getSelectedAccountOperationResponse.getContext();

			List<String> deliveryTypeList = getSysParamListById(responseCtx,
					"FT_DELIVERY_TYPE_LIST",
					getSelectedAccountOperationResponse.getContext()
							.getActivityId());

			if (deliveryTypeList != null && deliveryTypeList.size() > 0) {

				for (String deliveryType : deliveryTypeList) {

					DeliveryTypeJSONModel deliveryTypeJSONModel = new DeliveryTypeJSONModel();

					String delTypLbl = "";

					if (!StringUtils.isEmpty(deliveryType)) {
						delTypLbl = getLabel(responseCtx, deliveryType,
								"FT_DELIVERY_TYPE");
						deliveryTypeJSONModel.setDelTypCde(deliveryType);
						deliveryTypeJSONModel.setDelTypLbl(delTypLbl);
					} else {
						deliveryTypeJSONModel.setDelTypCde("");
						deliveryTypeJSONModel.setDelTypLbl("");
					}

					responseModel.addDeliveryType(deliveryTypeJSONModel);
				}
			}
		}

		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = (RetrievePayeeInfoOperationResponse) responses[1];

		String payeeTypeCode = null;

		if (retrievePayeeInfoOperationResponse != null) {
			payeeTypeCode = retrievePayeeInfoOperationResponse
					.getBeneficiaryDTO().getPayeeTypeCode();
		}

		String drfTyp = "";

		if (payeeTypeCode != null && payeeTypeCode.equals("MC")) {
			drfTyp = getLabel(responseCtx, "MC", "FT_DRAFT_TYPE");
		} else if (payeeTypeCode != null && payeeTypeCode.equals("BKD")) {
			drfTyp = getLabel(responseCtx, "BD", "FT_DRAFT_TYPE");
		}

		if (!StringUtils.isEmpty(drfTyp)) {
			responseModel.setDrfTyp(drfTyp);
		}

		if (retrievePayeeInfoOperationResponse != null) {

			BeneficiaryDTO beneficiaryDTO = retrievePayeeInfoOperationResponse
					.getBeneficiaryDTO();

			responseCtx = retrievePayeeInfoOperationResponse.getContext();

			BankDraftBeneficiaryJSONModel beneficiaryJSONModel = new BankDraftBeneficiaryJSONModel(
					beneficiaryDTO);
			responseModel.setPayInfo(beneficiaryJSONModel);
			beneficiaryJSONModel.setCntry(getLabel(responseCtx, responseCtx
					.getCountryCode(), "CMN_COUNTRY"));

		}

		TransactionLimitOperationResponse transactionLimitOperationResponse = getTransactionLimitOperationResponse(responses);

		if (transactionLimitOperationResponse != null) {
			AmountJSONModel txnLmt = new AmountJSONModel();
			txnLmt.setAmt(BMGFormatUtility
					.getFormattedAmount(transactionLimitOperationResponse
							.getAValidDailyLimit()));
			txnLmt.setCurr(transactionLimitOperationResponse.getContext()
					.getLocalCurrency());
			responseModel.setTxnLmt(txnLmt);
		}

		if (responseCtx != null) {

			if (payeeTypeCode != null && payeeTypeCode.equals("MC")) {
				responseModel.setCurrLst(getSysParamListById(responseCtx,
						"PMT_FT_BKD_SUPPORT_CURRENCY", "PMT_FT_MRC_ONETIME"));
				List<CountryCodeJSONModel> cntrLst = new ArrayList<CountryCodeJSONModel>();
				CountryCodeJSONModel cnCodeJSONModel = new CountryCodeJSONModel();
				cnCodeJSONModel.setCntrCde(responseCtx.getCountryCode());
				cnCodeJSONModel.setCntrNam(getLabel(responseCtx, responseCtx
						.getCountryCode(), "CMN_COUNTRY"));
				cntrLst.add(cnCodeJSONModel);
				responseModel.setCntrLst(cntrLst);
			} else if (payeeTypeCode != null && payeeTypeCode.equals("BKD")) {

				responseModel.setCurrLst(getSysParamListById(responseCtx,
						"PMT_FT_BKD_SUPPORT_CURRENCY", "PMT_FT_BKD_ONETIME"));
				String businessId = responseCtx.getBusinessId();

				if (businessIDMap.containsKey(businessId)
						&& businessIDMap.get(businessId)) {

					List<CountryCodeJSONModel> cntrLst = new ArrayList<CountryCodeJSONModel>();
					CountryCodeJSONModel cnCodeJSONModel = new CountryCodeJSONModel();
					cnCodeJSONModel.setCntrCde(responseCtx.getCountryCode());
					cnCodeJSONModel.setCntrNam(getLabel(responseCtx,
							responseCtx.getCountryCode(), "CMN_COUNTRY"));
					cntrLst.add(cnCodeJSONModel);
					responseModel.setCntrLst(cntrLst);

				} else {
					responseModel.setCntrLst(getCountryList(responseCtx));
				}
			}

		}

		postProcess(responseModel, responses);
		bmbPayload.setPayData(responseModel);
	}

	protected void postProcess(BankDraftPayInfoJSONResponseModel responseModel,
			ResponseContext... responses) {

	}

	private List<CountryCodeJSONModel> getCountryList(Context context) {

		List<CountryCodeJSONModel> cntryJSONLst = null;
		ListValueResServiceRequest listReq = new ListValueResServiceRequest();
		listReq.setContext(context);
		listReq.setGroup("CMN_COUNTRY");

		ListValueResByGroupServiceResponse listvByGroupServiceResponse = listValueResServiceImpl
				.getListValueByGroup(listReq);

		List<ListValueCacheDTO> countryCodeList = listvByGroupServiceResponse
				.getListValueCahceDTO();

		if (countryCodeList != null && countryCodeList.size() > 0) {
			cntryJSONLst = new ArrayList<CountryCodeJSONModel>();
			for (ListValueCacheDTO dto : countryCodeList) {
				CountryCodeJSONModel countryCodeJSONModel = new CountryCodeJSONModel();
				countryCodeJSONModel.setCntrCde(dto.getKey());
				countryCodeJSONModel.setCntrNam(dto.getLabel());
				cntryJSONLst.add(countryCodeJSONModel);
			}
		}
		return cntryJSONLst;
	}

	private CasaAccountJSONModel getCASAAccount(CustomerAccountDTO account,
			ResponseContext response) {
		CasaAccountJSONModel accountJSONModel = null;
		if (account != null) {
			CASAAccountDTO casaAcct = (CASAAccountDTO) account;
			accountJSONModel = new CasaAccountJSONModel(casaAcct);
			// CHECK added masking
			/*accountJSONModel.setMkdActNo(getMaskedAccountNumber(casaAcct
					.getBranchCode(), casaAcct.getAccountNumber()));*/

			  if(branchCodeCountryList.contains(BMBContextHolder.getContext().getBusinessId())){
				accountJSONModel.setMkdActNo(getMaskedAccountNumber(casaAcct.getBranchCode(), casaAcct.getAccountNumber()));
			    }
			    else {
				accountJSONModel.setMkdActNo(getMaskedAccountNumber(null, casaAcct.getAccountNumber()));
			    }

			accountJSONModel.setActNo(getCorrelationId(casaAcct
					.getAccountNumber(), response));

		}
		return accountJSONModel;

	}

	public ListValueResServiceImpl getListValueResServiceImpl() {
		return listValueResServiceImpl;
	}

	public void setListValueResServiceImpl(
			ListValueResServiceImpl listValueResServiceImpl) {
		this.listValueResServiceImpl = listValueResServiceImpl;
	}

	protected String getLabel(Context responseCtx, String key, String group) {

		String label = "";
		ListValueResServiceRequest listReq = new ListValueResServiceRequest();
		listReq.setContext(responseCtx);
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
	 *
	 * @param responses
	 * @return
	 */
	protected TransactionLimitOperationResponse getTransactionLimitOperationResponse(
			ResponseContext... responses) {
		TransactionLimitOperationResponse transactionLimitOperationResponse = (TransactionLimitOperationResponse) responses[2];

		return transactionLimitOperationResponse;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	protected List<String> getSysParamListById(Context context, String paramId,
			String activityId) {
		String params = getSysParamValue(context, paramId, activityId);

		List<String> valueList = new ArrayList<String>();

		if (params != null) {
			String[] valueArray = params.split(",");

			for (String value : valueArray) {
				valueList.add(value);
			}

			return valueList;
		}

		return valueList;
	}

	// Added to get only system parameter value based on param id...
	protected String getSysParamValue(Context context, String paramId,
			String activityId) {

		String paramValue = "";

		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

		systemParameterDTO.setBusinessId(context.getBusinessId());
		systemParameterDTO.setSystemId(context.getSystemId());
		systemParameterDTO.setActivityId(activityId);
		systemParameterDTO.setParameterId(paramId);

		SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
		systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

		SystemParameterServiceResponse response = getSystemParameterService()
				.getSystemParameter(systemParameterServiceRequest);

		if (response != null && response.getSystemParameterDTO() != null) {
			paramValue = response.getSystemParameterDTO().getParameterValue();
		}

		return paramValue;
	}

}
