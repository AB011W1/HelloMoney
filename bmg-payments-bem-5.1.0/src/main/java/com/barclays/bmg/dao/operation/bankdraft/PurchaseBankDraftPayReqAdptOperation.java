package com.barclays.bmg.dao.operation.bankdraft;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.Beneficiary.Beneficiary;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.DomesticDemandDraft.DeliveryInfo;
import com.barclays.bem.DomesticDemandDraft.DomesticDemandDraft;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.CASAAccountDomesticDemandDraft;
import com.barclays.bem.MakeDomesticDemandDraftByAccount.MakeDomesticDemandDraftByAccountRequest;
import com.barclays.bem.PostalAddress.PostalAddress;
import com.barclays.bem.PostalAddress.UnstructuredAddress;
import com.barclays.bem.Remitter.Remitter;
import com.barclays.bem.TelephoneAddress.TelephoneAddress;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BankDraftTransactionDTO;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.ContactInfoDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;

public class PurchaseBankDraftPayReqAdptOperation extends AbstractReqAdptOperation {

	public MakeDomesticDemandDraftByAccountRequest createBankDraftRequest(
			WorkContext workContext) {

		BEMReqHeader bemHeader = this
				.buildRequestHeader(
						workContext,
						ServiceIdConstants.SERVICE_MAKE_DOMESTIC_DEMAND_DRAFT_BY_ACCOUNT);
		MakeDomesticDemandDraftByAccountRequest makeDomesticDemandDraftByAccountRequest = new MakeDomesticDemandDraftByAccountRequest();

		makeDomesticDemandDraftByAccountRequest.setRequestHeader(bemHeader);

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest = (PurchaseBankDraftServiceRequest) args[0];

		CASAAccountDomesticDemandDraft caAccountDomesticDemandDraft = createPayload(purchaseBankDraftServiceRequest);


		makeDomesticDemandDraftByAccountRequest
				.setCASAAccountDomesticDemandDraftInfo(caAccountDomesticDemandDraft);

		//FIXME
		caAccountDomesticDemandDraft.setTransactionType("6");

		return makeDomesticDemandDraftByAccountRequest;
	}

	public CASAAccountDomesticDemandDraft createPayload(
			PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest) {
		CASAAccountDomesticDemandDraft destiObject = null;

		BankDraftTransactionDTO bankDraftTransactionDTO = purchaseBankDraftServiceRequest
				.getBankDraftTransactionDTO();

		CustomerAccountDTO customerAccountDTO = bankDraftTransactionDTO
				.getSourceAcct();
		BeneficiaryDTO beneficiaryDTO = bankDraftTransactionDTO
				.getBeneficiaryDTO();

		destiObject = new CASAAccountDomesticDemandDraft();

		destiObject.setAccountNumber(customerAccountDTO.getAccountNumber());
		Branch fromBranch = new Branch();
		fromBranch.setBranchCode(customerAccountDTO.getBranchCode());
		destiObject.setAccountBranch(fromBranch);
		DomesticDemandDraft draft = new DomesticDemandDraft();
		if (bankDraftTransactionDTO.getTxnAmt() != null) {
			draft.setDemandDraftAmount(bankDraftTransactionDTO.getTxnAmt()
					.getAmount().doubleValue());
			draft.setDemandDraftCurrencyCode(bankDraftTransactionDTO.getTxnAmt().getCurrency());
		}

		draft.setDDPayableAtCountry(bankDraftTransactionDTO.getPayableAtCode());
		if (bankDraftTransactionDTO.getFxRateDTO() != null) {
			TransactionFxRate rate = new TransactionFxRate();
			draft.setTransactionFxRate(rate);
		}

		DeliveryInfo delInfo = new DeliveryInfo();

		Branch branch = new Branch();
		branch.setBranchCode(bankDraftTransactionDTO.getBranchCode());
		branch.setBranchName(bankDraftTransactionDTO.getBranchName());
		delInfo.setBranch(branch);

		delInfo.setDeliveryModeCode(bankDraftTransactionDTO.getDeliveryType());

		PostalAddress address = new PostalAddress();
		UnstructuredAddress usadd = new UnstructuredAddress();
		ContactInfoDTO contactInfoDTO = bankDraftTransactionDTO
				.getRemitterContactInfoDTO();
		if ("BEN".equalsIgnoreCase(bankDraftTransactionDTO.getDeliveryType())) {
			usadd.setAddressLine1(beneficiaryDTO.getDestinationAddressLine1());
			usadd.setAddressLine2(beneficiaryDTO.getDestinationAddressLine2());
			usadd.setAddressLine3(beneficiaryDTO.getDestinationAddressLine3());
		} else if ("REM".equalsIgnoreCase(bankDraftTransactionDTO
				.getDeliveryType())) {
			usadd.setAddressLine1(contactInfoDTO.getAddressLineOne());
			usadd.setAddressLine2(contactInfoDTO.getAddressLineTwo());
			usadd.setAddressLine3(contactInfoDTO.getAddressLineThree());
			UnstructuredAddress remitterAddr = new UnstructuredAddress();
			remitterAddr.setAddressLine1(contactInfoDTO.getAddressLineOne());
			remitterAddr.setAddressLine2(contactInfoDTO.getAddressLineTwo());
			remitterAddr.setAddressLine3(contactInfoDTO.getAddressLineThree());
			Remitter remitterInfo = new Remitter();
			remitterInfo.setPostalAddress(new PostalAddress());
			remitterInfo.getPostalAddress()
					.setUnStructuredAddress(remitterAddr);
			remitterInfo.setRemitterName(new IndividualName());
			remitterInfo.getRemitterName().setFullName(
					contactInfoDTO.getFullName());
			draft.setRemitterInfo(remitterInfo);
		} else {
			usadd.setAddressLine1("address1");
			usadd.setAddressLine2("address2");
		}
		address.setUnStructuredAddress(usadd);
		delInfo.setMailAddress(address);
		draft.setDeliveryInfo(delInfo);
		draft.setNarrationText(bankDraftTransactionDTO.getTxnNot());
		draft.setDDPayableAtCountry(bankDraftTransactionDTO.getPayableAtCode());

		Beneficiary beneficiaryInfo = new Beneficiary();
		beneficiaryInfo.setBeneficiaryName(beneficiaryDTO.getBeneficiaryName());
		TelephoneAddress taddr = new TelephoneAddress();
		taddr.setPhoneNumber(contactInfoDTO.getContactNo());
		beneficiaryInfo
				.setBeneficiaryTelephoneAddress(new TelephoneAddress[] { taddr });
		draft.setBeneficiaryInfo(beneficiaryInfo);

		destiObject.setDomesticDemandDraft(draft);

		// Set Transaction Type
		destiObject.setTransactionType(bankDraftTransactionDTO.getDraftType());
		return destiObject;
	}

}
