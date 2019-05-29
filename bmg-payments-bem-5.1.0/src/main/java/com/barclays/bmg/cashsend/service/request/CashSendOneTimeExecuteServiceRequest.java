package com.barclays.bmg.cashsend.service.request;

import java.util.Properties;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CashSendRequestDTO;

public class CashSendOneTimeExecuteServiceRequest extends RequestContext {
    private CashSendRequestDTO cashSendRequestDTO;
    private Properties hsmProperties;

    public Properties getHSMProperties() {
	return hsmProperties;
    }

    public void setHSMProperties(Properties bcagProperties) {
	this.hsmProperties = bcagProperties;
    }

    public CashSendRequestDTO getCashSendRequestDTO() {
	return cashSendRequestDTO;
    }

    public void setCashSendRequestDTO(CashSendRequestDTO cashSendRequestDTO) {
	this.cashSendRequestDTO = cashSendRequestDTO;
    }

}
