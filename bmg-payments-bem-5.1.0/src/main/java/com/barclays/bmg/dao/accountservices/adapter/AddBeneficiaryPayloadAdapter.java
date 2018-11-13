package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.AddIndividualBeneficiary.AddIndividualBeneficiary;
import com.barclays.bem.Bank.Bank;
import com.barclays.bem.ISOBankCode.ISOBankCode;
import com.barclays.bem.IndividualBeneficiary.IndividualBeneficiary;
import com.barclays.bem.PostalAddress.PostalAddress;
import com.barclays.bem.PostalAddress.UnstructuredAddress;
import com.barclays.bem.TelephoneAddress.TelephoneAddress;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.service.request.AddBeneficiaryServiceRequest;

/**
 * @author BTCI
 *
 */
public class AddBeneficiaryPayloadAdapter {

	/**
	 * @param workContext
	 * @return AddIndividualBeneficiary
	 *
	 *         Response Adapter for Add Beneficiary BEM response
	 *
	 */
	public AddIndividualBeneficiary adaptPayload(WorkContext workContext) {

		AddIndividualBeneficiary requestBody = new AddIndividualBeneficiary();
		IndividualBeneficiary individualBeneficiary = new IndividualBeneficiary();

		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		AddBeneficiaryServiceRequest addBeneficiaryServiceRequest = (AddBeneficiaryServiceRequest) args[0];
		Context context = addBeneficiaryServiceRequest.getContext();
		requestBody.setCustomerNumber(context.getCustomerId());

		individualBeneficiary.setBeneficiaryID(addBeneficiaryServiceRequest
				.getPayeeId());
		BeneficiaryDTO beneficiaryDTO = addBeneficiaryServiceRequest
				.getBeneficiaryDTO();

		individualBeneficiary.setBeneficiaryID(beneficiaryDTO.getPayeeId());

		// account
		TransactionAccount beneficiaryAccountInfo = new TransactionAccount();
		if (null != beneficiaryDTO.getPayeeTypeCode()
				&& beneficiaryDTO.getPayeeTypeCode().equals("BCP")) {
			beneficiaryAccountInfo.setAccountNumber(beneficiaryDTO
					.getCardNumber());
		} else {
			beneficiaryAccountInfo.setAccountNumber(beneficiaryDTO
					.getDestinationAccountNumber());
		}
		beneficiaryAccountInfo.setAccountCurrencyCode(beneficiaryDTO
				.getCurrency());
		individualBeneficiary.setBeneficiaryAccountInfo(beneficiaryAccountInfo);

		individualBeneficiary.setBeneficiaryName(beneficiaryDTO
				.getBeneficiaryName());
		individualBeneficiary.setBeneficiaryNickName(beneficiaryDTO
				.getPayeeNickname());
		individualBeneficiary.setBeneficiaryTypeCode(beneficiaryDTO
				.getPayeeTypeCode());
		individualBeneficiary.setBeneficiaryPaymentChannelTypeCode("LOCAL");

		// address
		UnstructuredAddress unStructuredAddress = new UnstructuredAddress();
		unStructuredAddress.setAddressLine1(beneficiaryDTO
				.getDestinationAddressLine1());
		unStructuredAddress.setAddressLine2(beneficiaryDTO
				.getDestinationAddressLine2());
		unStructuredAddress.setAddressLine3(beneficiaryDTO
				.getDestinationAddressLine3());
		unStructuredAddress.setCountryCode(beneficiaryDTO
				.getDestinationIsoCountryCode());
		unStructuredAddress.setCityName(beneficiaryDTO.getCity());
		// residence status
		if (null == beneficiaryDTO.getDestinationResdentStatus()) {
			individualBeneficiary.setBeneficiaryResidenceStatusCode("false");
		} else {
			individualBeneficiary
					.setBeneficiaryResidenceStatusCode(beneficiaryDTO
							.getDestinationResdentStatus().equals("RES") ? "true"
							: "false");
		}

		PostalAddress beneficiaryPostalAddress = new PostalAddress();
		beneficiaryPostalAddress.setUnStructuredAddress(unStructuredAddress);
		individualBeneficiary
				.setBeneficiaryPostalAddress_IndividualBeneficiary(beneficiaryPostalAddress);

		TelephoneAddress taddr = new TelephoneAddress();
		taddr.setPhoneNumber(beneficiaryDTO.getContactNumber());
		individualBeneficiary
				.setBeneficiaryTelephoneAddress(new TelephoneAddress[] { taddr });

		// beneficiary bank
		Bank benficaryBank = new Bank();
		benficaryBank.setBankName(beneficiaryDTO.getDestinationBankName());
		benficaryBank.setBranchName(beneficiaryDTO.getDestinationBranchName());

		PostalAddress bankAddress = new PostalAddress();
		UnstructuredAddress unStructuredBankAddress = new UnstructuredAddress();
		unStructuredBankAddress.setAddressLine1(beneficiaryDTO
				.getDestinationBankAddressLine1());
		unStructuredBankAddress.setAddressLine2(beneficiaryDTO
				.getDestinationBankAddressLine2());
		unStructuredBankAddress.setCountryCode(beneficiaryDTO
				.getDestinationBankIsoCountryCode());
		bankAddress.setUnStructuredAddress(unStructuredBankAddress);
		benficaryBank.setBankAddress(bankAddress);

		// ISO bank
		ISOBankCode isoBankCode = new ISOBankCode();
		isoBankCode.setBankCode(beneficiaryDTO.getDestinationBankCode());
		isoBankCode.setBranchCode(beneficiaryDTO.getDestinationBranchCode());
		benficaryBank.setISOBankCode(isoBankCode);

		benficaryBank.setBankSWIFTCode(beneficiaryDTO
				.getDestinationBankSwiftCode());
		if (!beneficiaryDTO.getDestinationAccountIbanFlg()) {
			benficaryBank.setBankClearingCode(beneficiaryDTO
					.getDestinationBankClearingCode());
		}
		benficaryBank.setBankClearingNetworkCode(beneficiaryDTO
				.getDestinationBankClearingNetCode());

		// set to individual beneficiary
		individualBeneficiary.setBeneficiaryBank(benficaryBank);

		// intermediatory bank
		Bank intermediatoryBankInfo = new Bank();
		intermediatoryBankInfo.setBankName(beneficiaryDTO
				.getIntermediaryBankName());

		PostalAddress intermediaryBankAddress = new PostalAddress();
		// intermediaryBankAddress.setStructuredAddressFlag(false);
		UnstructuredAddress intermediaryUnStructuredBankAddress = new UnstructuredAddress();
		intermediaryUnStructuredBankAddress.setAddressLine1(beneficiaryDTO
				.getIntermediaryBankAddressLine1());
		intermediaryUnStructuredBankAddress.setAddressLine2(beneficiaryDTO
				.getIntermediaryBankAddressLine2());
		intermediaryUnStructuredBankAddress.setCountryCode(beneficiaryDTO
				.getIntermediaryBankIsoCountryCode());
		intermediaryBankAddress
				.setUnStructuredAddress(intermediaryUnStructuredBankAddress);
		intermediatoryBankInfo.setBankAddress(intermediaryBankAddress);

		intermediatoryBankInfo.setBankSWIFTCode(beneficiaryDTO
				.getIntermediaryBankSwiftCode());
		intermediatoryBankInfo.setBankClearingCode(beneficiaryDTO
				.getIntermediaryBankClearingCode());
		individualBeneficiary.setIntermediatoryBankInfo(intermediatoryBankInfo);

		// IBan flag
		individualBeneficiary.setIBANFlag(beneficiaryDTO
				.getDestinationAccountIbanFlg());
		individualBeneficiary.setNIB(beneficiaryDTO.getNib());

		requestBody.setIndividualBeneficiary(individualBeneficiary);

		requestBody.setAutoAuthorize("true");
		requestBody.setCustomerAuthMechanismTypeCode(beneficiaryDTO
				.getCustomerAuthMechanismTypeCode());

		return requestBody;

	}

}
