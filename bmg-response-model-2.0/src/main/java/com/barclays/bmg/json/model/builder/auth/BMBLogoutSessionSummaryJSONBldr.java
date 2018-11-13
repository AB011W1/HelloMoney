package com.barclays.bmg.json.model.builder.auth;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.SessionActivityType;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.exception.BMBCustomClassCastException;
import com.barclays.bmg.json.model.KeyValueJSONModel;
import com.barclays.bmg.json.model.LogoutJSONModel;
import com.barclays.bmg.json.model.SessionActivityJSONModel;
import com.barclays.bmg.json.model.builder.BMBCommonJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.operation.response.SessionSummaryOperationResponse;
import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.sessionactivity.dto.SessionSummaryDTO;
import com.barclays.bmg.service.sessionactivity.util.SessionActivityUtility;
import com.barclays.bmg.utils.BMGFormatUtility;

public class BMBLogoutSessionSummaryJSONBldr extends BMBCommonJSONBuilder implements BMBJSONBuilder {

	@Override
	public Object createJSONResponse(ResponseContext responseContext) {

		if(responseContext instanceof SessionSummaryOperationResponse) {
			SessionSummaryOperationResponse resp = (SessionSummaryOperationResponse) responseContext;

			BMBPayload bmbPayload = new BMBPayload(createHeader());

			populatePayLoad(resp, bmbPayload);

			return bmbPayload;
		} else {
			throw new BMBCustomClassCastException("BMB Custom Class Cast Exception");
		}
	}

	protected BMBPayloadHeader createHeader() {

		BMBPayloadHeader header = new BMBPayloadHeader();
		header.setResCde(ResponseCodeConstants.SUCCESS_RES_CODE);
		header.setResMsg("");
		header.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
		header.setResId(ResponseIdConstants.LOGOUT_RESPONSE_ID);

		return header;

	}

	protected void populatePayLoad(
			SessionSummaryOperationResponse sessionSummaryOperationResponse,
			BMBPayload bmbPayload) {

		LogoutJSONModel logoutJSONModel = new LogoutJSONModel();

		SessionSummaryDTO sessionSummary = sessionSummaryOperationResponse
				.getSessionSummaryDTO();

		if (sessionSummary != null) {
			SessionActivityUtility.formatSessionSummary(sessionSummary);

			logoutJSONModel.setDur(sessionSummary.getDuration());
			logoutJSONModel.setDurHrs(sessionSummary.getDurationHours());
			logoutJSONModel.setDurMins(sessionSummary.getDurationMinutes());
			logoutJSONModel.setDurSecs(sessionSummary.getDurationSeconds());
			logoutJSONModel.setLgnTm(BMGFormatUtility
					.getLongDate(sessionSummary.getLoginTime()));
			logoutJSONModel.setLgtTm(BMGFormatUtility
					.getLongDate(sessionSummary.getLogoutTime()));

			List<SessionActivityDTO> sessList = sessionSummary
					.getSessionActivityList();

			if (sessList != null && sessList.size() > 0) {
				List<SessionActivityJSONModel> seList = new ArrayList<SessionActivityJSONModel>(
						sessList.size());
				logoutJSONModel.setActvToDis(true);
				for (SessionActivityDTO sessActivityDTO : sessList) {

					if (!sessActivityDTO.getTxnTyp().equals("LOG_IN")
							&& !sessActivityDTO.getTxnTyp().equals("LOG_OUT")) {

						SessionActivityJSONModel seModel = new SessionActivityJSONModel();
						seModel.setRefNo(sessActivityDTO.getRefNo());
						if (sessActivityDTO.getTxnTyp() != null
								&& (sessActivityDTO
										.getTxnTyp()
										.equals(
												SessionActivityType.PURCHASE_BANK_DRAFT) || sessActivityDTO
										.getTxnTyp()
										.equals(
												SessionActivityType.PURCHASE_MANAGER_CHEQUE))) {
							seModel.setSta(SessionActivityType.INPROCESS);
						} else {
							seModel.setSta(sessActivityDTO.getStatus());
						}
						seModel.setTxnTm(sessActivityDTO.getTxnTime());
					//	seModel.setTxnTyp(setTxnActivity(sessActivityDTO
					//			.getTxnTyp(), sessActivityDTO.getBusinessId()));
						seModel.setTxnTyp(getTxnActivity(sessActivityDTO
								.getTxnTyp(), sessionSummaryOperationResponse.getContext()));
						List<KeyValuePairBean> keyList = sessActivityDTO
								.getTxnDetails();
						if (keyList != null && keyList.size() > 0) {
							List<KeyValueJSONModel> keyList2 = new ArrayList<KeyValueJSONModel>(
									keyList.size());

							for (KeyValuePairBean keyPair : keyList) {
								KeyValueJSONModel keyModel = new KeyValueJSONModel(
										keyPair.getKey(), (String) keyPair
												.getValue());
								keyList2.add(keyModel);
							}
							seModel.setTxnDetls(keyList2);
						}
						seList.add(seModel);
					}
				}
				if (!seList.isEmpty()) {
					logoutJSONModel.setSsnActvLst(seList);
				}
			}
		}
		bmbPayload.setPayData(logoutJSONModel);
	}

//	private String setTxnActivity(String txnType, String businessId) {
//
//		String txnActivity = "";
//
//		if (SessionActivityType.FUND_TRANSFER_OWN.equals(txnType)) {
//			txnActivity = "Fund transfer to Own account";
//		} else if (SessionActivityType.FUND_TRANSFER_INTERNAL.equals(txnType)) {
//			txnActivity = "Fund Transfer to Domestic Barclays account";
//		} else if (SessionActivityType.FUND_TRANSFER_EXTERNAL.equals(txnType)) {
//			txnActivity = "Fund Transfer to Domestic Other Bank Account";
//		} else if (SessionActivityType.FUND_TRANSFER_INTERNATIONAL
//				.equals(txnType)) {
//
//			if (StringUtils.isNotEmpty(businessId) && businessId.equals("KEBRB")) {
//				txnActivity = "International Fund Transfer";
//			}else{
//				txnActivity = "International & Urgent Fund Transfer";
//			}
//
//		} else if (SessionActivityType.BILL_PAYMENT.equals(txnType)) {
//			txnActivity = "Pay Bill";
//		} else if (SessionActivityType.MOBILE_TOPUP.equals(txnType)) {
//			txnActivity = "Prepaid Mobile Recharge";
//		} else if (SessionActivityType.BARCLAY_CARD_PAYMENT.equals(txnType)) {
//			txnActivity = "Payment to Third Party Barclaycard";
//		} else if (SessionActivityType.FUND_TRANSFER_CARD_PAYMENT
//				.equals(txnType)) {
//			txnActivity = "Payment to Own Barclaycard";
//		} else if (SessionActivityType.PURCHASE_BANK_DRAFT.equals(txnType)) {
//			txnActivity = "Purchase Bank Draft";
//		} else if (SessionActivityType.PURCHASE_MANAGER_CHEQUE.equals(txnType)) {
//			txnActivity = "Purchase Manager's Cheque";
//		} else if (SessionActivityType.CHEQUE_BOOK_REQUEST.equals(txnType)) {
//			txnActivity = "Cheque Book Request";
//		} else {
//			txnActivity = txnType;
//		}
//		return txnActivity;
//	}

	private String getTxnActivity(String txnType, Context context) {

		String txnActivity = "";
		txnActivity = getScriptResource(context, txnType);

		return txnActivity;
	}

}
