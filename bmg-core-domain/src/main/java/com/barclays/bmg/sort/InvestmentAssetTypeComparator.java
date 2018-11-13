package com.barclays.bmg.sort;

import java.io.Serializable;
import java.util.Comparator;
import com.barclays.bmg.dto.InvestmentAccountDTO;

public class InvestmentAssetTypeComparator implements Comparator<InvestmentAccountDTO>, Serializable {


	private static final long serialVersionUID = -7766078970523190312L;

	@Override
    public int compare(InvestmentAccountDTO object1, InvestmentAccountDTO object2) {

	int compareInd = 0;

	if (object1 != null && object2 != null) {
	    String assetTypeName1 = object1.getAssetTypeName();
	    String assetTypeName2 = object2.getAssetTypeName();
	    String assetTypeCode1 = object1.getAssetTypeCode();
	    String assetTypeCode2 = object2.getAssetTypeCode();

	    if (assetTypeName1 != null && assetTypeName2 != null) {
		compareInd = assetTypeName1.compareToIgnoreCase(assetTypeName2);
	    } else {
		compareInd = assetTypeCode1.compareToIgnoreCase(assetTypeCode2);
	    }
	}

	return compareInd;
    }

}
