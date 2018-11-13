package com.barclays.bmg.json.model.builder.fundtransfer.international;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.billpayment.ExternalBankJSONModel;
import com.barclays.bmg.json.model.billpayment.ExternalBeneficiaryJSONModel;

public class InternationalFundTransferCountryCheckUtility {

	public static void bankCountryCodeCheck(
			ExternalBeneficiaryJSONModel beneficiaryJSONModel,
			BeneficiaryDTO beneficiaryDTO) {

		String beneCntryCde = "";

		if (beneficiaryDTO != null
				&& beneficiaryDTO.getDestinationBankIsoCountryCode() != null) {
			beneCntryCde = beneficiaryDTO.getDestinationBankIsoCountryCode();
		}

		beneficiaryJSONModel.getBeneBank().setCtrCde(beneCntryCde);

		if (StringUtils.isNotEmpty(beneCntryCde)
				&& (beneCntryCde.equals("GB") || beneCntryCde.equals("US")
						|| beneCntryCde.equals("IN") || beneCntryCde
						.equals("IM"))) {

			beneficiaryJSONModel.getBeneBank().setClrCode(
					beneficiaryDTO.getDestinationBankClearingCode());
			beneficiaryJSONModel.getBeneBank().setClrNetCode(
					beneficiaryDTO.getDestinationBankClearingNetCode());

		} else {

			beneficiaryJSONModel.getBeneBank().setClrCode("");
			beneficiaryJSONModel.getBeneBank().setClrNetCode("");
		}

		if (beneficiaryJSONModel.getIntBankLst() != null) {
			setAllIntBnkClrCde(beneficiaryJSONModel.getIntBankLst(),
					beneficiaryDTO);
		}

	}

	private static void setAllIntBnkClrCde(
			List<ExternalBankJSONModel> intBnkLst, BeneficiaryDTO beneficiaryDTO) {

		if (!intBnkLst.isEmpty()) {
			for (ExternalBankJSONModel intBankList : intBnkLst) {
				intBankList.setClrCode(beneficiaryDTO
						.getIntermediaryBankClearingCode());
				intBankList.setClrNetCode("");
			}
		}
	}
}
