package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.DeleteIndividualCustBeneficiary.DeleteIndividualCustomerBeneficiaryRequest;
import com.barclays.bmg.dao.accountservices.adapter.DeleteBeneficiaryHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.DeleteBeneficiaryPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryReqAdptOperation {

	private DeleteBeneficiaryHeaderAdapter deleteBeneficiaryHeaderAdapter;
	private DeleteBeneficiaryPayloadAdapter deleteBeneficiaryPayloadAdapter;


	/**
	 * @param deleteBeneficiaryHeaderAdapter
	 */
	public final void setDeleteBeneficiaryHeaderAdapter(
			final DeleteBeneficiaryHeaderAdapter deleteBeneficiaryHeaderAdapter) {
		this.deleteBeneficiaryHeaderAdapter = deleteBeneficiaryHeaderAdapter;
	}


	/**
	 * @param deleteBeneficiaryPayloadAdapter
	 */
	public final void setDeleteBeneficiaryPayloadAdapter(
			final DeleteBeneficiaryPayloadAdapter deleteBeneficiaryPayloadAdapter) {
		this.deleteBeneficiaryPayloadAdapter = deleteBeneficiaryPayloadAdapter;
	}


	/**
	 * @param context
	 * @return DeleteIndividualCustomerBeneficiaryRequest
	 */
	public final DeleteIndividualCustomerBeneficiaryRequest adaptRequestforDeleteBeneficiary(final WorkContext context){

		DeleteIndividualCustomerBeneficiaryRequest request = new DeleteIndividualCustomerBeneficiaryRequest();
		request.setRequestHeader(deleteBeneficiaryHeaderAdapter.buildRequestHeader(context));
		request.setIndividualBeneficiaryInfo(deleteBeneficiaryPayloadAdapter.adaptPayload(context));
		return request;
	}

}
