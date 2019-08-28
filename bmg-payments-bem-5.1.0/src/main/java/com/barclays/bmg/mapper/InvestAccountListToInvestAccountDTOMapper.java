/*
 * Copyright (c) 2009 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.mapper;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.InvestmentAccount.InvestmentAccount;
import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.dto.NAV;
import com.barclays.bmg.utils.ConvertUtils;

public class InvestAccountListToInvestAccountDTOMapper {

    public List<InvestmentAccountDTO> mapCollection(InvestmentAccount[] sourceObject) {

	List<InvestmentAccountDTO> investmentAccountDTOList = new ArrayList<InvestmentAccountDTO>();
	for (InvestmentAccount cas : sourceObject) {
	    investmentAccountDTOList.add(mapBean(cas, null));

	}

	return investmentAccountDTOList;

    }

    public InvestmentAccountDTO mapBean(InvestmentAccount source, InvestmentAccountDTO target) {
	InvestmentAccountDTO target1 = target;
	if (target1 == null) {
	    target1 = new InvestmentAccountDTO();
	}

	if (source.getProduct() != null) {
	    target1.setFundName(source.getProduct().getProductName());
	    target1.setInvestmentName(source.getProduct().getProductName());
	    target1.setProductName(source.getProduct().getProductName());
	}

	target1.setNoOfUnit(ConvertUtils.convertAmount(source.getNumberOfUnits()));
	target1.setCurrency(source.getFundCurrencyCode());
	NAV currentNAV = new NAV();
	currentNAV.setNavAmount(ConvertUtils.convertNAVAmountInString(source.getCurrentNAVAmount()));
	target1.setCurrentNAV(currentNAV);

	target1.setAsOf(ConvertUtils.convertDate(source.getCurrentNAVDate()));

	target1.setCurrentMarketValue(ConvertUtils.convertAmount(source.getCurrentMarketValueAmount()));
	target1.setTotalAmountInvested(ConvertUtils.convertAmount(source.getTotalInvestmentAmount()));
	target1.setDailygrowthDecline(ConvertUtils.convertAmount(source.getDailyNAVGrowthDeclinePercentage()));

	target1.setMonthToDateGrowth(ConvertUtils.convertAmount(source.getNAVGrowthDeclinePercentageMTD()));
	target1.setYearToDateGrowthDecline(ConvertUtils.convertAmount(source.getNAVGrowthDeclinePercentageYTD()));

	target1.setDividendInterestReceived(ConvertUtils.convertAmount(source.getDividendInterestReceived()));

	target1.setLienUnits(ConvertUtils.convertAmount(source.getLienUnits()));
	target1.setRedeemedUnits(ConvertUtils.convertAmount(source.getRedeemedUnits()));

	return target1;
    }
}
