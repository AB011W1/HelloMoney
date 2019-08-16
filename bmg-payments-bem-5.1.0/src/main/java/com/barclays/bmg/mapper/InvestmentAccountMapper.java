package com.barclays.bmg.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclays.bem.RetrieveIndividualCustAcctList.InvestmentAccount;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.sort.InvestmentAssetTypeComparator;
import com.barclays.bmg.utils.ConvertUtils;

public class InvestmentAccountMapper {

    public List<InvestmentAccountDTO> mapCollection(InvestmentAccount[] sourceObject) {

	List<InvestmentAccountDTO> investmentAccountDTOList = new ArrayList<InvestmentAccountDTO>();
	for (InvestmentAccount cas : sourceObject) {
	    investmentAccountDTOList.add(mapBean(cas, null));

	}

	InvestmentAssetTypeComparator comparator = new InvestmentAssetTypeComparator();

	Collections.sort(investmentAccountDTOList, comparator);

	return investmentAccountDTOList;

    }

    public InvestmentAccountDTO mapBean(InvestmentAccount sourceObject, InvestmentAccountDTO destiObject) {
	InvestmentAccountDTO destiObject1 = destiObject;
	if (null == destiObject1) {
	    destiObject1 = new InvestmentAccountDTO();
	}
	// SSCContext sscContext = (SSCContext)MCFEContextUtility.getMCFEContext();
	// ListValueResDAO listValueResDAO = (ListValueResDAO)SpringBeansUtils.getBeanForName("listValueResDAO");
	// List<ListValueResDTO> listValueCache = listValueResDAO.findListValueCache(sscContext, "ASSETTYPE");
	destiObject1.setAssetTypeCode(sourceObject.getAssetTypeCode());
	destiObject1.setCurrency(sourceObject.getCurrencyCode());
	destiObject1.setAsOf(ConvertUtils.convertDate(sourceObject.getAsOfDate()));
	destiObject1.setCurrentMarketValue(ConvertUtils.convertAmount(sourceObject.getCurrentMarketValueAmount()));
	// destiObject = setAssetTypeName(destiObject,listValueCache);
	return destiObject1;
    }
    /*
     * private InvestmentAccountDTO setAssetTypeName(InvestmentAccountDTO destiObject, List<ListValueResDTO> listValueCache) { // TODO Auto-generated
     * method stub if(destiObject!=null){ for(ListValueResDTO lv: listValueCache){ if(destiObject.getAssetTypeCode()!=null &&
     * lv.getListKey().equals(destiObject.getAssetTypeCode())){ destiObject.setAssetTypeName(lv.getListValue()); break; } } } return destiObject; }
     */

}
