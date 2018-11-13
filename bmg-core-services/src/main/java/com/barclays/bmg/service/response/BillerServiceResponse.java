package com.barclays.bmg.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.bmg.dto.BillerDTO;

public class BillerServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 512783284953103219L;
    private List<BillerDTO> billerList;
    private BillerDTO billerDTO;
    private BillerCreditDTO billerCreditDTO;

    public List<BillerDTO> getBillerList() {
	return billerList;
    }

    public void setBillerList(List<BillerDTO> billerList) {
	this.billerList = billerList;
    }

    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

	public BillerCreditDTO getBillerCreditDTO() {
		return billerCreditDTO;
	}

	public void setBillerCreditDTO(BillerCreditDTO billerCreditDTO) {
		this.billerCreditDTO = billerCreditDTO;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
