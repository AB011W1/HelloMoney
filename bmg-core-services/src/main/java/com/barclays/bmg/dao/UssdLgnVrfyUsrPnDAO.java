/**
 *
 */
package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.UssdLgnVrfyUsrPnServiceRequest;
import com.barclays.bmg.service.response.UssdLgnVrfyUsrPnServiceResponse;

/**
 * @author E20037686
 * 
 */
public interface UssdLgnVrfyUsrPnDAO {
    public UssdLgnVrfyUsrPnServiceResponse verifyUserWithPin(UssdLgnVrfyUsrPnServiceRequest request);
}