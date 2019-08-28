package com.barclays.bmg.mapper;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.TimeDepositAccountSummary.TimeDepositAccountSummary;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.dto.TermDepositDTO;
import com.barclays.bmg.utils.ConvertUtils;

public class TimeDepositAccountMapper {

    public List<TdAccountDTO> mapCollection(TimeDepositAccountSummary[] sourceObject) {

	List<TdAccountDTO> tdAccountDTOList = new ArrayList<TdAccountDTO>();
	for (TimeDepositAccountSummary cas : sourceObject) {
	    tdAccountDTOList.add(mapBean(cas, null));

	}

	return tdAccountDTOList;

    }

    public TdAccountDTO mapBean(TimeDepositAccountSummary sourceObject, TdAccountDTO destiObject) {
	TdAccountDTO destiObject1 = destiObject;
	if (null == destiObject1) {
	    destiObject1 = new TdAccountDTO();
	}

	if (sourceObject != null) {
	    TermDepositDTO termDepoistDTO = new TermDepositDTO();

	    destiObject1.setAccountNumber(sourceObject.getAccountNumber());
	    destiObject1.setCurrency(sourceObject.getAccountCurrencyCode());
	    if (destiObject1 != null) {
		destiObject1.setProductCode(sourceObject.getProduct().getProductCode());
		destiObject1.setProductName(sourceObject.getProduct().getProductName());
		destiObject1.setDesc(sourceObject.getProduct().getProductName());

	    }
	    if (sourceObject.getDomicileBranch() != null) {
		destiObject1.setBranchCode(sourceObject.getDomicileBranch().getBranchCode());
	    }
	    termDepoistDTO.setOriginalDepositNumber(sourceObject.getOriginalDepositNumber());
	    termDepoistDTO.setDepositNumber(sourceObject.getDepositNumber());

	    // /*
	    // * Tao.Shang <tao.shang>, 20/07/2010: BEM service returns deposit number 0 and original deposit number 151568, BIR passes the same
	    // values to service RetrieveTermDepositDetails, The service does not return correct TD details. Bala (BIR SG) will discuss to BEM team to
	    // correct. For the time being, BIR will shift the values and passed to BEM service and make the feature run. But there will be risky.
	    // */
	    // termDepoistDTO.setOriginalDepositNumber(sourceObject.getDepositNumber());
	    // termDepoistDTO.setDepositNumber(sourceObject.getOriginalDepositNumber());

	    // destiObject.setAvailableBalance(ConvertUtils.convertBalance(sourceObject.getArrangementBalance(), ConvertConstants.AVAILABLEBALANCE));
	    if (sourceObject.getAccountCurrentBalance() != null) {
		destiObject1.setCurrentBalance(ConvertUtils.convertAmount(sourceObject.getAccountCurrentBalance().getAccountCCYBalance()));
	    }
	    destiObject1.setDepositDTO(termDepoistDTO);
	    destiObject1.setAccountStatus(sourceObject.getAccountLifecycleStatusCode());
	    destiObject1.setRelationshipCode(ConvertUtils.getRelShipTpeCode(sourceObject.getJointAccountRelationship()));

	}
	return destiObject1;

    }
}
