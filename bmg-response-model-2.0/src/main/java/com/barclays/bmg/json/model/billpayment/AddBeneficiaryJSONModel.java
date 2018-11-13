package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.lookup.BranchJSONModel;

/**
 * @author BTCI
 *
 */
public class AddBeneficiaryJSONModel extends ExternalBeneficiaryJSONModel
		implements Serializable {

	private static final long serialVersionUID = -5436515665377036572L;

	private BranchJSONModel branchInfo;

	/**
	 * @param beneficiaryDTO
	 */
	public AddBeneficiaryJSONModel(BeneficiaryDTO beneficiaryDTO) {

		super(beneficiaryDTO);

		branchInfo = new BranchJSONModel();
		branchInfo.setBrnNam(beneficiaryDTO.getDestinationBranchName());
		branchInfo.setBrnCde(beneficiaryDTO.getDestinationBranchCode());
		branchInfo.setBnkNam(beneficiaryDTO.getDestinationBankName());
		branchInfo.setBnkCde(beneficiaryDTO.getDestinationBankCode());
	}

	/**
	 * @return BranchJSONModel
	 */
	public BranchJSONModel getBranchInfo() {
		return branchInfo;
	}

	/**
	 * @param branchInfo
	 */
	public void setBranchInfo(BranchJSONModel branchInfo) {
		this.branchInfo = branchInfo;
	}

}
