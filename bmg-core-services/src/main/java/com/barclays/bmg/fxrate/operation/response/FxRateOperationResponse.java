package com.barclays.bmg.fxrate.operation.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.FxBoardRatesDTO;
import com.barclays.bmg.dto.FxRateDTO;

public class FxRateOperationResponse extends ResponseContext {
    private static final long serialVersionUID = 2174553166755246L;

    private FxBoardRatesDTO fxBoardRateDTO;

	public FxBoardRatesDTO getFxBoardRateDTO() {
		return fxBoardRateDTO;
	}

	public void setFxBoardRateDTO(FxBoardRatesDTO fxBoardRateDTO) {
		this.fxBoardRateDTO = fxBoardRateDTO;
	}



}
