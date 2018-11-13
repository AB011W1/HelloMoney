package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.TacticalHelloMoneyEncryptPinServiceRequest;
import com.barclays.bmg.service.response.TacticalHelloMoneyEncryptPinServiceResponse;

public interface TacticalHelloMoneyEncryptPinDAO {
    public TacticalHelloMoneyEncryptPinServiceResponse encryptPin(TacticalHelloMoneyEncryptPinServiceRequest request);
}