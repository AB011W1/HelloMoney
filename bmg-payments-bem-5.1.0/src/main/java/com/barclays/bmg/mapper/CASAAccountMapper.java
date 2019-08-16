package com.barclays.bmg.mapper;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.CASAAccountSummary.CASAAccountSummary;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.utils.ConvertUtils;

public class CASAAccountMapper {

    public List<CASAAccountDTO> mapCollection(CASAAccountSummary[] sourceObject) {

	List<CASAAccountDTO> cASAAccountDTOList = new ArrayList<CASAAccountDTO>();
	for (CASAAccountSummary cas : sourceObject) {
	    cASAAccountDTOList.add(mapBean(cas, null));

	}

	return cASAAccountDTOList;

    }

    public CASAAccountDTO mapBean(CASAAccountSummary sourceObject, CASAAccountDTO destiObject) {
	CASAAccountDTO result = null;
	if (null == destiObject) {
	    result = new CASAAccountDTO();
	} else {
	    result = destiObject;
	}

	result.setProductCode(sourceObject.getProduct().getProductCode());
	if (sourceObject.getDomicileBranch() != null) {
	    result.setBranchCode(sourceObject.getDomicileBranch().getBranchCode());
	}

	result.setProductName(sourceObject.getProduct().getProductName());
	result.setAccountNumber(sourceObject.getAccountNumber());
	result.setIban(sourceObject.getIBANAccountNumber());

	// Tim 2010-09-21 update for casa is internet availble start
	if (null != sourceObject.getInternetBankingAccessFlag()) {
	    result.setInternetBankingAccessFlag(sourceObject.getInternetBankingAccessFlag());
	}

	// Tim 2010-09-23 update for casa is internet availble end
	// Tim 2010-09-21 update for casa is internet availble end
	result.setDesc(sourceObject.getProduct().getProductName());
	result.setCurrentBalance(ConvertUtils.convertAmount((sourceObject.getAccountCurrentBalance().getAccountCCYBalance())));
	result.setAvailableBalance(ConvertUtils.convertAmount(sourceObject.getAccountAvailableBalance().getAccountCCYBalance()));
	result.setCurrency(sourceObject.getAccountCurrencyCode());
	result.setAccountStatus(sourceObject.getAccountLifecycleStatusCode());

	result.setRelationshipCode(ConvertUtils.getRelShipTpeCode(sourceObject.getJointAccountRelationship()));
	if (sourceObject.getAccountAttributes() != null && sourceObject.getAccountAttributes().getIsChequeSupportedFlag() != null) {
	    result.setCheckSupportFlag(sourceObject.getAccountAttributes().getIsChequeSupportedFlag());
	} else {
	    result.setCheckSupportFlag(true);
	}
	// Support Check Flag
	return result;
    }

}
