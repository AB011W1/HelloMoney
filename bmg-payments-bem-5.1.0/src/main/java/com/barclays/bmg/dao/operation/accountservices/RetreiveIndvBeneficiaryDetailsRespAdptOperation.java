package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.IndividualBeneficiary.IndividualBeneficiary;
import com.barclays.bem.RetrieveIndividualBeneficiaryDetails.IndividualBeneficiaryInfo;
import com.barclays.bem.RetrieveIndividualBeneficiaryDetails.RetrieveIndividualBeneficiaryDetailsResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;

public class RetreiveIndvBeneficiaryDetailsRespAdptOperation extends AbstractResAdptOperation{

	public RetreiveBeneficiaryDetailsServiceResponse adaptResponse(WorkContext workContext, Object obj){

		RetreiveBeneficiaryDetailsServiceResponse response =
			new RetreiveBeneficiaryDetailsServiceResponse();

		RetrieveIndividualBeneficiaryDetailsResponse bemResponse = (RetrieveIndividualBeneficiaryDetailsResponse) obj;
		response.setSuccess(checkResponseHeader(bemResponse.getResponseHeader()));
		if(checkResponseHeader(bemResponse.getResponseHeader())){
			response.setBeneficiaryDTO(getBeneficiaryInfo(bemResponse));
			response.setSuccess(true);
		}else{
			response.setSuccess(false);
		}
		return response;
	}

	private BeneficiaryDTO getBeneficiaryInfo(RetrieveIndividualBeneficiaryDetailsResponse bemResponse){
		IndividualBeneficiaryInfo individualBeneficiaryInfo = bemResponse.getIndividualBeneficiaryInfo();

		BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();

		if(individualBeneficiaryInfo.getIndividualBeneficiary()!=null){

			IndividualBeneficiary individualBeneficiary = individualBeneficiaryInfo.getIndividualBeneficiary();

            beneficiaryDTO.setPayeeId(individualBeneficiary.getBeneficiaryID());
            beneficiaryDTO.setPayeeNickname(individualBeneficiary.getBeneficiaryNickName());
            if (null != individualBeneficiary.getBeneficiaryAccountInfo()) {
                beneficiaryDTO.setDestinationAccountNumber(individualBeneficiary
                        .getBeneficiaryAccountInfo().getAccountNumber());
                // TODO:
                beneficiaryDTO.setCardNumber(individualBeneficiary.getBeneficiaryAccountInfo()
                        .getAccountNumber());
            }
            // beneficiaryDTO.setPayeeTypeCode(individualBeneficiary.getBeneficiaryType());
            // beneficiaryDTO.setStatus(individualBeneficiary.getBeneficiaryStatusCode());
            beneficiaryDTO.setPayeeTypeCode(individualBeneficiary.getBeneficiaryTypeCode());
            beneficiaryDTO.setStatus(individualBeneficiary.getBeneficiaryLifecycleStatusCode());

            beneficiaryDTO.setPaymentChannelId(individualBeneficiary
                    .getBeneficiaryPaymentChannelTypeCode());
            beneficiaryDTO.setDestinationBankClearingNetCode(individualBeneficiary
                    .getBeneficiaryPaymentChannelTypeCode());
            beneficiaryDTO.setIntermediaryBankClearingNetCode(individualBeneficiary
                    .getBeneficiaryPaymentChannelTypeCode());
            if (null != individualBeneficiary.getBeneficiaryName()) {
                beneficiaryDTO.setBeneficiaryName(individualBeneficiary.getBeneficiaryName());
            }
            if (null != individualBeneficiary.getBeneficiaryPostalAddress_IndividualBeneficiary()
                    && null != individualBeneficiary.getBeneficiaryPostalAddress_IndividualBeneficiary()
                            .getUnStructuredAddress()) {
                beneficiaryDTO.setDestinationAddressLine1(individualBeneficiary
                        .getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress().getAddressLine1());
                beneficiaryDTO.setDestinationAddressLine2(individualBeneficiary
                        .getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress().getAddressLine2());
                beneficiaryDTO.setDestinationAddressLine3(individualBeneficiary
                        .getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress().getAddressLine3());
                beneficiaryDTO.setDestinationIsoCountryCode(individualBeneficiary
                        .getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress().getCountryCode());
            }
            if (null == individualBeneficiary.getBeneficiaryResidenceStatusCode()) {
                beneficiaryDTO.setDestinationResdentStatus("NRES");
            } else {
                beneficiaryDTO.setDestinationResdentStatus(Boolean.getBoolean(individualBeneficiary.getBeneficiaryResidenceStatusCode()) ? "RES"
                        : "NRES");
            }
            if (null != individualBeneficiary.getBeneficiaryTelephoneAddress()
            		&& individualBeneficiary.getBeneficiaryTelephoneAddress().length > 0) {
            	beneficiaryDTO.setContactNumber(individualBeneficiary.getBeneficiaryTelephoneAddress()[0].getPhoneNumber());
            }

            if (null != individualBeneficiary.getBeneficiaryBank()) {
                beneficiaryDTO.setDestinationBankName(individualBeneficiary.getBeneficiaryBank()
                        .getBankName());
                if (null != individualBeneficiary.getBeneficiaryBank()
                        && null != individualBeneficiary.getBeneficiaryBank()
                                .getBankAddress()
                        && null != individualBeneficiary.getBeneficiaryBank()
                                .getBankAddress().getUnStructuredAddress()) {
                    beneficiaryDTO.setDestinationBankAddressLine1(individualBeneficiary
                            .getBeneficiaryBank().getBankAddress().getUnStructuredAddress()
                            .getAddressLine1());
                    beneficiaryDTO.setDestinationBankAddressLine2(individualBeneficiary
                            .getBeneficiaryBank().getBankAddress().getUnStructuredAddress()
                            .getAddressLine2());
                    beneficiaryDTO.setDestinationBankIsoCountryCode(individualBeneficiary
                            .getBeneficiaryBank().getBankAddress().getUnStructuredAddress()
                            .getCountryCode());
                }
                if (null != individualBeneficiary.getBeneficiaryBank().getISOBankCode()) {
                    beneficiaryDTO.setDestinationBankCode(individualBeneficiary
                            .getBeneficiaryBank().getISOBankCode().getBankCode());
                    beneficiaryDTO.setDestinationBranchCode(individualBeneficiary
                            .getBeneficiaryBank().getISOBankCode().getBranchCode());

                }
                beneficiaryDTO.setDestinationBankChipsUid(individualBeneficiary
                        .getBeneficiaryBank().getCHIPSUID());
                beneficiaryDTO.setDestinationBankClearingCode(individualBeneficiary
                        .getBeneficiaryBank().getBankClearingCode());
                beneficiaryDTO.setDestinationBankSwiftCode(individualBeneficiary
                        .getBeneficiaryBank().getBankSWIFTCode());
                beneficiaryDTO.setDestinationBankClearingNetCode(individualBeneficiary
                        .getBeneficiaryBank().getBankRoutingNumber());
                beneficiaryDTO.setDestinationBankClearingNetCode(individualBeneficiary
                        .getBeneficiaryBank().getBankClearingNetworkCode());
                beneficiaryDTO.setDestinationBranchName(individualBeneficiary.getBeneficiaryBank().getBranchName());
            }

            if (null != individualBeneficiary.getIntermediatoryBankInfo()) {
                beneficiaryDTO.setIntermediaryBankName(individualBeneficiary
                        .getIntermediatoryBankInfo().getBankName());
                if (null != individualBeneficiary.getIntermediatoryBankInfo()
                        && null != individualBeneficiary.getIntermediatoryBankInfo()
                                .getBankAddress()
                        && null != individualBeneficiary.getIntermediatoryBankInfo()
                                .getBankAddress().getUnStructuredAddress()) {
                    beneficiaryDTO.setIntermediaryBankAddressLine1(individualBeneficiary
                            .getIntermediatoryBankInfo().getBankAddress().getUnStructuredAddress()
                            .getAddressLine1());
                    beneficiaryDTO.setIntermediaryBankAddressLine2(individualBeneficiary
                            .getIntermediatoryBankInfo().getBankAddress().getUnStructuredAddress()
                            .getAddressLine2());
                    beneficiaryDTO.setIntermediaryBankIsoCountryCode(individualBeneficiary
                            .getIntermediatoryBankInfo().getBankAddress().getUnStructuredAddress()
                            .getCountryCode());

                }
                beneficiaryDTO.setIntermediaryBankChipsUid(individualBeneficiary
                        .getIntermediatoryBankInfo().getCHIPSUID());
                beneficiaryDTO.setIntermediaryBankClearingCode(individualBeneficiary
                        .getIntermediatoryBankInfo().getBankClearingCode());
                beneficiaryDTO.setIntermediaryBankSwiftCode(individualBeneficiary
                        .getIntermediatoryBankInfo().getBankSWIFTCode());
                beneficiaryDTO.setIntermediaryBankClearingNetCode(individualBeneficiary
                        .getIntermediatoryBankInfo().getBankRoutingNumber());
            }

            // TODO: check this field
            beneficiaryDTO.setStatus(individualBeneficiary.getBeneficiaryLifecycleStatusCode());
            if (null != individualBeneficiary.getIBANFlag()) {
                beneficiaryDTO.setDestinationAccountIbanFlg(individualBeneficiary.getIBANFlag());
            } else {
                beneficiaryDTO.setDestinationAccountIbanFlg(false);
            }

            beneficiaryDTO.setDeliveryType("MB");

		}
		return beneficiaryDTO;
	}
}
