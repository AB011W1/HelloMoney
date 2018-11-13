package com.barclays.bmg.service.featureblackout;

import com.barclays.bmg.service.featureblackout.request.FeatureBlackoutServiceRequest;

public interface FeatureBlackoutService {

    public boolean checkFeatureBlackout(FeatureBlackoutServiceRequest request);

}
