package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.TacticalHelloMoneyVerifyUserServiceRequest;
import com.barclays.bmg.service.response.TacticalHelloMoneyVerifyUserServiceResponse;

public interface TacticalHelloMoneyVerifyUserDAO {
    public TacticalHelloMoneyVerifyUserServiceResponse verifyUserWithThmPin(TacticalHelloMoneyVerifyUserServiceRequest request);
}