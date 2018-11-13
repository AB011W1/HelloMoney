package com.barclays.bmg.chequebook.operation;

import java.util.List;

import com.barclays.bmg.chequebook.operation.request.ChequeBookValidationOperationRequest;
import com.barclays.bmg.chequebook.operation.response.ChequeBookValidationOperationResponse;
import com.barclays.bmg.constants.ChequeBookResponseCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.BMBCommonOperation;

public class ChequeBookRequestValidationOperation extends BMBCommonOperation {

	private List<String> chequeBookTypeCountryList;

	/**
	 * Validate input for cheque book request
	 *
	 * @param request
	 * @return
	 */

	public ChequeBookValidationOperationResponse chequeBookValidate(
			ChequeBookValidationOperationRequest request) {

		ChequeBookValidationOperationResponse chequeBookValidationOperationResponse = new ChequeBookValidationOperationResponse();

		Context context = request.getContext();
		String bkSize = request.getBkSize();
		String bkTyp = request.getBkTyp();

		chequeBookValidationOperationResponse.setContext(context);

		if (!validateChequeBookSize(chequeBookValidationOperationResponse,
				bkSize)) {
			chequeBookValidationOperationResponse.setSuccess(false);
			chequeBookValidationOperationResponse
					.setResCde(ChequeBookResponseCodeConstant.CHEQUE_BOOK_SIZE_INVALID);
			getMessage(chequeBookValidationOperationResponse);

		} else {
			if (chequeBookTypeCountryList.contains(context.getBusinessId())) {
				if (!validateChequeBookType(
						chequeBookValidationOperationResponse, bkTyp)) {
					chequeBookValidationOperationResponse.setSuccess(false);
					chequeBookValidationOperationResponse
							.setResCde(ChequeBookResponseCodeConstant.CHEQUE_BOOK_TYPE_INVALID);
					getMessage(chequeBookValidationOperationResponse);

				}
			}
		}

		if (chequeBookValidationOperationResponse.isSuccess()) {
			chequeBookValidationOperationResponse.setTxnRefNo(request
					.getContext().getOrgRefNo());
			// chequeBookValidationOperationResponse.setBkSize(bkSize);
			// chequeBookValidationOperationResponse.setBkTyp(bkTyp);
			chequeBookValidationOperationResponse
					.setBrnCde(request.getBrnCde());
			chequeBookValidationOperationResponse
					.setBrnNam(request.getBrnNam());
		}

		return chequeBookValidationOperationResponse;

	}

	/**
	 * Validate cheque book size
	 *
	 * @param context
	 * @param bkSize
	 * @return
	 */

	private boolean validateChequeBookSize(
			ChequeBookValidationOperationResponse response, String bkSize) {

		boolean isValid = false;

		String label = getListValueLabel(response.getContext(),
				"ACCT_CASA_CHEQUEBOOK_SIZE", bkSize);

		if (label != null && !"".equals(label)) {
			isValid = true;
			response.setBkSize(label);
		}

		/*
		 * for(ListValueCacheDTO listVal : getListValueByGroup(context,
		 * "ACCT_CASA_CHEQUEBOOK_SIZE")){ if(bkSize.equals(listVal.getLabel())){
		 * isValid = true; break; } }
		 */

		return isValid;

	}

	/**
	 * Validate cheque book type
	 *
	 * @param context
	 * @param bkTyp
	 * @return
	 */

	private boolean validateChequeBookType(
			ChequeBookValidationOperationResponse response, String bkTyp) {

		boolean isValid = false;

		String label = getListValueLabel(response.getContext(),
				"ACCT_CASA_CHEQUEBOOK_TP", bkTyp);

		if (label != null && !"".equals(label)) {
			isValid = true;
			response.setBkTyp(label);
		}

		/*
		 * for(ListValueCacheDTO listVal : getListValueByGroup(context,
		 * "ACCT_CASA_CHEQUEBOOK_TP")){ if(bkTyp.equals(listVal.getLabel())){
		 * isValid = true; break; } }
		 */

		return isValid;
	}

	public List<String> getChequeBookTypeCountryList() {
		return chequeBookTypeCountryList;
	}

	public void setChequeBookTypeCountryList(
			List<String> chequeBookTypeCountryList) {
		this.chequeBookTypeCountryList = chequeBookTypeCountryList;
	}

}
